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

	/**
	 * OPTIONAL
	 * Converted users + roles + channels	Application Command
	 */
	public DiscordInteractionResolvedData resolved;

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

	/**
	 * OPTIONAL
	 * Values the user selected in a select menu component
	 */
	public String[] values;

	/**
	 * OPTIONAL
	 * ID the of user or message targeted by a user or message command	User Command, Message Command
	 */
	@SerializedName("target_id")
	public String targetId;
}
