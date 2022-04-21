package dev.theturkey.discordminimaljava.payloads;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.theturkey.discordminimaljava.DiscordMinimal;

public class IdentifyPayload extends DiscordGatewayPayload
{

	public IdentifyPayload(long intents, int shardId, int numShards)
	{
		super();
		this.op = 2;
		JsonObject data = new JsonObject();
		data.addProperty("token", DiscordMinimal.token == null ? "" : DiscordMinimal.token);
		data.addProperty("intents", intents);
		JsonArray shardArr = new JsonArray();
		shardArr.add(shardId);
		shardArr.add(numShards);
		data.add("shard", shardArr);

		JsonObject properties = new JsonObject();
		properties.addProperty("$os", System.getProperty("os.name"));
		properties.addProperty("$os", "discord-minimal-java");
		properties.addProperty("$device", "discord-minimal-java");
		data.add("properties", properties);
		this.d = data;
	}
}