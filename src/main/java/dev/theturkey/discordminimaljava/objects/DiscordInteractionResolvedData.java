package dev.theturkey.discordminimaljava.objects;

import java.util.HashMap;
import java.util.Map;

public class DiscordInteractionResolvedData
{
	/**
	 * OPTIONAL
	 * Map of Snowflakes to user objects
	 * The ids and User objects
	 */
	public Map<String, DiscordUser> users = new HashMap<>();

	/**
	 * OPTIONAL
	 * Map of Snowflakes to partial member objects
	 * The ids and partial Member objects
	 * * Partial Member objects are missing user, deaf and mute fields
	 */
	public Map<String, DiscordGuildMember> members = new HashMap<>();

	/**
	 * OPTIONAL
	 * Map of Snowflakes to role objects
	 * The ids and Role objects
	 */
	public Map<String, DiscordRole> roles = new HashMap<>();

	/**
	 * OPTIONAL
	 * Map of Snowflakes to partial channel objects
	 * The ids and partial Channel objects
	 * * Partial Channel objects only have id, name, type and permissions fields. Threads will also have thread_metadata and parent_id fields.
	 */
	public Map<String, DiscordChannel> channels = new HashMap<>();

	/**
	 * OPTIONAL
	 * Map of Snowflakes to partial messages objects
	 * The ids and partial Message objects
	 */
	public Map<String, DiscordMessage> messages = new HashMap<>();

	/**
	 * OPTIONAL
	 * Map of Snowflakes to attachment objects
	 * The ids and attachment objects
	 */
	public Map<String, DiscordAttachment> attachments = new HashMap<>();
}
