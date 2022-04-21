package dev.theturkey.discordminimaljava.rest;

public interface HttpResponseHandler
{
	void onResponse(int code, String body);
}
