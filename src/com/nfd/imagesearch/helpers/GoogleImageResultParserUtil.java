package com.nfd.imagesearch.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.JsonReader;
import android.util.JsonToken;

/*
 * 
 */

public class GoogleImageResultParserUtil {
	
	private static final String RESPONSE_DATA_KEY = "responseData";
	private static final String RESULTS_KEY = "results";
	private static final String IMG_WIDTH_KEY = "width";
	private static final String IMG_URL_KEY = "url";
	private static final String CURSOR_KEY = "cursor";
	private static final String MORE_RESULTS_URL_KEY = "moreResultsUrl";
	private static final String CONTENT_KEY = "content";
	
	public GoogleImageResultParserUtil() {
		
	}
	
	public List<GoogleImageResult> readImageResults(JSONObject json) throws JSONException {
		List<GoogleImageResult> images = new ArrayList<GoogleImageResult>();
		JSONObject responseData = json.getJSONObject(RESPONSE_DATA_KEY);
		JSONArray resultsArray = responseData.getJSONArray(RESULTS_KEY);
		for(int i=0; i<resultsArray.length(); i++) {
			JSONObject result = (JSONObject) resultsArray.get(i);
			images.add(readImageObject(result));
		}
		return images;
	}
	
	public GoogleImageResult readImageObject(JSONObject obj) throws JSONException{
		GoogleImageResult image = new GoogleImageResult();
//		image.fullUrl = obj.getString(IMG_URL_KEY);
//		image.content = obj.getString(CONTENT_KEY);
		return image;
	}
	
	public List readJsonStream(InputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		try {
			return readImage(reader);//readImagesArray(reader);
		} finally {
			reader.close();
		}
	}

	public List readImagesArray(JsonReader reader) throws IOException {
		List images = new ArrayList();
		reader.beginArray();
		
		while (reader.hasNext()) {
			images.add(readImage(reader));
		}
		reader.endArray();
		return images;
	}

	public List<GoogleImageResult> readImage(JsonReader reader) throws IOException {
		long id = -1;
		String text = null;
		List<GoogleImageResult> results = new ArrayList<GoogleImageResult>();

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(RESULTS_KEY)&& reader.peek() != JsonToken.NULL) {
				results = readResultsArray(reader);
			} else if (name.equals("text")) {
				text = reader.nextString();
			} else if (name.equals("geo") && reader.peek() != JsonToken.NULL) {
//				geo = readDoublesArray(reader);
			} else if (name.equals("user")) {
//				user = readUser(reader);
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
//		return new Message(id, text, null, null);//user, geo);
		return results;
	}

	public List<GoogleImageResult> readResultsArray(JsonReader reader) throws IOException {
		List<GoogleImageResult> resultsList = new ArrayList<GoogleImageResult>();
		reader.beginArray();
		while (reader.hasNext()) {
			GoogleImageResult result = readResult(reader);
			resultsList.add(result);
/*			String name = reader.nextName();
			if (name.equals("name")) {
				username = reader.nextString();
			} else if (name.equals("followers_count")) {
				followersCount = reader.nextInt();
			} else {
				reader.skipValue();
			}
			*/
		}
		reader.endArray();
	
		return resultsList;
	}
	
	public GoogleImageResult readResult(JsonReader reader) throws IOException {
		GoogleImageResult image = new GoogleImageResult();
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
//			if (name.equals(IMG_URL_KEY)) {
//				image.fullUrl = reader.nextString();
//			} else {
//				reader.skipValue();
//			}
		}
		reader.endObject();
		return image;
	}
	

}
