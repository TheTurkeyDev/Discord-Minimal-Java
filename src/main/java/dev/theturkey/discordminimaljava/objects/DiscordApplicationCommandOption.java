package dev.theturkey.discordminimaljava.objects;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscordApplicationCommandOption
{
	public DiscordApplicationCommandOptionType type;

	/**
	 * 1-32 character name, lowercase
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
	 * 1-100 character description
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
	 * choices for STRING, INTEGER, and NUMBER types for the user to pick from, max 25
	 */
	public List<DiscordApplicationCommandOptionChoice> choices = new ArrayList<>();

	/**
	 * If the parameter is required or optional--default false
	 */
	public boolean required;

	/**
	 * Array of application command option	if the option is a subcommand or subcommand group type, this nested options will be the parameters
	 */
	public List<DiscordApplicationCommandOption> options = new ArrayList<>();

	/**
	 * OPTIONAL
	 * If the option is a channel type, the channels shown will be restricted to these types
	 */
	public List<DiscordChannelType> channel_types = new ArrayList<>();

}
