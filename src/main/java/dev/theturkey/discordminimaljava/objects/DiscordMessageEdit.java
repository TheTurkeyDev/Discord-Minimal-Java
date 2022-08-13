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

	//allowed_mentions	allowed mention object	Allowed mentions for the message

	/**
	 * Components to include with the message
	 */
	public DiscordComponent[] components;

	//files[n]	file contents	Contents of the file being sent/edited. See Uploading Files
	//payload_json	string	JSON-encoded body of non-file params (multipart/form-data only). See Uploading Files
	//attachments	array of attachment objects	Attached files to keep and possible descriptions for new files. See Uploading Files
}
