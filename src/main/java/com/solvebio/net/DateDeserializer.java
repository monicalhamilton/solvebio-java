package com.solvebio.net;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class DateDeserializer implements JsonDeserializer<Date> {

	private static final String DATE_PATTERN_MS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private static final String DATE_PATTERN_S = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	private static final SimpleDateFormat DATE_FORMAT_MS = new SimpleDateFormat(
			DATE_PATTERN_MS);
	private static final SimpleDateFormat DATE_FORMAT_S = new SimpleDateFormat(
			DATE_PATTERN_S);

	public Date deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		try {
			// This seems hacky, but for now I need to pick the correct
			// formatter since I'm getting back created_at timestamps with
			// format 2014-07-23T22:58:25Z and updated_at timestamps with format
			// 2014-07-24T01:09:28.585Z
			String dateTimeString = json.getAsString();
			if (dateTimeString.length() == 24) {
				return DATE_FORMAT_MS.parse(dateTimeString);
			} else {
				return DATE_FORMAT_S.parse(dateTimeString);
			}

		} catch (ParseException e) {
			throw new JsonParseException("Could not deserialize json " + json
					+ " into java.util.Date.");
		}
	}
}
