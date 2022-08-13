package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DiscordGuild
{
	/**
	 * Guild id
	 */
	public String id;
	/**
	 * Guild name (2-100 characters, excluding trailing and leading whitespace)
	 */
	public String name;
	/**
	 * icon hash
	 */
	public String icon;
	/**
	 * icon hash, returned when in the template object
	 */
	@SerializedName("icon_hash")
	public String iconHash;
	// splash	?string	splash hash
	// discovery_splash	?string	discovery splash hash; only present for guilds with the "DISCOVERABLE" feature
	/**
	 * id of owner
	 */
	@SerializedName("owner_id")
	public String ownerId;
	// region? ***	?string	voice region id for the guild (deprecated)
	// afk_channel_id	?snowflake	id of afk channel
	// afk_timeout	integer	afk timeout in seconds
	// widget_enabled?	boolean	true if the server widget is enabled
	// widget_channel_id?	?snowflake	the channel id that the widget will generate an invite to, or null if set to no invite
	// verification_level	integer	verification level required for the guild
	// default_message_notifications	integer	default message notifications level
	// explicit_content_filter	integer	explicit content filter level
	//public roles: DiscordRole[] = [];            // Roles in the guild
	// emojis	array of emoji objects	custom guild emojis
	// features	array of guild feature strings	enabled guild features
	// mfa_level	integer	required MFA level for the guild
	// application_id	?snowflake	application id of the guild creator if it is bot-created
	// system_channel_id	?snowflake	the id of the channel where guild notices such as welcome messages and boost events are posted
	// system_channel_flags	integer	system channel flags
	// rules_channel_id	?snowflake	the id of the channel where Community guilds can display rules and/or guidelines
	// joined_at? *	ISO8601 timestamp	when this guild was joined at
	// large? *	boolean	true if this is considered a large guild
	/**
	 * True if this guild is unavailable due to an outage
	 */
	public boolean unavailable;
	// member_count? *	integer	total number of members in this guild
	// voice_states? *	array of partial voice state objects	states of members currently in voice channels; lacks the guild_id key
	// members? *	array of guild member objects	users in the guild

	/**
	 * Channels in the guild
	 */
	public List<DiscordChannel> channels = new ArrayList<>();

	// threads? *	array of channel objects	all active threads in the guild that current user has permission to view
	// presences? *	array of partial presence update objects	presences of the members in the guild, will only include non-offline members if the size is greater than large threshold
	// max_presences?	?integer	the maximum number of presences for the guild (null is always returned, apart from the largest of guilds)
	// max_members?	integer	the maximum number of members for the guild
	// vanity_url_code	?string	the vanity url code for the guild
	// description	?string	the description of a Community guild
	// banner	?string	banner hash
	// premium_tier	integer	premium tier (Server Boost level)
	// premium_subscription_count?	integer	the number of boosts this guild currently has
	// preferred_locale	string	the preferred locale of a Community guild; used in server discovery and notices from Discord; defaults to "en-US"
	// public_updates_channel_id	?snowflake	the id of the channel where admins and moderators of Community guilds receive notices from Discord
	// max_video_channel_users?	integer	the maximum amount of users in a video channel
	// approximate_member_count?	integer	approximate number of members in this guild, returned from the GET /guilds/<id> endpoint when with_counts is true
	// approximate_presence_count?	integer	approximate number of non-offline members in this guild, returned from the GET /guilds/<id> endpoint when with_counts is true
	// welcome_screen?	welcome screen object	the welcome screen of a Community guild, shown to new members, returned in an Invite's guild object
	// nsfw_level	integer	guild NSFW level
	// stage_instances? *	array of stage instance objects	Stage instances in the guild
	// stickers?	array of sticker objects	custom guild stickers
	// guild_scheduled_events? *	array of guild scheduled event objects	the scheduled events in the guild
	// premium_progress_bar_enabled	boolean	whether the guild has the boost progress bar enabled
}
