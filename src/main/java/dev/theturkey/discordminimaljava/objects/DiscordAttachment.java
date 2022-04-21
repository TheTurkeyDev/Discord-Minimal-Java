package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordAttachment
{
	/**
	 * Attachment id
	 */
	public long id;

	/**
	 * 	Name of file attached
	 */
	public String filename;

	/**
	 * OPTIONAL
	 * Description for the file
	 */
	public String description;

	/**
	 * OPTIONAL
	 * The attachment's media type
	 */
	@SerializedName("content_type")
	public String contentType;

	/**
	 *	Size of file in bytes
	 */
	public int size;

	/**
	 * Source url of file
	 */
	public String url;

	/**
	 * A proxied url of file
	 */
	@SerializedName("proxy_url")
	public String proxyUrl;

	/**
	 * OPTIONAL
	 * Height of file (if image)
	 */
	public int height;

	/**
	 * OPTIONAL
	 * Width of file (if image)
	 */
	public int width;

	/**
	 * OPTIONAL
	 * Whether this attachment is ephemeral
	 */
	public boolean ephemeral;
}
