package com.nfd.imagesearch.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nfd.imagesearch.helpers.GoogleImageResult;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<GoogleImageResult> images;

    public ImageAdapter(Context c) {
    	images = new ArrayList<GoogleImageResult> ();
        mContext = c;
    }
    
    public ImageAdapter(Context c, List<GoogleImageResult> array) {
    	mContext = c;
    	images = array;
    }
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.size();
	}
    

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	ImageLoader imageLoader = ImageLoader.getInstance();
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(100, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        String imageUri = images.get(position).getThumbUrl();
        imageLoader.displayImage(imageUri, imageView);
        
        return imageView;
    }
}
