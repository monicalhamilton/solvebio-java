package com.solvebio.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Maps;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.solvebio.SolveBioClient;
import com.solvebio.exception.APIConnectionException;
import com.solvebio.exception.APIException;
import com.solvebio.exception.AuthenticationException;
import com.solvebio.exception.InvalidRequestException;

public abstract class APIResource extends ObjectResource {

	public static final Gson GSON = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.registerTypeAdapter(RawJsonObject.class,
					new RawJsonObjectDeserializer())
			.registerTypeAdapter(Date.class, new DateDeserializer()).create();

	public static String camelCaseToUnderscoreCase(String name) {
		StringBuilder translation = new StringBuilder();
		for (int i = 0; i < name.length(); i++) {
			char character = name.charAt(i);
			// Upper case characters need an underscore before them, unless it's
			// the first
			if (Character.isUpperCase(character) && translation.length() != 0) {
				translation.append("_");
			}
			translation.append(character);
		}
		// Make all lowercase
		return translation.toString().toLowerCase();
	}

	private static String singleClassName(Class<? extends APIResource> clazz) {
		return camelCaseToUnderscoreCase(clazz.getSimpleName()
				.replace("$", " "));
	}

	private static String pluralClassName(Class<? extends APIResource> clazz) {
		// add support for proper pluralization of classes that end in "y", such
		// as com.solvebio.model.Depository
		return String.format("%ss", singleClassName(clazz)).replaceFirst("ys$",
				"ies");
	}

	protected static String classURL(Class<? extends APIResource> clazz) {
		return String.format("%s/%s/%s", SolveBioClient.API_HOST,
				SolveBioClient.API_VERSION, pluralClassName(clazz));
	}

	protected static String instanceURL(Class<? extends APIResource> clazz,
			String id) throws InvalidRequestException {
		return String.format("%s/%s", classURL(clazz), id);
	}

	protected static String nestedURL(Class<? extends APIResource> clazz,
			String id, String path) throws InvalidRequestException {
		return String.format("%s/%s/%s", classURL(clazz), id, path);
	}

	protected static String nestedURL(Class<? extends APIResource> clazz,
			String id, Class<? extends APIResource> pathClazz)
			throws InvalidRequestException {
		return String.format("%s/%s/%s", classURL(clazz), id,
				pluralClassName(pathClazz));
	}

	public static final String CHARSET = "UTF-8";

	protected enum RequestMethod {
		GET, POST
	}

	private static String urlEncode(String str)
			throws UnsupportedEncodingException {
		if (str == null) {
			return null;
		} else {
			return URLEncoder.encode(str, CHARSET);
		}
	}

	private static String urlEncodePair(String k, String v)
			throws UnsupportedEncodingException {
		return String.format("%s=%s", urlEncode(k), urlEncode(v));
	}

	// Adds Authorization Token header
	// (for help, see:
	// http://www.django-rest-framework.org/api-guide/authentication#tokenauthentication)
	static Map<String, String> getHeaders(String apiKey) {
		Map<String, String> headers = Maps.newHashMap();
		headers.put("Accept-Charset", CHARSET);
		headers.put("Authorization", "Token " + apiKey);
		return headers;
	}

	private static java.net.HttpURLConnection createSolveBioConnection(
			String url, String apiKey) throws IOException {
		URL apiURL = new URL(url);
		java.net.HttpURLConnection conn = (java.net.HttpURLConnection) apiURL
				.openConnection();
		conn.setConnectTimeout(30 * 1000);
		conn.setReadTimeout(80 * 1000);
		conn.setUseCaches(false);
		for (Map.Entry<String, String> header : getHeaders(apiKey).entrySet()) {
			conn.setRequestProperty(header.getKey(), header.getValue());
		}
		return conn;
	}

	private static String formatURL(String url, String query) {
		if (query == null || query.isEmpty()) {
			return url;
		} else {
			// In some cases, URL can already contain a question mark
			String separator = url.contains("?") ? "&" : "?";
			return String.format("%s%s%s", url, separator, query);
		}
	}

	private static java.net.HttpURLConnection createGetConnection(String url,
			String query, String apiKey) throws IOException,
			APIConnectionException {
		String getURL = formatURL(url, query);
		java.net.HttpURLConnection conn = createSolveBioConnection(getURL,
				apiKey);
		conn.setRequestMethod("GET");
		return conn;
	}

