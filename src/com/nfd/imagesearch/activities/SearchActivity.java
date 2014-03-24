package com.nfd.imagesearch.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nfd.imagesearch.R;
import com.nfd.imagesearch.adapters.ImageAdapter;
import com.nfd.imagesearch.helpers.EndlessScrollListener;
import com.nfd.imagesearch.helpers.GoogleImageResult;
import com.nfd.imagesearch.helpers.GoogleRestClient;
import com.nfd.imagesearch.helpers.ImageResultArrayAdapter;
import com.nfd.imagesearch.models.Settings;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class SearchActivity extends Activity {
	public static final int SETTINGS_REQUEST = 123;
	public static final int DISPLAY_IMAGE_REQUEST = 890;
	public static final String SETTINGS_EXTRA = "settings";
	public static final String IMAGE_EXTRA = "image";
	
	EditText etSearchText;
	GridView gvImageResults;
	List<GoogleImageResult> imageResults = new ArrayList<GoogleImageResult>();
	ImageResultArrayAdapter imageAdapter;
	Settings settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		settings = new Settings();
		setupViews();		
		imageAdapter = new ImageResultArrayAdapter(this, imageResults); 
		gvImageResults.setAdapter(imageAdapter);
		
		setGridViewListeners();
		
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
		Toast.makeText(this, "Settings launched", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this, SearchSettingsActivity.class);
		i.putExtra(SETTINGS_EXTRA, settings);
		startActivityForResult(i, SETTINGS_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == SETTINGS_REQUEST) {
			if(resultCode == RESULT_OK) {
				settings = (Settings) data.getSerializableExtra(SETTINGS_EXTRA);
				Toast.makeText(this, settings.toString(), Toast.LENGTH_SHORT).show();
			}
		} else if(requestCode == DISPLAY_IMAGE_REQUEST) {
			if(resultCode == RESULT_OK) {
				
			}
		}

	}

	// Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset, int totalItems) {
      // This method probably sends out a network request and appends new data items to your adapter. 
      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
      // Deserialize API response and then construct new objects to append to the adapter
    	Log.d("TEST - SearchActivity - customLoadMoreDataFromApi", "offset: " + totalItems);

		String params = "?v=1.0&start=" + totalItems + formatQueryParameters() + "&rsz=8&q=" + Uri.encode(etSearchText.getText().toString()) ;
		
		GoogleRestClient.get(params, new RequestParams(), new JsonHttpResponseHandler() {
		    @Override
		    public void onSuccess(JSONObject response) {
		    	Log.d("TEST - SearchActivity - customload api", response.toString());
		    	JSONArray imageJsonResults = null;
		    	try {
		    		imageJsonResults = response.getJSONObject(GoogleImageResult.RESPONSE_DATA_KEY).getJSONArray(GoogleImageResult.RESULTS_KEY);		    	
		    		imageAdapter.addAll(GoogleImageResult.fromJSONArray(imageJsonResults));//parser.readImageResults(obj);
		    		Log.d("TEST - SearchActivity - onSearchClick", "images: " + imageResults.size());

		    	} catch(JSONException e) {
		    		e.printStackTrace();
		    	}
		    }
		});
    }

	private void setGridViewListeners() {
		gvImageResults.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
	           	Log.d("TEST - SearchActivity - customLoadMoreDataFromApi", page + " total items: " + totalItemsCount);
	           	if(totalItemsCount >= 64) {
	           		Toast.makeText(getApplicationContext(), "Max results retrieved", Toast.LENGTH_SHORT).show();
	           		return;
	           	}
	        	customLoadMoreDataFromApi(page, totalItemsCount); 
	        }
	        });

		gvImageResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				GoogleImageResult image = imageAdapter.getItem(pos);
				i.putExtra(IMAGE_EXTRA, image);
				startActivity(i);//, SearchActivity.DISPLAY_IMAGE_REQUEST);				
			}
		});
	}
	
	public void onSearchClick(View view) {		
		Log.d("TEST - SearchActivity - onSearchClick", etSearchText.getText().toString());
		if(etSearchText.getText() == null || etSearchText.getText().toString().length() == 0) {
			Toast.makeText(this, "Please enter search criteria.", Toast.LENGTH_SHORT).show();
			return;
		}

		imageAdapter.clear();
		RequestParams parms = setQueryParameters();

		GoogleRestClient.get("", parms, new JsonHttpResponseHandler() {
		    @Override
		    public void onSuccess(JSONObject response) {
		    	Log.d("TEST - SearchActivity - onSearchClick", response.toString());
		    	
		    	JSONArray imageJsonResults = null;
		    	try {
		    		imageJsonResults = response.getJSONObject(GoogleImageResult.RESPONSE_DATA_KEY).getJSONArray(GoogleImageResult.RESULTS_KEY);
		    		imageAdapter.addAll(GoogleImageResult.fromJSONArray(imageJsonResults));
		    		Log.d("TEST - SearchActivity - onSearchClick", "images: " + imageResults.size());
		    	}catch(JSONException e) {
		    		e.printStackTrace();
		    	}
		    }
		});		
	}
	
	private RequestParams setQueryParameters() {
		RequestParams parms = new RequestParams();
		parms.put("q", etSearchText.getText().toString());
		parms.put("v", "1.0");
		parms.put("start", "0");
		parms.put("rsz", "8");
		if(settings == null) {
			return parms;
		}

		String imageColor = settings.getImageColor();
		if(imageColor != null && imageColor.length() > 0) {
			parms.put("imgcolor", imageColor);
		}
		String imageSize = settings.getImageSize();
		if(imageSize != null && imageSize.length() > 0) {
			parms.put("imgsz", imageSize);
		}
		
		String imageType = settings.getImageType();
		if(imageType != null && imageType.length() > 0) {
			parms.put("imgtype", imageType);
		}
		
		String searchSite = settings.getSearchSite();
		if(searchSite != null && searchSite.length() > 0) {
			parms.put("as_sitesearch", searchSite);
		}
		return parms;
	}
	
	private String formatQueryParameters() {
		if(settings == null) {
			return "";
		}
		StringBuilder parameters = new StringBuilder();
		String imageColor = settings.getImageColor();
		if(imageColor != null && imageColor.length() > 0) {
			parameters.append("&imgcolor=" + imageColor);
		}
		String imageSize = settings.getImageSize();
		if(imageSize != null && imageSize.length() > 0) {
			parameters.append("&imgsz=" + imageSize);
		}
		
		String imageType = settings.getImageType();
		if(imageType != null && imageType.length() > 0) {
			parameters.append("&imgtype=" + imageType);
		}
		
		String searchSite = settings.getSearchSite();
		if(searchSite != null && searchSite.length() > 0) {
			parameters.append("&as_sitesearch=" + Uri.encode(searchSite));
		}
		return parameters.toString();
	}

}
