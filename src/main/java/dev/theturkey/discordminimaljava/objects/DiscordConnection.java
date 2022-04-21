package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordConnection
{
	/**
	 * Id of the connection account
	 */
	public String id;

	/**
	 * The username of the connection account
	 */
	public String name;

	/**
	 * The service of the connection (twitch, youtube)
	 */
	public String type;

	/**
	 * OPTIONAL
	 * Whether the connection is revoked
	 */
	public boolean revoked;

	// integrations?	array	an array of partial server integrations

	/**
	 * Whether the connection is verified
	 */
	public boolean verified;

	/**
	 * Whether friend sync is enabled for this connection
	 */
	@SerializedName("friend_sync")
	public boolean friendSync;

	/**
	 * Whether activities related to this connection will be shown in presence updates
	 */
	@SerializedName("show_activity")
	public boolean showActivity;

	/**
	 * <a href="https://discord.com/developers/docs/resources/user#connection-object-visibility-types">visibility</a> of this connection
	 */
	public int visibility;
}
