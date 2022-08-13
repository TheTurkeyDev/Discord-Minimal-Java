package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordMessageReactionAdd
{
	/**
	 * The user's id
	 */
	@SerializedName("user_id")
	public String userId;

	/**
	 * The id of the channel
	 */
	@SerializedName("channel_id")
	public String channelId;

	/**
	 * The id of the message
	 */
	@SerializedName("message_id")
	public String messageId;

	/**
	 * OPTIONAL
	 * The id of the guild
	 */
	@SerializedName("guild_id")
	public String guildId;

	/**
	 * OPTIONAL
	 * The member who reacted if this happened in a guild
	 */
	public DiscordGuildMember member;

	/**
	 * PARTIAL
	 * The emoji used to react - example
	 */
	//public DiscordGuildMember emoji;
}
