package com.nfd.imagesearch.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.nfd.imagesearch.R;
import com.nfd.imagesearch.helpers.GoogleImageResult;

public class ImageDisplayActivity extends Activity {

	private SmartImageView ivResult;
	private ShareActionProvider miShareAction;
	private GoogleImageResult image;
	private TextView tvTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("DEBUG - ImageDisplayActivity - onCreate", "Displaying image");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		setupViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		
		MenuItem item = menu.findItem(R.id.miShare);
	    // Fetch and store ShareActionProvider
	    miShareAction = (ShareActionProvider) item.getActionProvider();
	    setupShareAction();
	    item.setActionProvider(miShareAction);
		return true;
	}
	
	private void setupViews() {
		Intent i = getIntent();
		image = (GoogleImageResult) i.getSerializableExtra(SearchActivity.IMAGE_EXTRA);
		
		ivResult = (SmartImageView) findViewById(R.id.ivResult);
		ivResult.setImageUrl(image.getFullUrl());
		Log.d("TEST - ImageDisplayActivity - full url:", image.getFullUrl());
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText(image.getTitle());
	}
	
	public void onLinkClick(View view) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(image.getContextUrl()));
		startActivity(browserIntent);
	}
	
/*	
	public void onShareImage(MenuItem mi) {
	    SmartImageView ivImage = (SmartImageView) findViewById(R.id.ivResult);
	    Uri bmpUri = getLocalBitmapUri(ivImage);
	    if (bmpUri != null) {
	        // Construct a ShareIntent with link to image
	        Intent shareIntent = new Intent();
	        shareIntent.setAction(Intent.ACTION_SEND);
	        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
	        shareIntent.setType("image/*");
	        // Launch sharing dialog for image
	        startActivity(Intent.createChooser(shareIntent, "Share Content"));  
	    } else {
	        // ...sharing failed, handle error
	    }
	}
*/
	
	public void setupShareAction() {
	    // Fetch Bitmap Uri locally
	    SmartImageView ivImage = (SmartImageView) findViewById(R.id.ivResult);

//	    bmpUri = 
	    		getLocalBitmapUri(ivImage); // see previous section
	    // Create share intent as described above
/*
	    		Intent shareIntent = new Intent();
	    shareIntent.setAction(Intent.ACTION_SEND);
	    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
	    shareIntent.setType("image/*");
	    // Attach share event to the menu item provider
	    miShareAction.setShareIntent(shareIntent);
	    */

	}

	private Uri bmpUri;
	public void getLocalBitmapUri(ImageView imageView) {
		Bitmap bitmap ;
		if(imageView.getDrawable() instanceof BitmapDrawable) {
			bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
			Intent shareIntent = new Intent();
		    shareIntent.setAction(Intent.ACTION_SEND);
		    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
		    shareIntent.setType("image/*");
		    // Attach share event to the menu item provider
		    miShareAction.setShareIntent(shareIntent);

			/*
			try {
				URL url = new URL(image.getFullUrl());

//				bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}*/
	    // Write image to default external storage directory   
	    bmpUri = null;
	    try {
	        File file =  new File(Environment.getExternalStoragePublicDirectory(  
	            Environment.DIRECTORY_DOWNLOADS), "share_image.png");  
	        FileOutputStream out = new FileOutputStream(file);
	        bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
	        out.close();
	        bmpUri = Uri.fromFile(file);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		} else {
			new retrieveBitMapTask().execute(image.getFullUrl());
		}
//	    return bmpUri;
	}
	
	private class retrieveBitMapTask extends AsyncTask<String, Void, Bitmap> {

	    private Exception exception;

	    protected Bitmap doInBackground(String... arg0) {
	        try {
				URL url = new URL(image.getFullUrl());
				
				HttpGet httpRequest = new HttpGet(URI.create(image.getFullUrl()) );
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
				HttpEntity entity = response.getEntity();
				BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
				Bitmap bitmap = BitmapFactory.decodeStream(bufHttpEntity.getContent());
//				httpRequest.abort();

//				 bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
	            return bitmap;
	        } catch (Exception e) {
	            this.exception = e;
	            e.printStackTrace();
	            return null;
	        }
	    }

	    protected void onPostExecute(Bitmap bitmap) {
		    try {
		    	File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		    	dir.mkdirs();
		        File file =  new File(Environment.getExternalStoragePublicDirectory(  
		            Environment.DIRECTORY_DOWNLOADS), "share_image.png");  
		        FileOutputStream out = new FileOutputStream(file);
		        bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
		        out.close();
		        bmpUri = Uri.fromFile(file);
		        Intent shareIntent = new Intent();
			    shareIntent.setAction(Intent.ACTION_SEND);
			    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
			    shareIntent.setType("image/*");
			    // Attach share event to the menu item provider
			    miShareAction.setShareIntent(shareIntent);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    }

	}
}

 