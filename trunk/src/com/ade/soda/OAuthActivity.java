package com.ade.soda;

import com.ade.site.OAuth;
import com.ade.site.OAuthListener;
import com.ade.site.Site;
import com.ade.site.SiteManager;
import com.ade.site.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class OAuthActivity extends Activity implements OAuthListener{
	private Site site;
	private User user;
	private OAuth oauth;
	private int siteID;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oauth);
		Intent intent=getIntent();
		if (intent.hasExtra("siteID")){
			siteID=intent.getIntExtra("siteID",SiteManager.SINA);
			site=SiteManager.getInstance().getSites().get(siteID);
		}
		if (intent.hasExtra("user")){
			user=(User) intent.getSerializableExtra("user");
		}	
		if (site!=null){
			doAuth();
		}
	}
	
	private void doAuth(){
		oauth=new OAuth(site.getOauthRequestUrl(),site.getOauthUrl(),
				site.getOauthAccessUrl(),(WebView)findViewById(R.id.webViewOauth));
		oauth.setListener(this);
		oauth.requestAccessToken(site.getAppKey(), site.getAppSecret());
	}

	@Override
	public void onFinish(OAuth auth) {
		if (auth.isSuccess()){
			if (user!=null){
				user.setAccessToken(auth.getAccessToken().token);
				user.setAccessSecret(auth.getAccessToken().secret);
			}
			Intent intent=new Intent();
			intent.putExtra("user", user);
			intent.putExtra("siteID", siteID);
			setResult(RESULT_OK,intent);
			finish();
		}
		else{
			finishActivity(RESULT_CANCELED);
		}
	}
}
