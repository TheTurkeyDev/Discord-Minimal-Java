package dev.theturkey.discordminimaljava.objects;

public class DiscordEditWebhookMessage
{
	/**
	 * The message contents (up to 2000 characters)
	 */
	public String content;

	/**
	 * Array of up to 10 embed objects
	 * Embedded rich content
	 */
	public DiscordEmbed[] embeds;

	//allowed_mentions	allowed mention object	Allowed mentions for the message
	//components *	array of message component	the components to include with the message
	//files[n] **	file contents	the contents of the file being sent/edited
	//payload_json **	string	JSON encoded body of non-file params (multipart/form-data only)
	//attachments **	array of partial attachment objects	attached files to keep and possible descriptions for new files
}
