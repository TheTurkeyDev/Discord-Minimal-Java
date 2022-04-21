package dev.theturkey.discordminimaljava.payloads;

import com.google.gson.JsonObject;
import dev.theturkey.discordminimaljava.DiscordMinimal;

public class ResumePayload extends DiscordGatewayPayload
{
	public ResumePayload(String sessionId, long seq)
	{
		super();
		this.op = 6;
		JsonObject data = new JsonObject();
		data.addProperty("token", DiscordMinimal.token == null ? "" : DiscordMinimal.token);
		data.addProperty("session_id", sessionId);
		data.addProperty("seq", seq);
		this.d = data;
	}
}
