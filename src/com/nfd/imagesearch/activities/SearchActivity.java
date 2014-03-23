package com.nfd.imagesearch.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nfd.imagesearch.R;
import com.nfd.imagesearch.adapters.ImageAdapter;
import com.nfd.imagesearch.helpers.EndlessScrollListener;
import com.nfd.imagesearch.helpers.GoogleImageResult;
import com.nfd.imagesearch.helpers.GoogleRestClient;
import com.nfd.imagesearch.helpers.ImageResultArrayAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class SearchActivity extends Activity {

	EditText etSearchText;
	GridView gvImageResults;
	List<GoogleImageResult> imageResults = new ArrayList<GoogleImageResult>();
//	ImageAdapter imageAdapter;
	ImageResultArrayAdapter imageAdapter;
	String moreResultsUrl = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();		
		imageAdapter = new ImageResultArrayAdapter(this, imageResults); //new ImageAdapter(this, imageResults);//
		gvImageResults.setAdapter(imageAdapter);
//		images = new ArrayList<GoogleImageResult>();
		setGridScroll();
    	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
    	ImageLoader.getInstance().init(config);
	}

	private void setupViews() {
		etSearchText = (EditText) findViewById(R.id.etSearchText);		
		gvImageResults = (GridView) findViewById(R.id.gvImageResults);
		gvImageResults.setAdapter(new ImageAdapter(this));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	public void onSettingsPress(MenuItem mi) {
	}

	// Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset, int totalItems) {

//    	if(offset > 7 || totalItems >= 56) {
//    		Toast.makeText(this, "Search Results Exceeded - pages: " + offset + " - items: " + totalItems, Toast.LENGTH_SHORT).show();
//    		return;
//    	}
      // This method probably sends out a network request and appends new data items to your adapter. 
      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
      // Deserialize API response and then construct new objects to append to the adapter
    	Log.d("TEST - SearchActivity - customLoadMoreDataFromApi", "offset: " + totalItems);
		RequestParams parms = new RequestParams();
		AsyncHttpClient client = new AsyncHttpClient();
		String params = "?v=1.0&start=" + totalItems + "&rsz=8&q=" + Uri.encode(etSearchText.getText().toString()) ;
//		parms.put("q", etSearchText.getText().toString());
//		parms.put("v", "1.0");
//		parms.put("start", totalItems);
//		parms.put("rsz", "8");
//		moreResultsUrl = response.getJSONObject("cursor").getString("moreResultsUrl");
		
		GoogleRestClient.get(params, parms, new JsonHttpResponseHandler() {
		    @Override
		    public void onSuccess(JSONObject response) {
		    	Log.d("TEST - SearchActivity - customload api", response.toString());
//		    	GoogleImageResultParserUtil parser = new GoogleImageResultParserUtil();
		    	JSONArray imageJsonResults = null;
		    	try {
		    		imageJsonResults = response.getJSONObject(GoogleImageResult.RESPONSE_DATA_KEY).getJSONArray(GoogleImageResult.RESULTS_KEY);
		    		
//		    		imageResults.addAll(GoogleImageResult.fromJSONArray(imageJsonResults));//parser.readImageResults(obj);
		    		imageAdapter.addAll(GoogleImageResult.fromJSONArray(imageJsonResults));//parser.readImageResults(obj);
//		    		imageAdapter.notify();
		    		moreResultsUrl = response.getJSONObject(GoogleImageResult.RESPONSE_DATA_KEY).getJSONObject("cursor").getString("moreResultsUrl");
		    		Log.d("TEST - SearchActivity - onSearchClick", "images: " + imageResults.size());

		    	}catch(JSONException e) {
		    		e.printStackTrace();
		    	}
		    }
		});
    }

	private void setGridScroll() {
		gvImageResults.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
	           	Log.d("TEST - SearchActivity - customLoadMoreDataFromApi", page + " total items: " + totalItemsCount);

	        	customLoadMoreDataFromApi(page, totalItemsCount); 
	                // or customLoadMoreDataFromApi(totalItemsCount); 
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
		parms.put("start", "0");
		parms.put("rsz", "8");
		GoogleRestClient.get("", parms, new JsonHttpResponseHandler() {
		    @Override
		    public void onSuccess(JSONObject response) {
		    	Log.d("TEST - SearchActivity - onSearchClick", response.toString());
//		    	GoogleImageResultParserUtil parser = new GoogleImageResultParserUtil();
		    	
		    	JSONArray imageJsonResults = null;
		    	try {
		    		imageJsonResults = response.getJSONObject(GoogleImageResult.RESPONSE_DATA_KEY).getJSONArray(GoogleImageResult.RESULTS_KEY);
		    		imageResults.clear();
//		    		imageResults.addAll(GoogleImageResult.fromJSONArray(imageJsonResults));//parser.readImageResults(obj);
		    		imageAdapter.addAll(GoogleImageResult.fromJSONArray(imageJsonResults));//parser.readImageResults(obj);
		    		moreResultsUrl = response.getJSONObject(GoogleImageResult.RESPONSE_DATA_KEY).getJSONObject("cursor").getString("moreResultsUrl");
		    		Log.d("TEST - SearchActivity - onSearchClick", "images: " + imageResults.size());
		    	}catch(JSONException e) {
		    		e.printStackTrace();
		    	}
		    }
		});		
	}

}
