package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordButton extends DiscordComponent
{
	/**
	 * One of button styles
	 */
	public DiscordButtonStyle style;

	/**
	 * Text that appears on the button, max 80 characters
	 * OPTIONAL
	 */
	public String label;

	//public emoji?	partial emoji	name, id, and animated

	/**
	 * A developer-defined identifier for the button, max 100 characters
	 * OPTIONAL (sorta)
	 */
	@SerializedName("custom_id")
	public String customId;

	/**
	 * A url for link-style buttons
	 * OPTIONAL (sorta)
	 */
	public String url;

	/**
	 * whether the button is disabled (default false)
	 */
	public boolean disabled = false;

	public DiscordButton()
	{
		super(DiscordComponentType.BUTTON);
	}
}
