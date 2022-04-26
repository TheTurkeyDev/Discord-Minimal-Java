package dev.theturkey.discordminimaljava.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.theturkey.discordminimaljava.DiscordMinimal;
import dev.theturkey.discordminimaljava.objects.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DiscordAPI
{
	public static Gson GSON;
	public static HttpClient CLIENT = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2)
			.build();
	public static String URL_BASE = "https://discord.com/api/v8/";
	private static final Map<String, BucketRateLimit> BUCKET_MAP = new HashMap<>();
	private static final ConcurrentLinkedQueue<DiscordAPIRequestData> REQUEST_QUEUE = new ConcurrentLinkedQueue<>();
	private static final Thread REQUEST_THREAD;

	static
	{
		GsonBuilder builder = new GsonBuilder();
		registerEnumTypeAdapters(builder, DiscordApplicationCommandType.class, DiscordApplicationCommandType::getFromId);
		registerEnumTypeAdapters(builder, DiscordApplicationCommandOptionType.class, DiscordApplicationCommandOptionType::getFromId);
		registerEnumTypeAdapters(builder, DiscordComponentType.class, DiscordComponentType::getFromId);
		registerEnumTypeAdapters(builder, DiscordInteractionType.class, DiscordInteractionType::getFromId);
		registerEnumTypeAdapters(builder, DiscordInteractionCallbackType.class, DiscordInteractionCallbackType::getFromId);
		GSON = builder.create();

		REQUEST_THREAD = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				long globalWait = -1;
				while(true)
				{
					DiscordAPIRequestData reqData = REQUEST_QUEUE.poll();

					if(reqData != null)
					{
						long now = System.currentTimeMillis();
						//TODO: Inefficient
						if(reqData.holdTime > now)
						{
							REQUEST_QUEUE.add(reqData);
							continue;
						}

						if(BUCKET_MAP.containsKey(reqData.urlGroup))
						{
							BucketRateLimit bucketInfo = BUCKET_MAP.get(reqData.urlGroup);
							if(bucketInfo.remain == 0)
							{
								long reset = (long) Math.ceil(bucketInfo.resetTime * 1000);
								if(now <= reset)
								{
									reqData.holdTime = reset;
									REQUEST_QUEUE.add(reqData);
									continue;
								}
							}
						}

						HttpRequest request = getDefaultReq(URL_BASE + reqData.url, reqData.reqType, reqData.body);
						HttpResponse<String> r;
						try
						{
							//TODO: Don't get right away
							r = CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get();
						} catch(Exception e)
						{
							e.printStackTrace();
							continue;
						}

						String bucket = r.headers().firstValue("x-ratelimit-bucket").orElse("ERR");

						String oldBucket = BUCKET_MAP.getOrDefault(reqData.urlGroup, new BucketRateLimit()).bucket;

						BucketRateLimit b = new BucketRateLimit();
						b.bucket = bucket;
						b.limit = Integer.parseInt(r.headers().firstValue("x-ratelimit-limit").orElse("0"));
						b.remain = Integer.parseInt(r.headers().firstValue("x-ratelimit-remaining").orElse("0"));
						b.resetTime = Double.parseDouble(r.headers().firstValue("x-ratelimit-reset").orElse("0"));
						b.resetAfter = Double.parseDouble(r.headers().firstValue("x-ratelimit-reset-after").orElse("0"));
						BUCKET_MAP.put(reqData.urlGroup, b);

						if(!oldBucket.isEmpty() && !oldBucket.equals(bucket))
							System.out.println("ERROR! Bucket mismatch! " + reqData.urlGroup + " | " + oldBucket + " | " + bucket);

						long globalWaitHeader = r.headers().firstValueAsLong("X-RateLimit-Global").orElse(-1);
						if(globalWaitHeader != -1)
						{
							JsonElement json = JsonParser.parseString(r.body());
							globalWait = json.getAsJsonObject().get("retry_after").getAsLong() * 1000;
						}
						String scope = r.headers().firstValue("X-RateLimit-Scope").orElse(null);
						if(scope != null)
						{
							BucketRateLimit bucketRateLimit = BUCKET_MAP.get(reqData.urlGroup);
							System.out.println("Rate limit! " + scope);
							System.out.println("\tInfo: " + bucketRateLimit.toString());
							System.out.println("\tReq: " + URL_BASE + reqData.url);
							System.out.println("\tGroup: " + reqData.urlGroup);
							reqData.holdTime = (long) Math.ceil(bucketRateLimit.resetTime * 1000);
							REQUEST_QUEUE.add(reqData);
							continue;
						}
						if(globalWait != -1)
						{
							try
							{
								Thread.sleep(globalWait);
							} catch(InterruptedException e)
							{
								e.printStackTrace();
							}
							REQUEST_QUEUE.add(reqData);
						}
						else
						{
							reqData.onComplete(r.statusCode(), r.body());
						}
					}
					else
					{
						synchronized(REQUEST_THREAD)
						{
							try
							{
								REQUEST_THREAD.wait();
							} catch(InterruptedException e)
							{
								e.printStackTrace();
							}
						}
					}
				}
			}
		});
		REQUEST_THREAD.start();
	}

	private static <T extends IDEnum> void registerEnumTypeAdapters(GsonBuilder gsonBuilder, Class<T> t, EnumDeserializer<IDEnum> deserializer)
	{
		gsonBuilder.registerTypeAdapter(t, new EnumSerializer<T>());
		gsonBuilder.registerTypeAdapter(t, deserializer);
	}

	private static HttpResponseWrapper sendRestCall(String group, String url, String reqType)
	{
		return sendRestCall(group, url, reqType, null);
	}

	private static HttpResponseWrapper sendRestCall(String group, String url, String reqType, String body)
	{
		HttpResponseWrapper response = new HttpResponseWrapper();
		REQUEST_QUEUE.add(new DiscordAPIRequestData(group, url, reqType, body, response));
		if(REQUEST_QUEUE.size() == 1)
		{
			synchronized(REQUEST_THREAD)
			{
				REQUEST_THREAD.notifyAll();
			}
		}
		return response;
	}

	public static HttpRequest getDefaultReq(String url, String reqType, String body)
	{
		return HttpRequest.newBuilder()
				.uri(URI.create(url))
				.timeout(Duration.ofMinutes(1))
				.header("Authorization", "Bot " + DiscordMinimal.token)
				.header("Content-Type", "application/json")
				.header("User-Agent", "Discord-Minimal-Java")
				.method(reqType, body == null ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(body))
				.build();
	}

	private static <T> ResponseObject<T> getResponseObj(int code, String body, Class<T> clazz)
	{
		if(code != 200)
			return new ResponseObject<>(null, GSON.fromJson(body, DiscordAPIError.class));
		else
			return new ResponseObject<>(GSON.fromJson(body, clazz), null);
	}

	private static ResponseObject<Void> getEmptyResponseObj(int code, String body)
	{
		if(code != 200)
			return new ResponseObject<>(null, GSON.fromJson(body, DiscordAPIError.class));
		else
			return new ResponseObject<>(null, null);
	}

	public static DiscordAPIResponse<DiscordGatewayBotInfo> getGatewayBot()
	{
		DiscordAPIResponse<DiscordGatewayBotInfo> apiResp = new DiscordAPIResponse<>();
		String url = "gateway/bot";
		sendRestCall(url, url, "GET").onResponse((code, body) ->
				apiResp.resolveCall(getResponseObj(code, body, DiscordGatewayBotInfo.class)));

		return apiResp;
	}

	public static DiscordAPIResponse<DiscordMessage> createMessage(long channelId, DiscordMessageCreate message)
	{
		DiscordAPIResponse<DiscordMessage> apiResp = new DiscordAPIResponse<>();
		String url = "channels/" + channelId + "/messages";
		sendRestCall(url + "/create", url, "POST", GSON.toJson(message)).onResponse((code, body) ->
				apiResp.resolveCall(getResponseObj(code, body, DiscordMessage.class)));

		return apiResp;
	}

	public static DiscordAPIResponse<DiscordMessage> sendEmbed(long channelId, DiscordEmbed embed)
	{
		DiscordMessageCreate message = new DiscordMessageCreate();
		message.embeds = new DiscordEmbed[]{embed};
		return createMessage(channelId, message);
	}

	public static DiscordAPIResponse<DiscordMessage> sendMessage(long channelId, String msg)
	{
		DiscordMessageCreate message = new DiscordMessageCreate();
		message.content = msg;
		return createMessage(channelId, message);
	}

	public static DiscordAPIResponse<DiscordMessage> editEmbed(DiscordMessage orig, DiscordEmbed embed)
	{
		DiscordMessageEdit editMessage = new DiscordMessageEdit();
		editMessage.embeds = new DiscordEmbed[]{embed};

		DiscordAPIResponse<DiscordMessage> apiResp = new DiscordAPIResponse<>();
		String url = "channels/" + orig.channelId + "/messages/" + orig.id;
		sendRestCall(url + "/edit", url, "PATCH", GSON.toJson(editMessage)).onResponse((code, body) ->
				apiResp.resolveCall(getResponseObj(code, body, DiscordMessage.class)));

		return apiResp;
	}

	public static DiscordAPIResponse<List<DiscordApplicationCommand>> getGlobalApplicationCommands(long applicationId)
	{
		DiscordAPIResponse<List<DiscordApplicationCommand>> apiResp = new DiscordAPIResponse<>();
		String url = "applications/" + applicationId + "/commands";
		sendRestCall(url, url, "GET").onResponse((code, body) ->
		{
			if(code != 200)
			{
				DiscordAPIError error = GSON.fromJson(body, DiscordAPIError.class);
				apiResp.resolveCall(new ResponseObject<>(null, error));
			}
			else
			{
				JsonArray jsonArray = JsonParser.parseString(body).getAsJsonArray();
				List<DiscordApplicationCommand> commands = new ArrayList<>();
				for(JsonElement e : jsonArray)
					commands.add(GSON.fromJson(e, DiscordApplicationCommand.class));
				apiResp.resolveCall(new ResponseObject<>(commands, null));
			}
		});

		return apiResp;
	}

	public static DiscordAPIResponse<Void> deleteGlobalApplicationCommands(DiscordApplicationCommand command)
	{
		DiscordAPIResponse<Void> apiResp = new DiscordAPIResponse<>();
		String url = "applications/" + command.applicationId + "/commands/" + command.id;
		sendRestCall(url + "/delete", url, "DELETE").onResponse((code, body) ->
				apiResp.resolveCall(getEmptyResponseObj(code, body)));

		return apiResp;
	}

	public static DiscordAPIResponse<DiscordApplicationCommand> createGlobalAppCommand(DiscordApplicationCommand command)
	{
		DiscordAPIResponse<DiscordApplicationCommand> apiResp = new DiscordAPIResponse<>();
		String url = "applications/" + command.applicationId + "/commands";
		sendRestCall(url + "/create", url, "POST", GSON.toJson(command)).onResponse((code, body) ->
				apiResp.resolveCall(getResponseObj(code, body, DiscordApplicationCommand.class)));

		return apiResp;
	}

	public static DiscordAPIResponse<Void> timeoutUser(long guildId, long userId, int durationSec)
	{
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("communication_disabled_until", LocalDateTime.now().plusSeconds(durationSec).atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));

		DiscordAPIResponse<Void> apiResp = new DiscordAPIResponse<>();
		String url = "guilds/" + guildId + "/members/" + userId;
		sendRestCall(url, url, "PATCH", GSON.toJson(jsonObject)).onResponse((code, body) ->
				apiResp.resolveCall(getEmptyResponseObj(code, body)));

		return apiResp;
	}

	public static void interactionCallback(long id, String token, DiscordInteractionResponse resp)
	{
		HttpRequest request = getDefaultReq(URL_BASE + "/interactions/" + id + "/" + token + "/callback", "POST", GSON.toJson(resp));
		try
		{
			CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static DiscordAPIResponse<DiscordMessage> interactionEdit(long id, String token, DiscordEditWebhookMessage edit)
	{
		DiscordAPIResponse<DiscordMessage> apiResp = new DiscordAPIResponse<>();
		String url = "webhooks/" + id + "/" + token + "/messages/@original";
		sendRestCall(url + "/edit", url, "PATCH", GSON.toJson(edit)).onResponse((code, body) ->
				apiResp.resolveCall(getResponseObj(code, body, DiscordMessage.class)));
		return apiResp;
	}
}
