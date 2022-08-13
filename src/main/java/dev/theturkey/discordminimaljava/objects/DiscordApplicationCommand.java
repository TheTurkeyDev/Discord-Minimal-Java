package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DiscordApplicationCommand
{
	/**
	 * unique id of the command
	 */
	public String id;

	/**
	 * the type of command, defaults 1 if not set
	 */
	public DiscordApplicationCommandType type;

	/**
	 * unique id of the parent application
	 */
	@SerializedName("application_id")
	public String applicationId;

	/**
	 * guild id of the command, if not global
	 */
	@SerializedName("guild_id")
	public String guildId;

	/**
	 * 1-32 character name
	 * lowercase
	 */
	public String name;

	/**
	 * 1-100 character description for CHAT_INPUT commands, empty string for USER and MESSAGE commands
	 */
	public String description;

	/**
	 * the parameters for the command, max 25
	 */
	public List<DiscordApplicationCommandOption> options = new ArrayList<>();

	/**
	 * whether the command is enabled by default when the app is added to a guild
	 */
	@SerializedName("default_permission")
	public boolean defaultPermission = true;

	/**
	 * autoincrementing version identifier updated during substantial record changes
	 */
	public long version;
}
