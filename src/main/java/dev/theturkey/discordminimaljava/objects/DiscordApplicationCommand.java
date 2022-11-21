package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * OPTIONAL
	 * Localization dictionary for name field. Values follow the same restrictions as name.
	 * <a href="https://discord.com/developers/docs/reference#locales">Available Locales</a>
	 */
	@SerializedName("name_localizations")
	public Map<String, String> nameLocalizations = new HashMap<>();

	/**
	 * 1-100 character description for CHAT_INPUT commands, empty string for USER and MESSAGE commands
	 */
	public String description;

	/**
	 * OPTIONAL
	 * Localization dictionary for description field. Values follow the same restrictions as description.
	 * <a href="https://discord.com/developers/docs/reference#locales">Available Locales</a>
	 */
	@SerializedName("description_localizations")
	public Map<String, String> descriptionLocalizations = new HashMap<>();

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
