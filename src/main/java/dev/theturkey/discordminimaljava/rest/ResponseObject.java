package dev.theturkey.discordminimaljava.rest;

import dev.theturkey.discordminimaljava.objects.DiscordAPIError;

public class ResponseObject<T>
{
	public T data;
	public DiscordAPIError error;

	public ResponseObject(T data, DiscordAPIError error)
	{
		this.data = data;
		this.error = error;
	}
}
