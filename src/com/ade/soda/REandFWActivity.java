package com.ade.soda;

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

public class REandFWActivity extends Activity implements OnClickListener{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reandfw);
		findViewById(R.id.BtnRE).setOnClickListener(this);
		findViewById(R.id.BtnFW).setOnClickListener(this);
		findViewById(R.id.BtnMore).setOnClickListener(this);
		findViewById(R.id.BtnFace).setOnClickListener(this);
		findViewById(R.id.BtnImg).setOnClickListener(this);
		findViewById(R.id.BtnOK).setOnClickListener(this);

		}
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
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
		}
	}
}
