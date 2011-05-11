package com.ade.soda;

import com.ade.site.Blog;
import com.ade.site.Site;
import com.ade.site.SiteManager;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import android.widget.Toast;

public class REandFWActivity extends Activity implements OnClickListener{
	private Blog blog;
	private Site site;
	
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
			site=SiteManager.getInstance().getSiteByID(blog.getSiteID());
		}
		
		//display blog content 
		WebView wvProfileImage=(WebView)findViewById(R.id.refwImage);
		WebView wvBlogPic =(WebView) findViewById(R.id.refwPic);
		ImageView vImage=(ImageView) findViewById(R.id.refwvImage);
		BlogTextView tvMsg = (BlogTextView)findViewById(R.id.refwMsg); 
		TextView tvUsrname = (TextView)findViewById(R.id.refwUsername);
		
		
		//write blog
		tvUsrname.setText( blog.getUser().getScreenName() );
		tvMsg.setText(blog.getText(),site.getFaceMap());
		wvProfileImage.loadUrl(blog.getUser().getProfileImageUrl());
		
		if (!blog.getUser().isVerified())
			vImage.setVisibility(View.INVISIBLE);
		
		String pic ="" ; 
		
		if (blog.getMiddlePic().length()>0 ){
			pic=blog.getMiddlePic();
		}else if (blog.getOriginalPic().length()>0)  {
			pic=blog.getOriginalPic();
		}
		
		if ( pic.length()>0 ){
			wvBlogPic.getSettings().setSupportZoom(false);
			wvBlogPic.loadUrl(pic);
		}else{
			wvBlogPic.setVisibility(View.GONE);
		}
		
		
		//retweeted msg
		Blog retBlog =blog.getRetweetedBlog();
		if ( retBlog !=null && blog.isHaveRetweetedBlog()&& blog.getInReplyBlogText().length()>0){
			BlogTextView tvRetMsg = (BlogTextView) findViewById(R.id.refwRetMsg);
			WebView wvRetPic =(WebView) findViewById(R.id.refwRetPic);
			String retPic="";
			
			if (blog.getInReplyBlogText().length()>0){
				if (blog.getInReplyUserScreenName().length()>0){
					tvRetMsg.setText("@"+retBlog.getUser().getScreenName()+": "+retBlog.getText(),
						SiteManager.getInstance().getSiteByID(blog.getSiteID()).getFaceMap());
				}
				else{
					tvRetMsg.setText(retBlog.getText(),
						SiteManager.getInstance().getSiteByID(blog.getSiteID()).getFaceMap());
				}
			}else{
				tvRetMsg.setVisibility(View.GONE);
			}
			
			if (retBlog.getMiddlePic().length() >0 ){
				retPic=retBlog.getMiddlePic();
			}else if (retBlog.getOriginalPic().length()>0){
				retPic=retBlog.getOriginalPic();
			}
			
			if (retPic.length()>0){
				wvRetPic.getSettings().setSupportZoom(false);
				wvRetPic.loadUrl(retPic);
			}
			else{
				wvRetPic.setVisibility(View.GONE);
			}
			
		}else{
			findViewById(R.id.refwReBlog).setVisibility(View.GONE);
			findViewById(R.id.refwRetPic).setVisibility(View.GONE);
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
