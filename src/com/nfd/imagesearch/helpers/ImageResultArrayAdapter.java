package com.nfd.imagesearch.helpers;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;
import com.nfd.imagesearch.R;

public class ImageResultArrayAdapter extends ArrayAdapter<GoogleImageResult> {

	public ImageResultArrayAdapter(Context context, List<GoogleImageResult> objects) {	
		super(context, R.layout.item_image_result, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GoogleImageResult imageInfo = this.getItem(position);
		SmartImageView ivImage;
		if(convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			ivImage = (SmartImageView) inflater.inflate(R.layout.item_image_result, parent, false);
		} else {
			ivImage = (SmartImageView) convertView;
			ivImage.setImageResource(android.R.color.transparent);
		}
		ivImage.setImageUrl(imageInfo.getThumbUrl());
		return ivImage;
	}
	
	

}
