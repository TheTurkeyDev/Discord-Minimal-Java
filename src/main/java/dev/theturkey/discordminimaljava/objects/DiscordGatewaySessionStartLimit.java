package dev.theturkey.discordminimaljava.objects;

import com.google.gson.annotations.SerializedName;

public class DiscordGatewaySessionStartLimit
{
	/**
	 * The total number of session starts the current user is allowed
	 */
	public int total;
	/**
	 * The remaining number of session starts the current user is allowed
	 */
	public int remaining;
	/**
	 * The number of milliseconds after which the limit resets
	 */
	@SerializedName("reset_after")
	public long resetAfter;
	/**
	 * The number of identify requests allowed per 5 seconds
	 */
	@SerializedName("max_concurrency")
	public int maxConcurrency;
}
