package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordMessageReference
{
	/**
	 * OPTIONAL
	 * Id of the originating message
	 */
	@SerializedName("message_id")
	public long messageId;

	/**
	 * OPTIONAL
	 * Id of the originating message's channel
	 */
	@SerializedName("channel_id")
	public long channelId;

	/**
	 * OPTIONAL
	 * Id of the originating message's guild
	 */
	@SerializedName("guild_id")
	public long guildId;

	/**
	 * OPTIONAL
	 * When sending, whether to error if the referenced message doesn't exist instead of sending as a normal (non-reply) message, default true
	 */
	@SerializedName("fail_if_not_exists")
	public boolean failIfNotExists = true;
}
