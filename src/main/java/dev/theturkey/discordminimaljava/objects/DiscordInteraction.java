package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;
import dev.theturkey.discordminimaljava.rest.DiscordAPI;
import dev.theturkey.discordminimaljava.rest.DiscordAPIResponse;

public class DiscordInteraction
{
	/**
	 * Id of the interaction
	 */
	public String id;

	/**
	 * Id of the application this interaction is for
	 */
	@SerializedName("application_id")
	public String applicationId;

	/**
	 * The type of interaction
	 */
	public DiscordInteractionType type;

	/**
	 * OPTIONAL
	 * The command data payload
	 */
	public DiscordInteractionData data;

	/**
	 * The guild it was sent from
	 */
	@SerializedName("guild_id")
	public String guildId;

	/**
	 * OPTIONAL
	 * The channel it was sent from
	 */
	@SerializedName("channel_id")
	public String channelId;

	/**
	 * OPTIONAL
	 * Guild member data for the invoking user, including permissions
	 */
	public DiscordGuildMember member;

	/**
	 * OPTIONAL
	 * User object for the invoking user, if invoked in a DM
	 */
	public DiscordUser user;

	/**
	 * A continuation token for responding to the interaction
	 */
	public String token;

	/**
	 * Read-only property, always 1
	 */
	public int version;

	/**
	 * For components, the message they were attached to
	 * OPTIONAL
	 */
	public DiscordMessage message;

	public boolean isButton()
	{
		return this.data != null && this.data.componentType == DiscordComponentType.BUTTON;
	}

	public boolean isSelect()
	{
		return this.data != null && (
				this.data.componentType == DiscordComponentType.STRING_SELECT ||
						this.data.componentType == DiscordComponentType.USER_SELECT ||
						this.data.componentType == DiscordComponentType.ROLE_SELECT ||
						this.data.componentType == DiscordComponentType.MENTIONABLE_SELECT ||
						this.data.componentType == DiscordComponentType.CHANNEL_SELECT
		);
	}

	public boolean isAppCommand()
	{
		return this.type == DiscordInteractionType.APPLICATION_COMMAND;
	}

	public void update(DiscordInteractionResponseData data)
	{
		updateInteraction(DiscordInteractionCallbackType.UPDATE_MESSAGE, data);
	}

	public void respond(String message)
	{
		DiscordInteractionResponseData data = new DiscordInteractionResponseData();
		data.content = message;
		respond(data);
	}

	public void respondEphemeral(String message)
	{
		DiscordInteractionResponseData data = new DiscordInteractionResponseData();
		data.content = message;
		data.flags = DiscordMessageFlags.EPHEMERAL;
		respond(data);
	}

	public void respond(DiscordInteractionResponseData data)
	{
		updateInteraction(DiscordInteractionCallbackType.CHANNEL_MESSAGE_WITH_SOURCE, data);
	}

	public DiscordAPIResponse<DiscordMessage> editResponse(DiscordEditWebhookMessage editMessage)
	{
		return DiscordAPI.interactionEdit(this.applicationId, this.token, editMessage);
	}

	public void deferUpdate()
	{
		updateInteraction(DiscordInteractionCallbackType.DEFERRED_UPDATE_MESSAGE, null);
	}

	public void updateInteraction(DiscordInteractionCallbackType type, DiscordInteractionResponseData data)
	{
		DiscordInteractionResponse response = new DiscordInteractionResponse();
		response.type = type;
		response.data = data;
		DiscordAPI.interactionCallback(this.id, this.token, response);
	}

	public DiscordAPIResponse<DiscordMessage> sendMessageInChannel(DiscordMessageCreate message)
	{
		return DiscordAPI.createMessage(this.channelId, message);
	}
}
