package dev.theturkey.discordminimaljava;

import dev.theturkey.discordminimaljava.objects.DiscordReady;
import dev.theturkey.discordminimaljava.payloads.DiscordGatewayPayload;
import dev.theturkey.discordminimaljava.payloads.HeartBeatPayload;
import dev.theturkey.discordminimaljava.payloads.IdentifyPayload;
import dev.theturkey.discordminimaljava.payloads.ResumePayload;
import dev.theturkey.discordminimaljava.rest.DiscordAPI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class DiscordWebSocket extends WebSocketClient
{
	private final Timer heartBeatTimer = new Timer();
	private TimerTask heartBeat;
	private long seq;
	private String sessionId;
	private final long intents;
	private final int shardId;
	private boolean resume = false;
	private final int numShards;

	private final DiscordMinimal dm;

	public DiscordWebSocket(DiscordMinimal dm, String url, long intents, int shardId, int numShards) throws URISyntaxException
	{
		super(new URI(url));
		this.dm = dm;
		this.intents = intents;
		this.shardId = shardId;
		this.numShards = numShards;
	}

	@Override
	public void onOpen(ServerHandshake handShakeData)
	{
	}

	@Override
	public void onMessage(String data)
	{
		DiscordGatewayPayload message = DiscordAPI.GSON.fromJson(data, DiscordGatewayPayload.class);
		if(message.s != null)
			this.seq = message.s;

		switch(message.op)
		{
			case 0:
				if(message.t.equals("READY"))
					sessionId = DiscordAPI.GSON.fromJson(message.d, DiscordReady.class).sessionId;
				dm.onPayload(message);
				break;
			case 7:
			case 9:
				if(message.d != null)
					this.send(DiscordAPI.GSON.toJson(new ResumePayload(sessionId, seq)));
				break;
			case 10:
				this.startHeartbeat(message.d.getAsJsonObject().get("heartbeat_interval").getAsInt());
				if(resume)
					this.send(DiscordAPI.GSON.toJson(new ResumePayload(sessionId, seq)));
				else
					this.send(DiscordAPI.GSON.toJson(new IdentifyPayload(this.intents, shardId, this.numShards)));
				break;
			case 11:
				//Heartbeat ACK
				break;
			default:
				System.out.println("[Discord] Unknown OP Code: " + message.op);
		}
	}

	private void startHeartbeat(int heartbeatDelay)
	{
		if(heartBeat != null)
			heartBeat.cancel();

		heartBeat = new TimerTask()
		{
			@Override
			public void run()
			{
				DiscordWebSocket.this.send(DiscordAPI.GSON.toJson(new HeartBeatPayload(seq)));
			}
		};
		heartBeatTimer.schedule(heartBeat, heartbeatDelay);
	}

	@Override
	public void onClose(int code, String reason, boolean remote)
	{
		heartBeat.cancel();
		dm.onClose(code, reason, remote);

		if(reason.equals("Clientside closed!"))
			return;

		switch(code)
		{
			case -1, 1000, 1001, 1006, 4000, 4008, 4009 -> this.initReconnect();
			default ->
			{
				System.out.println("[DISCORD] Closed: " + code + " - " + reason);
				//this.initReconnectFull();
			}
		}
	}

	private void initReconnect()
	{
		this.resume = true;
		new Thread(this::reconnect).start();
	}

	@Override
	public void onError(Exception ex)
	{
		dm.onError(ex);
	}
}