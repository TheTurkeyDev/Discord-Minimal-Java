package dev.theturkey.discordminimaljava.objects;

public class DiscordGuildMember
{
	/**
	 * OPTIONAL
	 * The user this guild member represents
	 */
	public DiscordUser user;

	/**
	 * OPTIONAL
	 * This users guild nickname
	 */
	public String nick;

	/**
	 * OPTIONAL
	 * the member's guild avatar hash
	 */
	public String avatar;

	/**
	 * Array of role object ids
	 */
	public long[] roles;
	// public joined_at	ISO8601 timestamp	when the user joined the guild
	// public premium_since?	?ISO8601 timestamp	when the user started boosting the guild

	/**
	 * Whether the user is deafened in voice channels
	 */
	public boolean deaf;

	/**
	 * Whether the user is muted in voice channels
	 */
	public boolean mute;

	/**
	 * OPTIONAL
	 * Whether the user has not yet passed the guild's Membership Screening requirements
	 */
	public boolean pending;

	/**
	 * OPTIONAL
	 * Total permissions of the member in the channel, including overwrites, returned when in the interaction object
	 */
	public String permissions;

}