	private static java.net.HttpURLConnection createPostConnection(String url,
			String query, String apiKey) throws IOException,
			APIConnectionException {
		java.net.HttpURLConnection conn = createSolveBioConnection(apiKey, url);

		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", String.format(
				"application/x-www-form-urlencoded;charset=%s", CHARSET));

		OutputStream output = null;
		try {
			output = conn.getOutputStream();
			output.write(query.getBytes(CHARSET));
		} finally {
			if (output != null) {
				output.close();
			}
		}
		return conn;
	}

	private static String createQuery(String apiKey, Map<String, Object> params)
			throws UnsupportedEncodingException, InvalidRequestException {
		Map<String, String> flatParams = flattenParams(apiKey, params);
		StringBuilder queryStringBuffer = new StringBuilder();
		for (Map.Entry<String, String> entry : flatParams.entrySet()) {
			if (queryStringBuffer.length() > 0) {
				queryStringBuffer.append("&");
			}
			queryStringBuffer.append(urlEncodePair(entry.getKey(),
					entry.getValue()));
		}
		return queryStringBuffer.toString();
	}

	private static Map<String, String> flattenParams(String apiKey,
			Map<String, Object> params) throws InvalidRequestException {
		if (params == null) {
			return Maps.newHashMap();
		}
		Map<String, String> flatParams = Maps.newHashMap();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof Map<?, ?>) {
				Map<String, Object> flatNestedMap = Maps.newHashMap();
				Map<?, ?> nestedMap = (Map<?, ?>) value;
				for (Map.Entry<?, ?> nestedEntry : nestedMap.entrySet()) {
					flatNestedMap.put(
							String.format("%s[%s]", key, nestedEntry.getKey()),
							nestedEntry.getValue());
				}
				flatParams.putAll(flattenParams(apiKey, flatNestedMap));
			} else if ("".equals(value)) {
				throw new InvalidRequestException("'" + key
						+ "' cannot be an empty String. Use null instead.",
						null);
			} else if (value == null) {
				flatParams.put(key, "");
			} else if (value != null) {
				flatParams.put(key, value.toString());
			}
		}
		return flatParams;
	}

	private static class Error {
		String detail;
	}

	private static String getResponseBody(InputStream responseStream)
			throws IOException {
		// \A is the beginning of
		// the stream boundary
		Scanner scanner = new Scanner(responseStream, CHARSET);
		String rBody = scanner.useDelimiter("\\A").next();
		scanner.close();
		responseStream.close();
		return rBody;
	}

	private static SolveBioResponse makeURLConnectionRequest(
			APIResource.RequestMethod method, String url, String query,
			String apiKey) throws APIConnectionException {
		java.net.HttpURLConnection conn = null;
		try {
			switch (method) {
			case GET:
				conn = createGetConnection(url, query, apiKey);
				break;
			case POST:
				conn = createPostConnection(url, query, apiKey);
				break;
			default:
				throw new APIConnectionException(String.format(
						"Unrecognized HTTP method %s.", method));
			}
			// trigger the request
			int rCode = conn.getResponseCode();
			String rBody = null;
			Map<String, List<String>> headers;

			if (rCode >= 200 && rCode < 300) {
				rBody = getResponseBody(conn.getInputStream());
			} else {
				rBody = getResponseBody(conn.getErrorStream());
			}
			headers = conn.getHeaderFields();
			return new SolveBioResponse(rCode, rBody, headers);

		} catch (IOException e) {
			throw new APIConnectionException(String.format(
					"Could not connect to SolveBio API (%s). ",
					SolveBioClient.API_HOST), e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	protected static <T> T request(APIResource.RequestMethod method,
			String url, Map<String, Object> params, Class<T> clazz,
			String apiKey) throws AuthenticationException,
			InvalidRequestException, APIConnectionException, APIException {
		return _request(method, url, params, clazz, apiKey);
	}

	private static <T> T _request(APIResource.RequestMethod method, String url,
			Map<String, Object> params, Class<T> clazz, String apiKey)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException {
		System.out.println(String.format("Going to query url %s.", url));
		String query;

		try {
			query = createQuery(apiKey, params);
		} catch (UnsupportedEncodingException e) {
			throw new InvalidRequestException("Unable to encode parameters to "
					+ CHARSET, e);
		}

		SolveBioResponse response = makeURLConnectionRequest(method, url,
				query, apiKey);
		int rCode = response.responseCode;
		String rBody = response.responseBody;
		if (rCode < 200 || rCode >= 300) {
			handleAPIError(rBody, rCode);
		}
		return GSON.fromJson(rBody, clazz);
	}

	private static void handleAPIError(String rBody, int rCode)
			throws InvalidRequestException, AuthenticationException,
			APIException {
		// Some error responses do not come back as Json...
		// For example "The requested URL /v1/depository_versions/2.0.0-1 was
		// not found on this server." came back as html...

		// I assume this is breaking the contract of what solvebio agreed to
		// send (json), but for now I'll try to parse the html
		String errorDetail = "";
		try {
			Error error = GSON.fromJson(rBody, APIResource.Error.class);
			errorDetail = error.detail;
		} catch (JsonSyntaxException e) {
			// If not json, try html
			Document doc = Jsoup.parse(rBody);
			Element body = doc.body();
			Elements paragraphs = body.getElementsByTag("p");
			if (paragraphs.size() == 1) {
				// Likely the error detail is in the first paragraph
				// This is a hack, but since I don't know the actual error
				// contract, this is a temporary workaround.
				Element paragraph = paragraphs.iterator().next();
				errorDetail = paragraph.childNode(0).toString();
			}
		}
		switch (rCode) {
		case 400:
			throw new InvalidRequestException(errorDetail, rCode, null);
		case 401:
			throw new AuthenticationException(errorDetail, rCode);
		case 404:
			throw new InvalidRequestException(errorDetail, rCode, null);
		case 501:
			throw new AuthenticationException(errorDetail, rCode);
		case 403:
			throw new AuthenticationException(errorDetail, rCode);
		default:
			throw new APIException(errorDetail, rCode, null);
		}
	}
}
