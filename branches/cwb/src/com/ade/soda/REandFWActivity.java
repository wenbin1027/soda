package com.ade.soda;

import com.ade.site.Blog;
import com.ade.site.Site;
import com.ade.site.SiteManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;	
import android.view.View;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;


public class REandFWActivity extends Activity{
	private Blog blog;
	private Site site;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reandfw);
		Intent intent=getIntent();
		if (intent!=null){
			if (intent.hasExtra("CurrentBlog")){
				blog=(Blog) intent.getSerializableExtra("CurrentBlog");
			}
			site=SiteManager.getInstance().getSiteByID(blog.getSiteID());
		}
		
		WebView wvProfileImage=(WebView)findViewById(R.id.refwImage);
		WebView wvBlogPic =(WebView) findViewById(R.id.refwPic);
		ImageView vImage=(ImageView) findViewById(R.id.refwvImage);
		BlogTextView tvMsg = (BlogTextView)findViewById(R.id.refwMsg); 
		TextView tvUsrname = (TextView)findViewById(R.id.refwUsername);
		TextView tvBlogInfo1 =(TextView)findViewById(R.id.refwInfo1);
		TextView tvBlogInfo2 =(TextView)findViewById(R.id.refwInfo2);
		String info1="";
		String info2="";
		info1= getString(R.string.fav)+" "+  blog.getUser().getFavouritesCount() 
		      + getString(R.string.separator)  
		      + getString(R.string.fans) +" " + blog.getUser().getFollowersCount();
        info2 = getString(R.string.blog) +" " + blog.getUser().getBlogsCount()
		      + getString(R.string.separator)
		      + getString(R.string.friends) + " " + blog.getUser().getFriendsCount() ;
		      
		tvBlogInfo1.setText(info1);
		tvBlogInfo2.setText(info2);
		
		//write blog
		tvUsrname.setText( blog.getUser().getScreenName()+" "+blog.getUser().getLocation() );
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
				wvRetPic.loadUrl(retPic);
			}
			else{
				wvRetPic.setVisibility(View.GONE);
			}
			
		}else{
			findViewById(R.id.refwReBlog).setVisibility(View.GONE);
			findViewById(R.id.refwRetPic).setVisibility(View.GONE);
		}
	}
}
