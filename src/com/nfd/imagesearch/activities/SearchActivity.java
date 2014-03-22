package com.nfd.imagesearch.activities;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nfd.imagesearch.R;
import com.nfd.imagesearch.adapters.ImageAdapter;
import com.nfd.imagesearch.helpers.GoogleImageResult;
import com.nfd.imagesearch.helpers.GoogleImageResultParserUtil;
import com.nfd.imagesearch.helpers.GoogleRestClient;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class SearchActivity extends Activity {

	EditText etSearchText;
	GridView gvImageResults;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		etSearchText = (EditText) findViewById(R.id.etSearchText);
		
		gvImageResults = (GridView) findViewById(R.id.gvImageResults);
		gvImageResults.setAdapter(new ImageAdapter(this));
    	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
    	ImageLoader.getInstance().init(config);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	public void onSettingsPress(MenuItem mi) {
		gvImageResults.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void onSearchClick(View view) {
		
		Log.d("TEST - SearchActivity - onSearchClick", etSearchText.getText().toString());
		if(etSearchText.getText() == null || etSearchText.getText().toString().length() == 0) {
			Toast.makeText(this, "Please enter search criteria.", Toast.LENGTH_SHORT).show();
			return;
		}
		RequestParams parms = new RequestParams();
		parms.put("q", etSearchText.getText().toString());
		parms.put("v", "1.0");
		GoogleRestClient.get("", parms, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	Log.d("TEST - SearchActivity - onSearchClick", response);
		    	GoogleImageResultParserUtil parser = new GoogleImageResultParserUtil();
		    	try {
		    		JSONObject obj = new JSONObject(response);
		    		List<GoogleImageResult> images = parser.readImageResults(obj);
		    		Log.d("TEST - SearchActivity - onSearchClick", "images: " + images.size());
		    		gvImageResults.setAdapter(new ImageAdapter(getApplicationContext(), images));
		    	}catch(JSONException e) {
		    		e.printStackTrace();
		    	}

		    }
		});
	}

}
