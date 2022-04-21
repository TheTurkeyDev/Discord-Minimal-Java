package dev.theturkey.discordminimaljava.rest;

public class DiscordAPIResponse<T>
{
	private PendingResponseObject<T> pendingResponse;

	public void resolveCall(ResponseObject<T> resp)
	{
		if(pendingResponse != null)
			pendingResponse.onResponse(resp);
	}

	public DiscordAPIResponse<T> onResponse(PendingResponseObject<T> pendingResponse)
	{
		this.pendingResponse = pendingResponse;
		return this;
	}
}
