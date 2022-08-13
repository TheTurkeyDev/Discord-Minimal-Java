package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordInteractionData
{
	/**
	 * The ID of the invoked command
	 */
	public String id;

	/**
	 * The name of the invoked command
	 */
	public String name;

	/**
	 * The type of the invoked command
	 */
	public int type;

	// public resolved ? resolved data	converted users + roles + channels	Application Command

	/**
	 * OPTIONAL
	 * The params + values from the user
	 */
	public DiscordApplicationCommandInteractionDataOption[] options;

	/**
	 * OPTIONAL
	 * The custom_id of the component
	 */
	@SerializedName("custom_id")
	public String customId;

	/**
	 * OPTIONAL
	 * The type of the component
	 */
	@SerializedName("component_type")
	public DiscordComponentType componentType;

	// public values ? array of select option values	the values the user selected	Component(Select)
	// public target_id ? snowflake	id the of user or message targetted by a user or message command	User Command, Message Command
}
