package dev.theturkey.discordminimaljava.objects;

public class DiscordMessageEdit
{
	/**
	 * The message contents (up to 2000 characters)
	 */
	public String content;

	/**
	 * Embedded rich content (up to 6000 characters)
	 */
	public DiscordEmbed[] embeds;

	/**
	 * Edit the <a href="https://discord.com/developers/docs/resources/channel#message-object-message-flags">flags</a> of a message (only SUPPRESS_EMBEDS can currently be set/unset)
	 */
	public int flags;
}
