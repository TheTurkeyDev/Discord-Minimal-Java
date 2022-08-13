package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordSelectMenu extends DiscordComponent
{
	/**
	 * A developer-defined identifier for the select menu, max 100 characters
	 */
	@SerializedName("custom_id")
	public String customId;

	/**
	 * Array of select options	the choices in the select, max 25
	 */
	public DiscordSelectOption[] options;

	/**
	 * Custom placeholder text if nothing is selected, max 150 characters
	 * OPTIONAL
	 */
	public String placeholder;

	/**
	 * The minimum number of items that must be chosen; default 1, min 0, max 25
	 * OPTIONAL
	 */
	@SerializedName("min_values")
	public int minValues = 1;

	/**
	 * The maximum number of items that can be chosen; default 1, max 25
	 * OPTIONAL
	 */
	@SerializedName("max_values")
	public int maxValues = 1;

	/**
	 * Disable the select, default false
	 */
	public boolean disabled = false;

	public DiscordSelectMenu(String customId, DiscordSelectOption[] options)
	{
		super(DiscordComponentType.SELECT_MENU);
		this.customId = customId;
		this.options = options;
	}
}
