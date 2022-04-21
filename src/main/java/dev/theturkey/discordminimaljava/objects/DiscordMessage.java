package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;
import dev.theturkey.discordminimaljava.rest.DiscordAPI;
import dev.theturkey.discordminimaljava.rest.DiscordAPIResponse;

public class DiscordMessage
{
	/**
	 * Id of the message
	 */
	public long id;

	/**
	 * Id of the channel the message was sent in
	 */
	@SerializedName("channel_id")
	public long channelId;

	/**
	 * OPTIONAL
	 * Id of the guild the message was sent in
	 */
	@SerializedName("guild_id")
	public long guildId;

	/**
	 * The author of this message(not guaranteed to be a valid user, see below)
	 */
	public DiscordUser author;

	/**
	 * OPTIONAL
	 * Partial guild member object, member properties for this message's author
	 */
	public DiscordGuildMember member;

	/**
	 * Contents of the message
	 */
	public String content;

	/**
	 * Timestamp when this message was sent
	 */
	public String timestamp;

	/**
	 * Timestamp when this message was edited(or null if never)
	 */
	@SerializedName("edited_timestamp")
	public String editedTimestamp;

	/**
	 * Whether this was a TTS message
	 */
	public boolean tts;

	/**
	 * Whether this message mentions everyone
	 */
	@SerializedName("mention_everyone")
	public boolean mentionEveryone;

	/**
	 * Users specifically mentioned in the message
	 */
	public DiscordUser[] mentions;
	//     public mention_roles	array of role object ids	roles specifically mentioned in this message
	//     public mention_channels ?**** array of channel mention objects	channels specifically mentioned in this message
	//     public attachments	array of attachment objects	any attached files
	//     public embeds	array of embed objects	any embedded content
	//     public reactions?: DiscordReaction;         reactions to the message
	//     public nonce ? integer or string	used for validating a message was sent
	//     public pinned	boolean	whether this message is pinned
	//     public webhook_id ? snowflake	if the message is generated by a webhook, this is the webhook's id
	//     public type integer	type of message
	//     public activity ? message activity object	sent with Rich Presence - related chat embeds
	//     public application ? partial application object	sent with Rich Presence - related chat embeds
	//     public application_id ? snowflake	if the message is a response to an Interaction, this is the id of the interaction's application

	/**
	 * OPTIONAL
	 * Data showing the source of a crosspost, channel follow add, pin, or reply message
	 */
	@SerializedName("message_reference")
	public DiscordMessageReference messageReference;

	//     public flags ? integer	message flags combined as a bitfield
	//     public referenced_message ?*****	? message object	the message associated with the message_reference
	//     public interaction ? message interaction object	sent if the message is a response to an Interaction
	//     public thread ? channel object	the thread that was started from this message, includes thread member object
	//     public components ? Array of message components	sent if the message contains components like buttons, action rows, or other interactive components
	//     public sticker_items ? array of message sticker item objects	sent if the message contains stickers
	//     public stickers ? array of sticker objects	Deprecated the stickers sent with the message


	public DiscordAPIResponse<DiscordMessage> reply(String message)
	{
		return this.reply(message, false);
	}

	public DiscordAPIResponse<DiscordMessage> reply(String message, boolean quote)
	{
		DiscordMessageCreate replyMessage = new DiscordMessageCreate();
		replyMessage.content = message;
		if(quote)
		{
			DiscordMessageReference reference = new DiscordMessageReference();
			reference.messageId = this.id;
			reference.channelId = this.channelId;
			replyMessage.messageReference = reference;
		}
		return DiscordAPI.createMessage(this.channelId, replyMessage);
	}

	public DiscordAPIResponse<DiscordMessage> sendMessageInChannel(String message)
	{
		DiscordMessageCreate toSend = new DiscordMessageCreate();
		toSend.content = message;
		return DiscordAPI.createMessage(this.channelId, toSend);
	}

	public DiscordAPIResponse<DiscordMessage> sendMessageInChannel(DiscordMessageCreate message)
	{
		return DiscordAPI.createMessage(this.channelId, message);
	}
}
