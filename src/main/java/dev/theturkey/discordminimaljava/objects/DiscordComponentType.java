package dev.theturkey.discordminimaljava.objects;

public enum DiscordComponentType implements IDEnum
{
	ACTION_ROW(1),
	BUTTON(2),
	SELECT_MENU(3),
	TEXT_INPUT(4);

	public final int id;

	DiscordComponentType(int id)
	{
		this.id = id;
	}

	@Override
	public int getId()
	{
		return id;
	}

	public static DiscordComponentType getFromId(int id)
	{
		for(DiscordComponentType type : DiscordComponentType.values())
			if(type.id == id)
				return type;
		return DiscordComponentType.ACTION_ROW;
	}
}
