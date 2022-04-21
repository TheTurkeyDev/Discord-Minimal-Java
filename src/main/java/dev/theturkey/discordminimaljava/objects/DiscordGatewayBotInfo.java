package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordGatewayBotInfo
{
	/**
	 * The WSS URL that can be used for connecting to the gateway
	 */
	public String url;
	/**
	 * The recommended number of shards to use when connecting
	 */
	public int shards;
	/**
	 * Information on the current session start limit
	 */
	@SerializedName("session_start_limit")
	public DiscordGatewaySessionStartLimit sessionStartLimit;
}
