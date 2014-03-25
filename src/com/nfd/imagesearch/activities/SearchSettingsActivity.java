package com.nfd.imagesearch.activities;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.nfd.imagesearch.R;
import com.nfd.imagesearch.models.Settings;

public class SearchSettingsActivity extends Activity {
	
	private Spinner spinnerImageSize;
	private Spinner spinnerImageColor;
	private Spinner spinnerImageType;
	private EditText etSearchSite;
	private Settings settings;
	private ArrayAdapter<CharSequence> imageSizeSpinAdapter;
	private ArrayAdapter<CharSequence> imageColorSpinAdapter;
	private ArrayAdapter<CharSequence> imageTypeSpinAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_settings);
		setupViews();
		setupSpinnerListeners();
		
		Intent intent = getIntent();
		settings = (Settings) intent.getSerializableExtra(SearchActivity.SETTINGS_EXTRA);
		populateSettings();
	}

	
	private void populateSettings() {
		String [] colorArray = getResources().getStringArray(R.array.image_color_filter_array);
		List<String> colors = Arrays.asList(colorArray);
		String [] sizeArray = getResources().getStringArray(R.array.image_size_filter_array);
		List<String> sizes = Arrays.asList(sizeArray);
		String [] typeArray = getResources().getStringArray(R.array.image_type_filter_array);
		List<String> types = Arrays.asList(typeArray);
		
		spinnerImageSize.setSelection(sizes.indexOf(settings.getImageSize()));
		spinnerImageColor.setSelection(colors.indexOf(settings.getImageColor()));
		spinnerImageType.setSelection(types.indexOf(settings.getImageType()));

		etSearchSite.setText(settings.getSearchSite());
	}
	
	private void setupViews() {
		spinnerImageSize = (Spinner) findViewById(R.id.spinImageSize);
		spinnerImageColor = (Spinner) findViewById(R.id.spinImageColor);
		spinnerImageType = (Spinner) findViewById(R.id.spinImageType);
		etSearchSite = (EditText) findViewById(R.id.etSearchSite);

		// Populate spinner values
		imageSizeSpinAdapter = ArrayAdapter.createFromResource(this, R.array.image_size_filter_array, android.R.layout.simple_spinner_item);
		imageSizeSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerImageSize.setAdapter(imageSizeSpinAdapter);
		
		imageColorSpinAdapter = ArrayAdapter.createFromResource(this, R.array.image_color_filter_array, android.R.layout.simple_spinner_item);
		imageColorSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerImageColor.setAdapter(imageColorSpinAdapter);
		
		imageTypeSpinAdapter = ArrayAdapter.createFromResource(this, R.array.image_type_filter_array, android.R.layout.simple_spinner_item);
		imageTypeSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerImageType.setAdapter(imageTypeSpinAdapter);
	}
	
	private void setupSpinnerListeners() {
		spinnerImageSize.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				// TODO Auto-generated method stub
				settings.setImageSize(parent.getItemAtPosition(pos).toString());				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		spinnerImageColor.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				settings.setImageColor(parent.getItemAtPosition(pos).toString());			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		spinnerImageType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				settings.setImageType(parent.getItemAtPosition(pos).toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_settings, menu);
		return true;
	}
	
	public void onSaveSettings(View view) {
		settings.setSearchSite(etSearchSite.getText().toString());
		Intent data = new Intent();
		Log.d("DEBUG", "save settings: " + settings.toString());
		
		File filesDir = getFilesDir();
	    File todoFile = new File(filesDir, Settings.SETTINGS_FILE);
	    try {

	    	FileUtils.write(todoFile, settings.convertToJson());
	    } catch(IOException e) {
	    	e.printStackTrace();
	    }

		
		data.putExtra(SearchActivity.SETTINGS_EXTRA, settings);
		setResult(RESULT_OK, data);
		// Close the screen and go back
		finish();
	}

}
