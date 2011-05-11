package com.ade.soda;

import android.content.Context;
import android.graphics.Picture;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;

public class PictureWebView extends WebView implements PictureListener {

	public PictureWebView(Context context) {
		super(context);
		setPictureListener(this);
	}

	public PictureWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setPictureListener(this);
	}

	public PictureWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setPictureListener(this);
	}

	@Override
	public void onNewPicture(WebView view, Picture picture) {
		ViewGroup.LayoutParams lp=view.getLayoutParams();
		DisplayMetrics dm=getContext().getResources().getDisplayMetrics();
		lp.height=(int) (picture.getHeight()*dm.density);
		lp.width=(int) (picture.getWidth()*dm.density);
		view.setLayoutParams(lp);
	}

}
