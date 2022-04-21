package dev.theturkey.discordminimaljava.rest;

public class DiscordAPIRequestData
{
	public String urlGroup;
	public String url;
	public String reqType;
	public String body;
	public HttpResponseWrapper response;

	// Time until we stop ignoring this request
	public long holdTime;

	public DiscordAPIRequestData(String urlGroup, String url, String reqType, String body, HttpResponseWrapper response)
	{
		this.urlGroup = urlGroup;
		this.url = url;
		this.reqType = reqType;
		this.body = body;
		this.response = response;
	}

	public void onComplete(int code, String body)
	{
		if(response != null)
			response.resolveCall(code, body);
	}
}
