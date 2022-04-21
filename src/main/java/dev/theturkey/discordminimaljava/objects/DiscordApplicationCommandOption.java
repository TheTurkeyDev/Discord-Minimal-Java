package dev.theturkey.discordminimaljava.objects;


import java.util.ArrayList;
import java.util.List;

public class DiscordApplicationCommandOption
{
	public DiscordApplicationCommandOptionType type;

	/**
	 * 1-32 character name, lowercase
	 */
	public String name;

	/**
	 * 1-100 character description
	 */
	public String description;

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

	/*
	channel_types?	array of channel types	if the option is a channel type, the channels shown will be restricted to these types
	 */
}
