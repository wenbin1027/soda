package com.ade.soda;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends Activity{
	private final int DISPLAY_LENGHT = 2000; //延迟两秒   
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		new Handler().postDelayed(new Runnable(){    
	         @Override  
	         public void run() {   
	             WelcomeActivity.this.finish();  
	         }  
	            
	        },DISPLAY_LENGHT );
	  }  
	} 
