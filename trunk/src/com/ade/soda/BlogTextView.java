package com.ade.soda;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.widget.TextView;

public class BlogTextView extends TextView {
	ImageGetter imageGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
              Drawable drawable = null;
              String sourceName=getContext().getPackageName()+":drawable/"+source;
              int id=getResources().getIdentifier(sourceName,null,null);
              if (id!=0){
	              drawable=getResources().getDrawable(id);
	              if (drawable!=null){
	            	  drawable.setBounds(0, 0, 
	            			  drawable.getIntrinsicWidth(), 
	            			  drawable.getIntrinsicHeight());
	              }
              }
              return drawable;
        }
	};

	public BlogTextView(Context context) {
		super(context);
		setAutoLinkMask(Linkify.ALL);
	}

	public BlogTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAutoLinkMask(Linkify.ALL);
	}

	public BlogTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setAutoLinkMask(Linkify.ALL);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		String cs=text.toString();
		int start=cs.indexOf('@');
		if (start<cs.length() && start>0){
			int end=cs.indexOf(' ',start);
			if (end<cs.length() && end>0 && end-start<=10){
				CharSequence subcs=new String(cs.subSequence(start, end).toString());
				cs=cs.replace(subcs,"<font color=#339966>"+subcs+"</font>" );
			}
			else{
				end=cs.indexOf(':',start);
				if (end<cs.length() && end>0 && end-start<=10){
					CharSequence subcs=new String(cs.subSequence(start, end).toString());
					cs=cs.replace(subcs,"<font color=#339966>"+subcs+"</font>" );
				}
			}
		}
		StringBuilder sb=new StringBuilder(cs);
		//sb.append("<img src=\"portrait\"/>");
	
		super.setText(Html.fromHtml(sb.toString(),imageGetter,null), type);
	}
}
