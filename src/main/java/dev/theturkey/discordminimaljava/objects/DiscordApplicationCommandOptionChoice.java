package dev.theturkey.discordminimaljava.objects;

public class DiscordApplicationCommandOptionChoice
{
	/*
	1-100 character choice name
	 */
	public String name;

	/*
	string, integer, or double	value of the choice, up to 100 characters if string
	 */
	public Object value;

	public DiscordApplicationCommandOptionChoice(String name, Object value)
	{
		this.name = name;
		this.value = value;
	}
}
