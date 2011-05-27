package com.ade.soda;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;

public class PictureWebView extends WebView {
	
	public PictureWebView(Context context) {
		super(context);
		init();
	}

	public PictureWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PictureWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		setBackgroundColor(0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return true;
	}

}
