package dev.theturkey.discordminimaljava.objects;

public enum DiscordApplicationCommandType implements IDEnum
{
	CHAT_INPUT(1),
	USER(2),
	MESSAGE(3);

	public final int id;

	DiscordApplicationCommandType(int id)
	{
		this.id = id;
	}


	public static DiscordApplicationCommandType getFromId(int id)
	{
		for(DiscordApplicationCommandType type : DiscordApplicationCommandType.values())
			if(type.id == id)
				return type;
		return DiscordApplicationCommandType.CHAT_INPUT;
	}

	@Override
	public int getId()
	{
		return this.id;
	}
}
