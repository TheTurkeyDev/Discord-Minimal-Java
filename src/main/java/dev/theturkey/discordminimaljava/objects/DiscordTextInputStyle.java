package dev.theturkey.discordminimaljava.objects;

public enum DiscordTextInputStyle implements IDEnum
{
	/**
	 * A single-line input
	 */
	SHORT(1),
	/**
	 * A multi-line input
	 */
	PARAGRAPH(2);

	private final int id;

	DiscordTextInputStyle(int id){
		this.id = id;
	}

	public static DiscordTextInputStyle getFromId(int id)
	{
		for(DiscordTextInputStyle type : DiscordTextInputStyle.values())
			if(type.id == id)
				return type;
		return DiscordTextInputStyle.SHORT;
	}

	@Override
	public int getId()
	{
		return this.id;
	}
}

