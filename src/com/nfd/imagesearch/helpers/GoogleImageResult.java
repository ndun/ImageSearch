package com.nfd.imagesearch.helpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;

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
			title = jsonObject.getString(TITLE);
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
	

/*
 * "GsearchResultClass":"GimageSearch",
			"width":"200",
			"height":"200",
			"imageId":"ANd9GcRwoH362kEP74tLKIiDkXKUVOjcCSvyJj-YGwwsm18YwBMaj1US9KrYPeE",
			"tbWidth":"104",
			"tbHeight":"104",
			"unescapedUrl":"http://rs1097.pbsrc.com/albums/g356/russelldempsey/Blues%20Leed%20Mascot/261020111011.jpg~c200",
			"url":"http://rs1097.pbsrc.com/albums/g356/russelldempsey/Blues%2520Leeds%2520Mascot/261020111011.jpg~c200",
			"visibleUrl":"photobucket.com",
			"title":"Blues Lounge Pictures, Images \u0026amp; Photos | Photobucket",
			"titleNoFormatting":"Blues Lounge Pictures, Images \u0026amp; Photos | Photobucket",
			"originalContextUrl":"http://photobucket.com/images/blues%20lounge",
			"content":"blues lounge photo: Mascot Lounge 261020111011.jpg",
			"contentNoFormatting":"blues lounge photo: Mascot Lounge 261020111011.jpg",
			"tbUrl":"http://t3.gstatic.com/images?q\u003dtbn:ANd9GcRwoH362kEP74tLKIiDkXKUVOjcCSvyJj-YGwwsm18YwBMaj1US9KrYPeE"	
 */
	
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
