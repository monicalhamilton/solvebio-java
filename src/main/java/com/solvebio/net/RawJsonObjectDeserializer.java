package com.solvebio.net;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class RawJsonObjectDeserializer implements JsonDeserializer<RawJsonObject> {
	public RawJsonObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		RawJsonObject object = new RawJsonObject();
		object.json = json.getAsJsonObject();
		return object;
	}

}
