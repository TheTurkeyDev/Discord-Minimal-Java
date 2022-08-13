package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordTextInput extends DiscordComponent
{
	/**
	 * A developer-defined identifier for the input, max 100 characters
	 */
	@SerializedName("custom_id")
	public String customId;

	/**
	 * The Text Input Style
	 */
	public DiscordTextInputStyle style;

	/**
	 * The label for this component, max 45 characters
	 */
	public String label;

	/**
	 * The minimum input length for a text input, min 0, max 4000
	 * OPTIONAL
	 */
	@SerializedName("min_length")
	public int minLength;

	/**
	 * The maximum input length for a text input, min 1, max 4000
	 * OPTIONAL
	 */
	@SerializedName("max_length")
	public int maxLength;

	/**
	 * Whether this component is required to be filled, default true
	 * OPTIONAL
	 */
	public boolean required = true;

	/**
	 * A pre-filled value for this component, max 4000 characters
	 * OPTIONAL
	 */
	public String value;

	/**
	 * Custom placeholder text if the input is empty, max 100 characters
	 * OPTIONAL
	 */
	public String placeholder;

	public DiscordTextInput()
	{
		super(DiscordComponentType.TEXT_INPUT);
	}
}
