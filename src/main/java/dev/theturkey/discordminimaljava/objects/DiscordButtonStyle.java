package dev.theturkey.discordminimaljava.objects;

public enum DiscordButtonStyle implements IDEnum
{
	/**
	 * Blurple
	 */
	PRIMARY(1),
	/**
	 * Grey
	 */
	SECONDARY(2),
	/**
	 * Green
	 */
	SUCCESS(3),
	/**
	 * Red
	 */
	DANGER(4),
	/**
	 * Grey, navigates to a URL
	 */
	Link(5);

	private final int id;

	DiscordButtonStyle(int id){
		this.id = id;
	}

	public static DiscordButtonStyle getFromId(int id)
	{
		for(DiscordButtonStyle type : DiscordButtonStyle.values())
			if(type.id == id)
				return type;
		return DiscordButtonStyle.PRIMARY;
	}

	@Override
	public int getId()
	{
		return this.id;
	}
}
