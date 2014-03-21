package com.nfd.imagesearch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nfd.imagesearch.R;
import com.nfd.imagesearch.helpers.GoogleRestClient;

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
		RequestParams parms = new RequestParams();
		parms.put("q", "dog");
		parms.put("v", "1.0");
		GoogleRestClient.get("", parms, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	Log.d("TEST - SearchActivity - onSearchClick", response);
		    }
		});
	}

}
