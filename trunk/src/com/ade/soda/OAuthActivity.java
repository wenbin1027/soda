package com.ade.soda;

import java.util.Set;

import com.ade.site.Blog;
import com.ade.site.OAuth;
import com.ade.site.OAuthListener;
import com.ade.site.Site;
import com.ade.site.SiteListener;
import com.ade.site.SiteManager;
import com.ade.site.User;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;
import android.widget.Toast;

public class OAuthActivity extends Activity implements OAuthListener,SiteListener{
	private final int AUTHSUCCESS=1;
	private final int AUTHFAIL=2;
	private final int VERIFYBEGIN=3;
	private final int VERIFYERROR=4;
	private final int VERIFYEND=5;
	private Site site;
	private User user;
	private OAuth oauth;
	private int siteID;
	private Dialog progressDlg;
	
	private Handler mainHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case AUTHSUCCESS:
				site.accountVerify();
				break;
			case AUTHFAIL:
				finish();
				break;
			case VERIFYBEGIN:
				if (progressDlg==null){
					progressDlg=ProgressDialog.show(
							OAuthActivity.this, 
							getResources().getString(R.string.waitDialogTitle),
							getResources().getString(R.string.waitDialogMessage),
							true,true,
							new DialogInterface.OnCancelListener() {
								@Override
								public void onCancel(DialogInterface dialog) {
									site.abort();
								}
							}
							);
				}
				break;
			case VERIFYEND:
				dismissDlg();
				Intent intent=new Intent();
				intent.putExtra("user", user);
				intent.putExtra("siteID", siteID);
				setResult(RESULT_OK,intent);
				finish();
				break;
			case VERIFYERROR:
				dismissDlg();
				Toast.makeText(OAuthActivity.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
				finish();
				break;
			}
			return true;
		}

		/**
		 * 
		 */
		private void dismissDlg() {
			//site.removeListener(OAuthActivity.this);
			if (progressDlg!=null){
				progressDlg.dismiss();
				progressDlg=null;
			}
		}
	});
	
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
			site.addListener(this);
		}
		if (intent.hasExtra("user")){
			user=(User) intent.getSerializableExtra("user");
		}	
		if (site!=null){
			doAuth();
		}
	}
	
	@Override
	protected void onDestroy() {
		site.removeListener(this);
		super.onDestroy();
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
			this.oauth=auth;
			if (user!=null){
				user.setAccessToken(auth.getAccessToken().token);
				user.setAccessSecret(auth.getAccessToken().secret);
				site.logIn(user);
			}
			mainHandler.sendEmptyMessage(AUTHSUCCESS);
		}
		else{
			mainHandler.sendEmptyMessage(AUTHFAIL);
		}
	}

	@Override
	public void onBeginRequest() {
		mainHandler.sendEmptyMessage(VERIFYBEGIN);
	}

	@Override
	public void onError(String errorMessage) {
		Message msg=new Message();
		msg.what=VERIFYERROR;
		msg.obj=errorMessage;
		mainHandler.sendMessage(msg);
	}

	@Override
	public void onResponsed() {
		mainHandler.sendEmptyMessage(VERIFYEND);
	}

}
