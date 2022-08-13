package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordMessageCreate
{
	/**
	 * The message contents(up to 2000 characters)
	 */
	public String content;

	//public tts?:boolean;                                   // true if this is a TTS message
	//public file: file contents	the contents of the file being sent

	/**
	 * OPTIONAL
	 * Embedded rich content(up to 6000 characters)
	 */
	public DiscordEmbed[] embeds;

	//public payload_json?:string;                            // JSON encoded body of non - file params
	//public allowed_mentions	allowed mention object	allowed mentions for the message

	/**
	 * OPTIONAL
	 * Include to make your message a reply
	 */
	@SerializedName("message_reference")
	public DiscordMessageReference messageReference;

	/**
	 * The components to include with the message
	 */
	public DiscordComponent[] components;

	/**
	 * OPTIONAL
	 * IDs of up to 3 stickers in the server to send in the message
	 */
	public long[] sticker_ids;
}
