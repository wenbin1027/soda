package com.ade.soda;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;

public class PictureWebView extends WebView implements PictureListener {
	private Picture picture;
	
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
		if (picture!=null){
			this.picture=picture;
			ViewGroup.LayoutParams lp=view.getLayoutParams();
			lp.height=picture.getHeight();
			lp.width=picture.getWidth();
			view.setLayoutParams(lp);	
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (picture!=null){
			canvas.drawPicture(picture);
		}
		else{
			Drawable background=this.getBackground();
			if (background!=null){
				background.setBounds(0,0,background.getIntrinsicWidth(),background.getIntrinsicHeight());
				background.draw(canvas);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return true;
	}

}
