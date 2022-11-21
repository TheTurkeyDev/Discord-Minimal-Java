package dev.theturkey.discordminimaljava;

import dev.theturkey.discordminimaljava.objects.DiscordGatewayBotInfo;
import dev.theturkey.discordminimaljava.objects.DiscordGuild;
import dev.theturkey.discordminimaljava.objects.DiscordGuildMemberUpdate;
import dev.theturkey.discordminimaljava.objects.DiscordGuildRoleCreateResponse;
import dev.theturkey.discordminimaljava.objects.DiscordInteraction;
import dev.theturkey.discordminimaljava.objects.DiscordMessage;
import dev.theturkey.discordminimaljava.objects.DiscordMessageReactionAdd;
import dev.theturkey.discordminimaljava.objects.DiscordReady;
import dev.theturkey.discordminimaljava.payloads.DiscordGatewayPayload;
import dev.theturkey.discordminimaljava.rest.DiscordAPI;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiscordMinimal
{
	public static final int API_VERSION = 10;
	public static String token;
	private final List<DiscordWebSocket> websocket = new ArrayList<>();

	private final long intents;
	private int shards = 1;

	public DiscordMinimal(long[] intents)
	{
		this.intents = Arrays.stream(intents).sum();
	}

	public void login(String token)
	{
		DiscordMinimal.token = token;

		DiscordAPI.getGatewayBot().onResponse(resp -> startBot(resp.data));
	}

	private void startBot(DiscordGatewayBotInfo gatewayInfo)
	{
		this.shards = gatewayInfo.shards;

		int numShards = gatewayInfo.sessionStartLimit.maxConcurrency;
		new Thread(() ->
		{
			while(true)
			{
				int startIndex = this.websocket.size();
				int max = Math.min(numShards, this.shards - startIndex);
				for(int i = 0; i < max; i++)
					this.initGatewaySocket(gatewayInfo.url, startIndex + i, numShards);

				if(this.websocket.size() == this.shards)
					return;

				try
				{
					Thread.sleep(7000);
				} catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void initGatewaySocket(String gatewayUrl, int shardId, int numShards)
	{
		try
		{
			DiscordWebSocket ws = new DiscordWebSocket(this, gatewayUrl + "/?v=" + API_VERSION + "&encoding=json", intents, shardId, numShards);
			this.websocket.add(ws);
			ws.connect();

		} catch(URISyntaxException e)
		{
			e.printStackTrace();
		}
	}

	public void onPayload(DiscordGatewayPayload payload)
	{
		switch(payload.t)
		{
			case "READY" -> onReady(assign(payload, DiscordReady.class));
			case "MESSAGE_CREATE" -> onMessageCreate(assign(payload, DiscordMessage.class));
			case "MESSAGE_UPDATE" -> onMessageUpdate(assign(payload, DiscordMessage.class));
			case "MESSAGE_DELETE" -> onMessageDelete(assign(payload, DiscordMessage.class));
			case "MESSAGE_REACTION_ADD" -> onMessageReactionAdd(assign(payload, DiscordMessageReactionAdd.class));
			case "INTERACTION_CREATE" -> onInteractionCreate(assign(payload, DiscordInteraction.class));
			case "GUILD_CREATE" -> onGuildCreate(assign(payload, DiscordGuild.class));
			case "GUILD_DELETE" -> onGuildDelete(assign(payload, DiscordGuild.class));
			case "GUILD_UPDATE" -> onGuildUpdate(assign(payload, DiscordGuild.class));
			case "GUILD_MEMBER_UPDATE" -> onDiscordGuildMemberUpdate(assign(payload, DiscordGuildMemberUpdate.class));
			case "GUILD_ROLE_CREATE" -> onDiscordGuildRoleCreate(assign(payload, DiscordGuildRoleCreateResponse.class));
			case "GUILD_ROLE_UPDATE" -> onDiscordGuildRoleUpdate(assign(payload, DiscordGuildRoleCreateResponse.class));
			case "RESUMED", "APPLICATION_COMMAND_PERMISSIONS_UPDATE", "GIFT_CODE_UPDATE" ->
			{
			}

			default -> System.out.println("UNKNOWN EVENT: " + payload.t);
		}
	}

	public <T> T assign(DiscordGatewayPayload payload, Class<T> clazz)
	{
		return DiscordAPI.GSON.fromJson(payload.d, clazz);
	}

	/*
	 * ======== EVENTS ==========
	 */
	//@formatter:off
	public void onClose(int code, String reason, boolean remote){}
	public void onError(Exception ex){}
	public void onReady(DiscordReady ready){}
	public void onMessageCreate(DiscordMessage message){}
	public void onMessageUpdate(DiscordMessage message){}
	public void onMessageDelete(DiscordMessage message){}
	public void onMessageReactionAdd(DiscordMessageReactionAdd reaction){}
	public void onInteractionCreate(DiscordInteraction interaction){}
	public void onGuildCreate(DiscordGuild guild){}
	public void onGuildDelete(DiscordGuild guild){}
	public void onGuildUpdate(DiscordGuild guild){}
	public void onDiscordGuildMemberUpdate(DiscordGuildMemberUpdate member){}
	public void onDiscordGuildRoleCreate(DiscordGuildRoleCreateResponse response){}
	public void onDiscordGuildRoleUpdate(DiscordGuildRoleCreateResponse response){}

	//@formatter:on
}
