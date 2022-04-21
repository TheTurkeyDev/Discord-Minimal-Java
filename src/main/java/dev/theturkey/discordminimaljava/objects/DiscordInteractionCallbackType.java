package dev.theturkey.discordminimaljava.objects;

public enum DiscordInteractionCallbackType implements IDEnum
{
	PONG(1),
	CHANNEL_MESSAGE_WITH_SOURCE(4),
	DEFERRED_CHANNEL_MESSAGE_WITH_SOURCE(5),
	DEFERRED_UPDATE_MESSAGE(6),
	UPDATE_MESSAGE(7);

	public final int id;

	DiscordInteractionCallbackType(int id)
	{
		this.id = id;
	}

	public static DiscordInteractionCallbackType getFromId(int id)
	{
		for(DiscordInteractionCallbackType type : DiscordInteractionCallbackType.values())
			if(type.id == id)
				return type;
		return DiscordInteractionCallbackType.PONG;
	}

	@Override
	public int getId()
	{
		return id;
	}
}
