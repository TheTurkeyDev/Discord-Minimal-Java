package dev.theturkey.discordminimaljava.payloads;

import com.google.gson.JsonElement;

public class DiscordGatewayPayload
{
	public int op;
	public JsonElement d;
	public Long s;
	public String t;
}
