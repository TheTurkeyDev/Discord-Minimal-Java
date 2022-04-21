package dev.theturkey.discordminimaljava.objects;

import com.google.gson.JsonElement;

public class DiscordAPIError
{
	public int code;
	public String message;
	public JsonElement errors;
}
