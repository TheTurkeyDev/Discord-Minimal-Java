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
import java.util.concurrent.CountDownLatch;

public class DiscordAPI
{
	public static Gson GSON;
	public static String URL_BASE = "https://discord.com/api/v" + DiscordMinimal.API_VERSION + "/";
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
		registerEnumTypeAdapters(builder, DiscordChannelType.class, DiscordChannelType::getFromId);
		registerEnumTypeAdapters(builder, DiscordButtonStyle.class, DiscordButtonStyle::getFromId);
		registerEnumTypeAdapters(builder, DiscordTextInputStyle.class, DiscordTextInputStyle::getFromId);
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
							r = getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString()).get();
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

	private static HttpClient getClient()
	{
		return HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_2)
				.build();
	}

	private static HttpResponseWrapper sendRestCall(String topLvlUrl, String url, String reqType)
	{
		return sendRestCall(topLvlUrl, url, reqType, null);
	}

	private static HttpResponseWrapper sendRestCall(String topLvlUrl, String url, String reqType, String body)
	{
		HttpResponseWrapper response = new HttpResponseWrapper();
		REQUEST_QUEUE.add(new DiscordAPIRequestData(topLvlUrl + url + "/" + reqType, topLvlUrl + url, reqType, body, response));
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

	private static <T> ResponseObject<List<T>> getResponseObjList(int code, String body, Class<T> clazz)
	{
		if(code != 200)
		{
			DiscordAPIError error = GSON.fromJson(body, DiscordAPIError.class);
			return new ResponseObject<>(null, error);
		}
		else
		{
			JsonArray jsonArray = JsonParser.parseString(body).getAsJsonArray();
			List<T> commands = new ArrayList<>();
			for(JsonElement e : jsonArray)
				commands.add(GSON.fromJson(e, clazz));
			return new ResponseObject<>(commands, null);
		}
	}

	private static ResponseObject<Void> getEmptyResponseObj(int code, String body)
	{
		if(code != 200)
			return new ResponseObject<>(null, GSON.fromJson(body, DiscordAPIError.class));
		else
			return new ResponseObject<>(null, null);
	}

	private static void latch(CountDownLatch latch)
	{
		try
		{
			latch.await();
		} catch(InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static <T> T makeBlockingRequest(DiscordAPIResponse<T> request)
	{
		List<T> info = new ArrayList<>();
		CountDownLatch latch = new CountDownLatch(1);
		request.onResponse(resp ->
		{
			info.add(resp.data);
			latch.countDown();
		});
		latch(latch);
		return info.get(0);
	}

	public static <T> List<T> makeBlockingListRequest(DiscordAPIResponse<List<T>> request)
	{
		List<T> info = new ArrayList<>();
		CountDownLatch latch = new CountDownLatch(1);
		request.onResponse(resp ->
		{
			info.addAll(resp.data);
			latch.countDown();
		});
		latch(latch);
		return info;
	}

	public static void makeBlockingVoidRequest(DiscordAPIResponse<Void> request)
	{
		CountDownLatch latch = new CountDownLatch(1);
		request.onResponse(resp -> latch.countDown());
		latch(latch);
	}

	public static DiscordAPIResponse<DiscordGatewayBotInfo> getGatewayBot()
	{
		DiscordAPIResponse<DiscordGatewayBotInfo> apiResp = new DiscordAPIResponse<>();
		sendRestCall("gateway/bot", "", "GET").onResponse((code, body) ->
				apiResp.resolveCall(getResponseObj(code, body, DiscordGatewayBotInfo.class)));

		return apiResp;
	}

	public static DiscordAPIResponse<DiscordMessage> createMessage(String channelId, DiscordMessageCreate message)
	{
		DiscordAPIResponse<DiscordMessage> apiResp = new DiscordAPIResponse<>();
		sendRestCall("channels/" + channelId + "/messages", "", "POST", GSON.toJson(message)).onResponse((code, body) ->
				apiResp.resolveCall(getResponseObj(code, body, DiscordMessage.class)));

		return apiResp;
	}

	public static DiscordAPIResponse<Void> deleteMessage(String channelId, String messageId)
	{
		DiscordAPIResponse<Void> apiResp = new DiscordAPIResponse<>();
		sendRestCall("channels/" + channelId + "/messages/", messageId, "DELETE").onResponse((code, body) ->
				apiResp.resolveCall(getEmptyResponseObj(code, body)));

		return apiResp;
	}

	public static DiscordAPIResponse<DiscordMessage> sendEmbed(String channelId, DiscordEmbed embed, DiscordComponent[] components)
	{
		DiscordMessageCreate message = new DiscordMessageCreate();
		message.embeds = new DiscordEmbed[]{embed};
		message.components = components;
		return createMessage(channelId, message);
	}

	public static DiscordAPIResponse<DiscordMessage> sendEmbed(String channelId, DiscordEmbed embed, DiscordComponent component)
	{
		return sendEmbed(channelId, embed, new DiscordComponent[]{component});
	}

	public static DiscordAPIResponse<DiscordMessage> sendEmbed(String channelId, DiscordEmbed embed)
	{
		return sendEmbed(channelId, embed, new DiscordComponent[0]);
	}

	public static DiscordAPIResponse<DiscordMessage> sendMessage(String channelId, String msg)
	{
		DiscordMessageCreate message = new DiscordMessageCreate();
		message.content = msg;
		return createMessage(channelId, message);
	}

	public static DiscordAPIResponse<DiscordMessage> editEmbed(String channelId, String messageId, DiscordEmbed embed, DiscordComponent[] components)
	{
		DiscordMessageEdit editMessage = new DiscordMessageEdit();
		editMessage.embeds = new DiscordEmbed[]{embed};
		editMessage.components = components;

		DiscordAPIResponse<DiscordMessage> apiResp = new DiscordAPIResponse<>();
		sendRestCall("channels/" + channelId + "/messages/", messageId, "PATCH", GSON.toJson(editMessage)).onResponse((code, body) ->
				apiResp.resolveCall(getResponseObj(code, body, DiscordMessage.class)));

		return apiResp;
	}

	public static DiscordAPIResponse<DiscordMessage> editEmbed(String channelId, String messageId, DiscordEmbed embed)
	{
		return editEmbed(channelId, messageId, embed, new DiscordComponent[0]);
	}

	public static DiscordAPIResponse<DiscordMessage> editEmbed(String channelId, String messageId, DiscordEmbed embed, DiscordComponent component)
	{
		return editEmbed(channelId, messageId, embed, new DiscordComponent[]{component});
	}

	public static DiscordAPIResponse<DiscordMessage> editEmbed(DiscordMessage orig, DiscordEmbed embed)
	{
		return editEmbed(orig.channelId, orig.id, embed, new DiscordComponent[0]);
	}

	public static DiscordAPIResponse<DiscordMessage> editEmbed(DiscordMessage orig, DiscordEmbed embed, DiscordComponent[] components)
	{
		return editEmbed(orig.channelId, orig.id, embed, components);
	}

	public static DiscordAPIResponse<DiscordMessage> editEmbed(DiscordMessage orig, DiscordEmbed embed, DiscordComponent component)
	{
		return editEmbed(orig, embed, new DiscordComponent[]{component});
	}

	public static DiscordAPIResponse<List<DiscordGuildMember>> listGuildMembers(String guildId)
	{
		return listGuildMembers(guildId, 1, "0");
	}

	public static DiscordAPIResponse<List<DiscordGuildMember>> listGuildMembers(String guildId, int limit)
	{
		return listGuildMembers(guildId, limit, "0");
	}

	public static DiscordAPIResponse<List<DiscordGuildMember>> listGuildMembers(String guildId, int limit, String after)
	{
		int maxLim = Math.min(1000, limit);
		DiscordAPIResponse<List<DiscordGuildMember>> apiResp = new DiscordAPIResponse<>();
		sendRestCall("/guilds/" + guildId + "/members", "?limit=" + maxLim + "&after=" + after, "GET").onResponse((code, body) ->
				apiResp.resolveCall(getResponseObjList(code, body, DiscordGuildMember.class)));
		return apiResp;
	}

	public static DiscordAPIResponse<DiscordGuildMember> getGuildMember(String guildId, String userId)
	{
		DiscordAPIResponse<DiscordGuildMember> apiResp = new DiscordAPIResponse<>();
		sendRestCall("/guilds/" + guildId + "/members", "/" + userId, "GET").onResponse((code, body) ->
				apiResp.resolveCall(getResponseObj(code, body, DiscordGuildMember.class)));
		return apiResp;
	}

	public static DiscordAPIResponse<List<DiscordRole>> getGuildRoles(String guildId)
	{
		DiscordAPIResponse<List<DiscordRole>> apiResp = new DiscordAPIResponse<>();
		sendRestCall("/guilds/" + guildId + "/roles", "", "GET").onResponse((code, body) ->
				apiResp.resolveCall(getResponseObjList(code, body, DiscordRole.class)));
		return apiResp;
	}

	public static DiscordAPIResponse<Void> addGuildMemberRole(String guildId, String userId, String roleId)
	{
		DiscordAPIResponse<Void> apiResp = new DiscordAPIResponse<>();
		sendRestCall("/guilds/" + guildId + "/members/", userId + "/roles/" + roleId, "PUT").onResponse((code, body) ->
				apiResp.resolveCall(getEmptyResponseObj(code, body)));
		return apiResp;
	}

	public static DiscordAPIResponse<Void> removeGuildMemberRole(String guildId, String userId, String roleId)
	{
		DiscordAPIResponse<Void> apiResp = new DiscordAPIResponse<>();
		sendRestCall("/guilds/" + guildId + "/members/", userId + "/roles/" + roleId, "DELETE").onResponse((code, body) ->
				apiResp.resolveCall(getEmptyResponseObj(code, body)));

		return apiResp;
	}

	public static DiscordAPIResponse<List<DiscordApplicationCommand>> getGlobalApplicationCommands(String applicationId)
	{
		DiscordAPIResponse<List<DiscordApplicationCommand>> apiResp = new DiscordAPIResponse<>();
		sendRestCall("applications/" + applicationId + "/commands", "", "GET").onResponse((code, body) ->
				apiResp.resolveCall(getResponseObjList(code, body, DiscordApplicationCommand.class)));

		return apiResp;
	}

	public static DiscordAPIResponse<Void> deleteGlobalApplicationCommands(DiscordApplicationCommand command)
	{
		DiscordAPIResponse<Void> apiResp = new DiscordAPIResponse<>();
		sendRestCall("applications/" + command.applicationId + "/commands/", command.id, "DELETE").onResponse((code, body) ->
				apiResp.resolveCall(getEmptyResponseObj(code, body)));

		return apiResp;
	}

	public static DiscordAPIResponse<DiscordApplicationCommand> createGlobalAppCommand(DiscordApplicationCommand command)
	{
		DiscordAPIResponse<DiscordApplicationCommand> apiResp = new DiscordAPIResponse<>();
		sendRestCall("applications/" + command.applicationId + "/commands", "", "POST", GSON.toJson(command)).onResponse((code, body) ->
				apiResp.resolveCall(getResponseObj(code, body, DiscordApplicationCommand.class)));

		return apiResp;
	}

	public static DiscordAPIResponse<DiscordApplicationCommand> createGuildAppCommand(DiscordApplicationCommand command, String guildId)
	{
		DiscordAPIResponse<DiscordApplicationCommand> apiResp = new DiscordAPIResponse<>();
		sendRestCall("applications/" + command.applicationId + "/guilds/" + guildId + "/commands", "", "POST", GSON.toJson(command)).onResponse((code, body) ->
				apiResp.resolveCall(getResponseObj(code, body, DiscordApplicationCommand.class)));

		return apiResp;
	}

	public static DiscordAPIResponse<Void> timeoutUser(String guildId, String userId, int durationSec)
	{
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("communication_disabled_until", LocalDateTime.now().plusSeconds(durationSec).atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));

		DiscordAPIResponse<Void> apiResp = new DiscordAPIResponse<>();
		sendRestCall("guilds/" + guildId + "/members/", userId, "PATCH", GSON.toJson(jsonObject)).onResponse((code, body) ->
				apiResp.resolveCall(getEmptyResponseObj(code, body)));

		return apiResp;
	}

	public static void interactionCallback(String id, String token, DiscordInteractionResponse resp)
	{
		HttpRequest request = getDefaultReq(URL_BASE + "/interactions/" + id + "/" + token + "/callback", "POST", GSON.toJson(resp));
		try
		{
			getClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static DiscordAPIResponse<DiscordMessage> interactionEdit(String id, String token, DiscordEditWebhookMessage edit)
	{
		DiscordAPIResponse<DiscordMessage> apiResp = new DiscordAPIResponse<>();
		sendRestCall("webhooks/" + id + "/" + token + "/messages/@original", "", "PATCH", GSON.toJson(edit)).onResponse((code, body) ->
				apiResp.resolveCall(getResponseObj(code, body, DiscordMessage.class)));
		return apiResp;
	}
}
