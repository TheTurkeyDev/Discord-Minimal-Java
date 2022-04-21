package dev.theturkey.discordminimaljava.objects;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public interface EnumDeserializer<T extends IDEnum> extends JsonDeserializer<T>
{
	@Override
	default T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) throws JsonParseException
	{
		T t = null;

		return getFromId(json.getAsInt());
	}

	T getFromId(int id);
}