package dev.theturkey.discordminimaljava.objects;

public enum DiscordChannelType implements IDEnum
{
	/**
	 * A text channel within a server
	 */
	GUILD_TEXT(0),
	/**
	 * A direct message between users
	 */
	DM(1),
	/**
	 * A voice channel within a server
	 */
	GUILD_VOICE(2),
	/**
	 * A direct message between multiple users
	 */
	GROUP_DM(3),
	/**
	 * An organizational category that contains up to 50 channels
	 */
	GUILD_CATEGORY(4),
	/**
	 * A channel that users can follow and crosspost into their own server
	 */
	GUILD_NEWS(5),
	/**
	 * A temporary sub-channel within a GUILD_NEWS channel
	 */
	GUILD_NEWS_THREAD(10),
	/**
	 * A temporary sub-channel within a GUILD_TEXT channel
	 */
	GUILD_PUBLIC_THREAD(11),
	/**
	 * a temporary sub-channel within a GUILD_TEXT channel that is only viewable by those invited and those with the MANAGE_THREADS permission
	 */
	GUILD_PRIVATE_THREAD(12),
	/**
	 * A voice channel for hosting events with an audience
	 */
	GUILD_STAGE_VOICE(13),
	/**
	 * The channel in a hub containing the listed servers
	 */
	GUILD_DIRECTORY(14),
	/**
	 * (still in development) a channel that can only contain threads;
	 */
	GUILD_FORUM(15);

	public final int id;

	DiscordChannelType(int id)
	{
		this.id = id;
	}

	public static DiscordChannelType getFromId(int i)
	{
		for(DiscordChannelType type : DiscordChannelType.values())
			if(type.id == i)
				return type;
		return DiscordChannelType.GUILD_TEXT;
	}

	@Override
	public int getId()
	{
		return this.id;
	}
}
