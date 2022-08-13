package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordRoleTag
{
	/**
	 * OPTIONAL
	 * The id of the bot this role belongs to
	 */
	@SerializedName("bot_id")
	public String botId;

	/**
	 * OPTIONAL
	 * The id of the integration this role belongs to
	 */
	@SerializedName("integration_id")
	public String integrationId;

	/**
	 * OPTIONAL
	 * Whether this is the guild's premium subscriber role
	 */
	@SerializedName("premium_subscriber")
	public boolean premiumSubscriber;
}
