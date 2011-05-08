package com.ade.soda;

import com.ade.site.Blog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;	
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;	
import android.widget.EditText;	
import android.widget.TextView;
import android.widget.Toast;


import android.widget.Toast;

public class REandFWActivity extends Activity implements OnClickListener{
	private Blog blog;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reandfw);
		Intent intent=getIntent();
		if (intent!=null){
			if (intent.hasExtra("CurrentBlog")){
				//主界面会把Blog对象通过Intent传递到这里
				blog=(Blog) intent.getSerializableExtra("CurrentBlog");
				Log.i("REandFW", blog.toString());
			}
		}
		
		//display blog content 
		WebView wvProfileImage=(WebView)findViewById(R.id.refwImage);
		WebView wvBlogPic =(WebView) findViewById(R.id.refwPic);
		WebView wvRetPic =(WebView) findViewById(R.id.refwRetPic);
		TextView tvMsg = (TextView)findViewById(R.id.refwMsg); 
		TextView tvUsrname = (TextView)findViewById(R.id.refwUsername);
		TextView tvRetMsg = (TextView) findViewById(R.id.refwRetMsg);
		
		
		tvMsg.setText(blog.getText());
		
		//sohu blog uses ScreenName instead of username
		String blogName;
		blogName=blog.getUser().getName();
		if (blogName.equals("")){
			blogName=blog.getUser().getScreenName();
		}
		
		tvUsrname.setText( blogName );
		wvProfileImage.loadUrl(blog.getUser().getProfileImageUrl());
		
		//middlePic has higher priority, picType is used to set picture size
		String pic=""; 
		String picType="";
		
		//this is used to set webview data string with respective size
		String data="";
		
		if (blog.getMiddlePic() !=null && ! "".equals(blog.getMiddlePic()) ){
			pic=blog.getMiddlePic();
			picType="msize"; //middle size picture
		}else if (blog.getOriginalPic() != null && ! "".equals(blog.getOriginalPic())){
			pic=blog.getOriginalPic();
			picType="osize"; //orignal size picture
		}
		
		if ( ! "".equals(pic)){
			wvBlogPic.setVisibility(0);	
			
			Log.i("REandFW",pic+" "+ picType);
			if ("msize".equals(picType)){
			   data="<IMG HEIGHT=\"180px\" WIDTH=\"200px\" SRC="+pic+">";
			}else{
			   data="<IMG HEIGHT=\"250px\" WIDTH=\"300px\" SRC="+pic+">";
			}
			wvBlogPic.loadData(data, "text/html", "UTF-8");
			//wvBlogPic.loadUrl(pic);
			Log.i("REandFW","data is "+ data);
		}
		
		//retweeted msg
		Blog retBlog = blog.getRetweetedBlog();
		if (retBlog != null){
			tvRetMsg.setText(" \nRE:" + retBlog.getUser().getName() + "\n" + retBlog.getText());
			
			pic="";
			picType="";
			
			if (retBlog.getMiddlePic() !=null && ! "".equals(retBlog.getMiddlePic()) ){
				pic=retBlog.getMiddlePic();
				picType="msize"; //middle size picture
			}else if (retBlog.getOriginalPic() != null && ! "".equals(retBlog.getOriginalPic())){
				pic=retBlog.getOriginalPic();
				picType="osize"; //orignal size picture
			}
			
			if ( ! "".equals(pic)){
				wvRetPic.setVisibility(0);
				
				if ("msize".equals(picType)){
					data="<IMG HEIGHT=\"180px\" WIDTH=\"200px\" SRC="+pic+">";
				}else{
					data="<IMG HEIGHT=\"250px\" WIDTH=\"300px\" SRC="+pic+">";
				}
				
				wvRetPic.loadData(data, "text/html", "UTF-8");;
			}
		}
		
		
		//findViewById(R.id.BtnRE).setOnClickListener(this);
		//findViewById(R.id.BtnFW).setOnClickListener(this);
		//findViewById(R.id.BtnMore).setOnClickListener(this);
		//findViewById(R.id.BtnFace).setOnClickListener(this);
		//findViewById(R.id.BtnImg).setOnClickListener(this);
		//findViewById(R.id.BtnOK).setOnClickListener(this);

		}
	
	//以下功能暂时不用
	public void onClick(View v) {
		
		/*switch (v.getId()) {
		case R.id.BtnRE:
			// todo
			break;
		case R.id.BtnFW:
			// todo
			break;
		case R.id.BtnMore:
			// todo
			break;
		case R.id.BtnImg:
			// todo
			break;
		case R.id.BtnFace:
			// todo
			break;
		case R.id.BtnOK:
			// todo
			REandFWActivity.this.finish();
			break;
		default:
			break;
		}*/
	}
}
