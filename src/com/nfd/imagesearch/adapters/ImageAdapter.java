package com.nfd.imagesearch.adapters;

import java.util.ArrayList;
import java.util.List;

import android.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView tvContent;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
//            tvContent = new TextView(mContext);
//            tvContent.setText(text)
        } else {
            imageView = (ImageView) convertView;
//            tvContent = (TextView) convertView;
        }

//        imageView.setImageResource(mThumbIds[position]);
        String imageUri = images.get(position).url;
        imageLoader.displayImage(imageUri, imageView);
//        tvContent.setText(position + " : " + images.get(position).content);
        
        return imageView;
//        return tvContent;
    }

    private Integer[] mThumbIds = {
            R.drawable.alert_dark_frame, R.drawable.alert_light_frame,
            R.drawable.arrow_down_float, R.drawable.arrow_up_float,
            R.drawable.btn_default_small, R.drawable.btn_dialog
    };


}
