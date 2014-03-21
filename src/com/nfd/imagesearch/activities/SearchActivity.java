package com.nfd.imagesearch.activities;

import com.nfd.imagesearch.R;
import com.nfd.imagesearch.R.id;
import com.nfd.imagesearch.R.layout;
import com.nfd.imagesearch.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends Activity {

	EditText etSearchText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		etSearchText = (EditText) findViewById(R.id.etSearchText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	public void onSettingsPress(MenuItem mi) {
		
	}
	
	public void onSearchClick(View view) {
		
		Log.d("TEST - SearchActivity - onSearchClick", etSearchText.getText().toString());
	}

}
