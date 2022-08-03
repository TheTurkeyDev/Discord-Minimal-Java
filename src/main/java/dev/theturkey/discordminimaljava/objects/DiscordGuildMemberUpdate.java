package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordGuildMemberUpdate
{
	/**
	 * The id of the guild
	 */
	@SerializedName("guild_id")
	public long guildId;

	/**
	 * User role ids
	 */
	public long[] roles;

	/**
	 * The user
	 */
	public DiscordUser user;

	/**
	 * OPTIONAL
	 * Nickname of the user in the guild
	 */
	public String nick;

	/**
	 * OPTIONAL
	 * The member's guild avatar hash
	 */
	public String avatar;

//	joined_at	?ISO8601 timestamp	when the user joined the guild
//	premium_since?	?ISO8601 timestamp	when the user starting boosting the guild

	/**
	 * OPTIONAL
	 * Whether the user is deafened in voice channels
	 */
	public boolean deaf;

	/**
	 * OPTIONAL
	 * Whether the user is muted in voice channels
	 */
	public boolean mute;

	/**
	 * OPTIONAL
	 * Whether the user has not yet passed the guild's Membership Screening requirements
	 */
	public boolean pending;
//	communication_disabled_until?	?ISO8601 timestamp	when the user's timeout will expire and the user will be able to communicate in the guild again, null or a time in the past if the user is not timed out
}
