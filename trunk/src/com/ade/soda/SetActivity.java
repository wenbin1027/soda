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

public class SetActivity extends Activity implements OnClickListener{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set);
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
	