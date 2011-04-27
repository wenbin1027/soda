package com.ade.soda;

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
import android.widget.Button;	
import android.widget.EditText;	
import android.widget.Toast;


import android.widget.Toast;

public class SetActivity extends Activity implements OnClickListener{
	private Site site;  //需要的话就用，不需要就删掉
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set);
		Intent intent=getIntent();
		if (intent!=null){
			if (intent.hasExtra("site")){  //传进来的是当前site
				site=SiteManager.getInstance().getSite(intent.getIntExtra("site", -1));
			}
		}
		findViewById(R.id.enterusername).setOnClickListener(this);
		findViewById(R.id.enterPwd).setOnClickListener(this);
		findViewById(R.id.sina).setOnClickListener(this);
		findViewById(R.id.sohu).setOnClickListener(this);
		}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.enterusername:
				//todo
				break;
			case R.id.enterPwd:
				//todo
				break;
			case R.id.sina:
				//todo
				break;
			case R.id.sohu:
				//todo
				break;
			default:
				break;
		}
	}
}
	