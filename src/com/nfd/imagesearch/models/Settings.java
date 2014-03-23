package com.nfd.imagesearch.models;

import java.io.Serializable;

public class Settings implements Serializable{

	private static final long serialVersionUID = 7426339926684740159L;
	private String imageSize;
	private String imageColor;
	private String searchSite;
	private String imageType;

	public Settings() {
		imageSize = imageColor = searchSite = imageType = "";
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
