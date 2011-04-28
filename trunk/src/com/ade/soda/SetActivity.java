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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import android.widget.Toast;

public class SetActivity extends Activity implements OnItemClickListener{
	private Site site;  //需要的话就用，不需要就删掉
	private ListView lView;
	private	String providerNames[];

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();
		if (intent!=null){
			if (intent.hasExtra("site")){  //传进来的是当前site
				site=SiteManager.getInstance().getSite(intent.getIntExtra("site", -1));
			}
		}

		setContentView(R.layout.set);
		lView = (ListView) findViewById(R.id.ListView01);
		this.providerNames = new String[] { getString(R.string.sina), getString(R.string.sohu)};
		lView.setAdapter(
			new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_multiple_choice, providerNames
			)
		);
		lView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Pass the account info to OAuthActivity
		Toast.makeText(this, this.providerNames[arg2], Toast.LENGTH_LONG).show();
	}

}
