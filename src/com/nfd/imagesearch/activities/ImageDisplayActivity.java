package com.nfd.imagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.loopj.android.image.SmartImageView;
import com.nfd.imagesearch.R;
import com.nfd.imagesearch.helpers.GoogleImageResult;

public class ImageDisplayActivity extends Activity {

	private SmartImageView ivResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		Intent i = getIntent();
		GoogleImageResult image = (GoogleImageResult) i.getSerializableExtra(SearchActivity.IMAGE_EXTRA);
		ivResult = (SmartImageView) findViewById(R.id.ivResult);
		ivResult.setImageUrl(image.getFullUrl());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}

}
