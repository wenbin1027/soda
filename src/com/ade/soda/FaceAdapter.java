package com.ade.soda;

import java.util.Map;
import java.util.Map.Entry;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;

public class FaceAdapter implements ListAdapter {
	private Context context;
	private Object faceArray[];
	
	public FaceAdapter(Map<String,String> faceMap,Context context){
		this.context=context;
		faceArray=(Object[])faceMap.entrySet().toArray();
	}
	
	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}

	@Override
	public int getCount() {
		return faceArray.length;
	}

	@Override
	public Object getItem(int position) {
		return faceArray[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView=null;
		if (convertView!=null){
			imageView=(ImageView)convertView;
		}
		else{
			imageView=new ImageView(context);
		}
		
        Drawable drawable = null;
        Entry<String,String> data=(Entry<String, String>) getItem(position);
        String sourceName=context.getPackageName()+":drawable/"+data.getValue();
        int id=context.getResources().getIdentifier(sourceName,null,null);
        if (id!=0){
              drawable=context.getResources().getDrawable(id);
              if (drawable!=null){
            	  imageView.setImageDrawable(drawable);
              }
        }
        
		return imageView;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return faceArray.length==0;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {

	}

}
