package com.ade.soda;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends Activity{
	private final int DISPLAY_LENGHT = 5000; //延迟五秒   
	
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
