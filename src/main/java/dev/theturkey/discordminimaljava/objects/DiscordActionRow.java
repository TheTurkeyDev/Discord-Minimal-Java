package dev.theturkey.discordminimaljava.objects;

public class DiscordActionRow extends DiscordComponent
{
	/**
	 * The components to include with the message
	 */
	public DiscordComponent[] components;

	public DiscordActionRow(DiscordComponent[] components)
	{
		super(DiscordComponentType.ACTION_ROW);
		this.components = components;
	}
}
