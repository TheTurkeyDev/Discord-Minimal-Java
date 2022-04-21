package dev.theturkey.discordminimaljava.payloads;

import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;

public class HeartBeatPayload extends DiscordGatewayPayload
{

	public HeartBeatPayload(long seq)
	{
		super();
		this.op = 1;
		this.d = seq == -1 ? JsonNull.INSTANCE : new JsonPrimitive(seq);
	}
}
