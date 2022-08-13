package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordGuildRoleCreateResponse
{
	/**
	 * The id of the guild
	 */
	@SerializedName("guild_id")
	public String guildId;

	/**
	 * The role created
	 */
	public DiscordRole role;
}
