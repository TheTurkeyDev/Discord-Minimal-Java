package dev.theturkey.discordminimaljava.objects;

public class DiscordIntents
{
	public static long GUILDS = 1;
	public static long GUILD_MEMBERS = 1 << 1;
	public static long GUILD_BANS = 1 << 2;
	public static long GUILD_EMOJIS_AND_STICKERS = 1 << 3;
	public static long GUILD_INTEGRATIONS = 1 << 4;
	public static long GUILD_WEBHOOKS = 1 << 5;
	public static long GUILD_INVITES = 1 << 6;
	public static long GUILD_VOICE_STATES = 1 << 7;
	public static long GUILD_PRESENCES = 1 << 8;
	public static long GUILD_MESSAGES = 1 << 9;
	public static long GUILD_MESSAGE_REACTIONS = 1 << 10;
	public static long GUILD_MESSAGE_TYPING = 1 << 11;
	public static long DIRECT_MESSAGES = 1 << 12;
	public static long DIRECT_MESSAGE_REACTIONS = 1 << 13;
	public static long DIRECT_MESSAGE_TYPING = 1 << 14;

}
