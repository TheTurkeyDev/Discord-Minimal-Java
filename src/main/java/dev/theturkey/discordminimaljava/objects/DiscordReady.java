package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordReady
{
	/**
	 * Gateway version
	 */
	public int v;

	/**
	 * Information about the user including email
	 */
	public DiscordUser user;

	/**
	 * The guilds the user is in
	 */
	public DiscordGuild[] guilds;

	/**
	 * Used for resuming connections
	 */
	@SerializedName("session_id")
	public String sessionId;

	/**
	 * Gateway url for resuming connections
	 */
	@SerializedName("resume_gateway_url")
	public String resumeGatewayUrl;

	/**
	 * OPTIONAL
	 * Array of two integers(shard_id, num_shards) the shard information associated with this session, if sent when identifying
	 */
	public int[] shard;

	/**
	 * Partial application object, contains id and flags
	 */
	public DiscordApplication application;
}
