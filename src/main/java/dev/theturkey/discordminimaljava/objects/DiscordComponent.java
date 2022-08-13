package dev.theturkey.discordminimaljava.objects;

public abstract class DiscordComponent
{
	/**
	 * Component type
	 */
	public DiscordComponentType type;

	public DiscordComponent(DiscordComponentType type)
	{
		this.type = type;
	}
}
