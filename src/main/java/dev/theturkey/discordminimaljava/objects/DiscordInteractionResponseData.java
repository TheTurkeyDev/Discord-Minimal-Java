package dev.theturkey.discordminimaljava.objects;

public class DiscordInteractionResponseData
{
	/**
	 * OPTIONAL
	 * Is the response TTS
	 */
	public boolean tts;

	/**
	 * OPTIONAL
	 * Message content
	 */
	public String content;

	/**
	 * OPTIONAL
	 * Supports up to 10 embeds
	 */
	public DiscordEmbed[] embeds;

	//public allowed_mentions? allowed mentions	allowed mentions object

	/**
	 * OPTIONAL
	 * Interaction callback data flags
	 */
	public long flags;

	/**
	 * OPTIONAL
	 * Message components
	 */
	public DiscordComponent[] components;
}
