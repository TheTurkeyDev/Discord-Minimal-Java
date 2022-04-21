package dev.theturkey.discordminimaljava.objects;

public class DiscordApplicationCommandInteractionDataOption
{
	/**
	 * the name of the parameter
	 */
	public String name;

	/**
	 * Value of <a href="https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-option-type">application command option type</a>
	 */
	public int type;

	/**
	 * OPTIONAL
	 * The value of the option resulting from user input
	 * string, integer, or double
	 */
	public Object value;

	//options?	array of application command interaction data option	present if this option is a group or subcommand

	/**
	 * OPTIONAL
	 * true if this option is the currently focused option for autocomplete
	 */
	public boolean focused;

	public String getValueAsString()
	{
		if(value instanceof String)
			return (String) value;
		return "";
	}

	public int getValueAsInteger()
	{
		if(value instanceof Integer)
			return (int) value;
		return -1;
	}

	public double getValueAsDouble()
	{
		if(value instanceof Double)
			return (double) value;
		return -1;
	}
}
