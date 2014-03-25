package com.nfd.imagesearch.helpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleImageResult implements Serializable{
	
	public static final String RESPONSE_DATA_KEY = "responseData";
	public static final String RESULTS_KEY = "results";
	public static final String IMG_WIDTH_KEY = "width";
	public static final String IMG_URL_KEY = "url";
	public static final String THUMB_URL_KEY = "tbUrl";
	public static final String CURSOR_KEY = "cursor";
	public static final String MORE_RESULTS_URL_KEY = "moreResultsUrl";
	public static final String CONTENT_KEY = "content";
	public static final String CONTEXT_URL = "originalContextUrl";
	public static final String TITLE = "titleNoFormatting";
	
	private static final long serialVersionUID = 5313197491336040141L;

	private String fullUrl;
	private String thumbUrl;
	private String content;
	private String title;
	private String contextUrl;
	
	public GoogleImageResult() {
	}
	
	public GoogleImageResult(JSONObject jsonObject) {
		try {
			fullUrl = jsonObject.getString(IMG_URL_KEY);
			content = jsonObject.getString(CONTENT_KEY);
			thumbUrl = jsonObject.getString(THUMB_URL_KEY);
			contextUrl = jsonObject.getString(CONTEXT_URL);
			title = StringEscapeUtils.unescapeHtml4(jsonObject.getString(TITLE));
		} catch(JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getFullUrl() {
		return fullUrl;
	}
	
	public String getThumbUrl() {
		return thumbUrl;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContextUrl() {
		return contextUrl;
	}
	
	public String toString() {
		return thumbUrl;
	}
		
	public static final List<GoogleImageResult> fromJSONArray(JSONArray imageResults) {
		List<GoogleImageResult> images = new ArrayList<GoogleImageResult>();
		try {
			for(int i=0; i<imageResults.length(); i++) {
				JSONObject result = (JSONObject) imageResults.get(i);
				images.add(new GoogleImageResult(result));
			}
		} catch(JSONException e) {
			e.printStackTrace();
		}
		return images;
	}
}
