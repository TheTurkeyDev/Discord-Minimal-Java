package dev.theturkey.discordminimaljava.objects;

public enum DiscordApplicationCommandOptionType implements IDEnum
{
	SUB_COMMAND(1),
	SUB_COMMAND_GROUP(2),
	STRING(3),
	INTEGER(4), //	Any integer between -2^53 and 2^53
	BOOLEAN(5),
	USER(6),
	CHANNEL(7),    //Includes all channel types + categories
	ROLE(8),
	MENTIONABLE(9),    //Includes users and roles
	NUMBER(10),   //Any double between -2^53 and 2^53
	ATTACHMENT(11);

	public final int id;

	DiscordApplicationCommandOptionType(int id)
	{
		this.id = id;
	}

	public static DiscordApplicationCommandOptionType getFromId(int id)
	{
		for(DiscordApplicationCommandOptionType type : DiscordApplicationCommandOptionType.values())
			if(type.id == id)
				return type;
		return DiscordApplicationCommandOptionType.SUB_COMMAND;
	}

	@Override
	public int getId()
	{
		return id;
	}
}