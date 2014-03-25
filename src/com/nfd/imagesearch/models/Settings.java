package com.nfd.imagesearch.models;

import java.io.Serializable;

import org.json.JSONObject;

import android.util.Log;

public class Settings implements Serializable {
	public static final String SETTINGS_FILE = "image_search_settings.txt";
	public static final String SIZE_KEY = "imageSize";
	public static final String COLOR_KEY = "imageColor";
	public static final String TYPE_KEY = "imageType";
	public static final String SITE_KEY = "searchSite";

	private static final long serialVersionUID = 7426339926684740159L;

	private String imageSize;
	private String imageColor;
	private String searchSite;
	private String imageType;

	public Settings() {
		imageSize = imageColor = searchSite = imageType = "";
	}

	public Settings(String values) {
		try {
			JSONObject json = new JSONObject(values);
			imageSize = json.getString(SIZE_KEY);
			imageColor = json.getString(COLOR_KEY);
			imageType = json.getString(TYPE_KEY);
			searchSite = json.getString(SITE_KEY);

		} catch (Exception e) {
			Log.d("DEBUG - Settings() - Error parsing json", values);
			e.printStackTrace();
		}
	}

	public String convertToJson() {
		JSONObject json = new JSONObject();
		try {
			json.put(SIZE_KEY, imageSize);
			json.put(COLOR_KEY, imageColor);
			json.put(TYPE_KEY, imageType);
			json.put(SITE_KEY, searchSite);
		} catch (Exception e) {
			return "";
		}
		return json.toString();
	}

	public void setImageSize(String size) {
		imageSize = size;
	}

	public void setImageColor(String color) {
		imageColor = color;
	}

	public void setSearchSite(String site) {
		searchSite = site;
	}

	public void setImageType(String type) {
		imageType = type;
	}

	public String getImageSize() {
		return imageSize;
	}

	public String getImageColor() {
		return imageColor;
	}

	public String getImageType() {
		return imageType;
	}

	public String getSearchSite() {
		return searchSite;
	}

	public String toString() {
		return "Size: " + imageSize + " - Color: " + imageColor + " - Type: " + imageType + " - site: " + searchSite;
	}
}
