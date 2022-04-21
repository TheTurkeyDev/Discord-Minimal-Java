package dev.theturkey.discordminimaljava.objects;

public enum DiscordInteractionType implements IDEnum
{
	PONG(1),
	APPLICATION_COMMAND(2),
	MESSAGE_COMPONENT(3),
	APPLICATION_COMMAND_AUTOCOMPLETE(4);

	public final int id;

	DiscordInteractionType(int id)
	{
		this.id = id;
	}

	public static DiscordInteractionType getFromId(int id)
	{
		for(DiscordInteractionType type : DiscordInteractionType.values())
			if(type.id == id)
				return type;
		return DiscordInteractionType.PONG;
	}

	@Override
	public int getId()
	{
		return id;
	}
}
