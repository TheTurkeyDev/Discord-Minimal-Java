package dev.theturkey.discordminimaljava.objects;

import java.util.Arrays;

public class DiscordEmbed
{
	/**
	 * OPTIONAL
	 * Title of embed
	 */
	public String title;

	/**
	 * OPTIONAL
	 * <a href="https://discord.com/developers/docs/resources/channel#embed-object-embed-types">Type of embed</a> (always "rich" for webhook embeds)
	 */
	public String type;

	/**
	 * OPTIONAL
	 * Description of embed
	 */
	public String description;

	/**
	 * OPTIONAL
	 * Url of embed
	 */
	public String url;

	/**
	 * OPTIONAL
	 * Timestamp of embed content
	 * ISO8601
	 */
	public String timestamp;

	/**
	 * OPTIONAL
	 * color code of the embed
	 */
	public int color;

	/*
	 * footer?	embed footer object	footer information
	 * image?	embed image object	image information
	 * thumbnail?	embed thumbnail object	thumbnail information
	 * video?	embed video object	video information
	 * provider?	embed provider object	provider information
	 * author?	embed author object	author information
	 */

	/**
	 * OPTIONAL
	 * Fields information
	 */
	public DiscordEmbedField[] fields = new DiscordEmbedField[0];

	public DiscordEmbed addField(String key, String value)
	{
		return this.addField(key, value, false);
	}

	public DiscordEmbed addField(String key, String value, boolean inline)
	{
		this.fields = Arrays.copyOf(fields, fields.length + 1);
		fields[fields.length - 1] = new DiscordEmbedField(key, value, inline);
		return this;
	}
}
