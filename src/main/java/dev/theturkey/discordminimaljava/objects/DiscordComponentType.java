package dev.theturkey.discordminimaljava.objects;

public enum DiscordComponentType implements IDEnum
{
	ACTION_ROW(1),
	BUTTON(2),
	/**
	 * A select menu for picking from choices
	 */
	STRING_SELECT(3),
	/**
	 * A text input object
	 */
	TEXT_INPUT(4),
	/**
	 * Select menu for users
	 */
	USER_SELECT(5),
	/**
	 * Select menu for roles
	 */
	ROLE_SELECT(6),
	/**
	 * Select menu for mentionables (users and roles)
	 */
	MENTIONABLE_SELECT(7),
	/**
	 * Select menu for channels
	 */
	CHANNEL_SELECT(8);

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
