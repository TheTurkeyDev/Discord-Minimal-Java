package dev.theturkey.discordminimaljava.rest;

public interface PendingResponseObject<T>
{
	void onResponse(ResponseObject<T> resp);
}

