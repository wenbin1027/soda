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

public class WriteActivity extends Activity implements OnClickListener{
	private Site site;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write);
		Intent intent=getIntent();
		if (intent!=null){
			if (intent.hasExtra("site")){
				site=SiteManager.getInstance().getSite(intent.getIntExtra("site", -1));
			}
		}
		findViewById(R.id.BtnImg).setOnClickListener(this);
		findViewById(R.id.BtnFace).setOnClickListener(this);
		findViewById(R.id.BtnSendMsg).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.BtnImg:
				//todo
				break;
			case R.id.BtnFace:
				//todo
				break;
			case R.id.BtnSendMsg:
				//todo
				WriteActivity.this.finish();
				break;
			default:
				break;
		}
	}
}
	