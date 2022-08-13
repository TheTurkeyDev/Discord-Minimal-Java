package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordSelectOption
{
	/**
	 * The user-facing name of the option, max 100 characters
	 */
	public String label;

	/**
	 * The dev-defined value of the option, max 100 characters
	 */
	public String value;

	/**
	 * An additional description of the option, max 100 characters
	 * OPTIONAL
	 */
	public String description;

	//emoji?	partial emoji object	id, name, and animated

	/**
	 * Will render this option as selected by default
	 */
	@SerializedName("default")
	public boolean isDefault = false;

	public DiscordSelectOption(String label, String value)
	{
		this.label = label;
		this.value = value;
	}
}
