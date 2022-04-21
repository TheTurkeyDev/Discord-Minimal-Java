package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordRole
{
	/**
	 * Role id
	 */
	public long id;

	/**
	 * Role name
	 */
	public String name;

	/**
	 * Integer representation of hexadecimal color code
	 */
	public int color;

	/**
	 * If this role is pinned in the user listing
	 */
	public boolean hoist;

	/**
	 * OPTIONAL
	 * Role icon hash
	 */
	public String icon;

	/**
	 * OPTIONAL
	 * Role unicode emoji
	 */
	@SerializedName("unicode_emoji")
	public String unicodeEmoji;

	/**
	 * Position of this role
	 */
	public int position;

	/**
	 * Permission bit set
	 */
	public String permissions;

	/**
	 * Whether this role is managed by an integration
	 */
	public boolean managed;

	/**
	 * Whether this role is mentionable
	 */
	public boolean mentionable;

	/**
	 * The tags this role has
	 */
	public DiscordRoleTag tags;
}
