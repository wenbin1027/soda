package com.ade.soda;

import com.ade.site.OAuth;
import com.ade.site.OAuthListener;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class OAuthActivity extends Activity implements OAuthListener{
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oauth);
		
		testSohuAuth();
		//testSinaAuth();
	}

	/**仅供测试
	 * 
	 */
	private void testSohuAuth() {
		OAuth auth=new OAuth("http://api.t.sohu.com/oauth/request_token",
				"http://api.t.sohu.com/oauth/authorize",
				"http://api.t.sohu.com/oauth/access_token",(WebView)findViewById(R.id.webViewOauth));
		auth.setListener(this);
		auth.requestAccessToken("afcEmgzaB3SxsWAdCosr","KY6Q9LHhwkgKAZbfRfCSw$$ZOM%!STwQ2YAro)(i");
	}

	/**仅供测试
	 * 
	 */
	private void testSinaAuth() {
		OAuth auth=new OAuth("http://api.t.sina.com.cn/oauth/request_token",
				"http://api.t.sina.com.cn/oauth/authorize",
				"http://api.t.sina.com.cn/oauth/access_token",(WebView)findViewById(R.id.webViewOauth));
		auth.setListener(this);
		auth.requestAccessToken("3393006127","70768c222a4613ed7f930bae3dee2e57");
	}

	@Override
	public void onFinish(OAuth auth) {
		Toast.makeText(this, auth.isSuccess()?"Success":"Fail", Toast.LENGTH_LONG).show();
		finish();
	}
}
