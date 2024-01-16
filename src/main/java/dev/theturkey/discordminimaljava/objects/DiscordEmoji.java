package dev.theturkey.discordminimaljava.objects;

import java.util.ArrayList;
import java.util.List;

public class DiscordEmoji
{
	/**
	 * Emoji id
	 * (Optional)
	 */
	public String id;
	/**
	 * Emoji name
	 * Can be null only in reaction emoji objects
	 * (Optional)
	 */
	public String name;
	/**
	 * roles allowed to use this emoji
	 * (Optional)
	 */
	public List<DiscordRole> roles = new ArrayList<>();
	/**
	 * User object	user that created this emoji
	 * (Optional)
	 */
	public DiscordUser user;
//	require_colons?	boolean	whether this emoji must be wrapped in colons
//	managed?	boolean	whether this emoji is managed
//	animated?	boolean	whether this emoji is animated
//	available?	boolean	whether this emoji can be used, may be false due to loss of Server Boosts
}
