package dev.theturkey.discordminimaljava.objects;

public class DiscordEmbedField
{
	/**
	 * Name of the field
	 */
	public String name;

	/**
	 * Value of the field
	 */
	public String value;

	/**
	 * OPTIONAL
	 * Whether or not this field should display inline
	 */
	public boolean inline;

	public DiscordEmbedField(String name, String value, boolean inline)
	{
		this.name = name;
		this.value = value;
		this.inline = inline;
	}
}
