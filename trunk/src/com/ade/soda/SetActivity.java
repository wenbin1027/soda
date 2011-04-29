package com.ade.soda;

import com.ade.site.Site;
import com.ade.site.SiteManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;	
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SetActivity extends Activity {
	private ListView lView;
	private	String providerNames[] = new String[] { getString(R.string.sina), getString(R.string.sohu)};
	private int[] siteIds = new int[] {SiteManager.SINA, SiteManager.SOHU};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set);
		lView = (ListView) findViewById(R.id.ListView01);
		lView.setAdapter(
				new ArrayAdapter<String>(
						this, 
						android.R.layout.simple_list_item_multiple_choice, providerNames
				)
		);
		lView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lView.setOnItemClickListener(this.mListCheckListener);
	}

	private OnItemClickListener mListCheckListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			SparseBooleanArray selectedIndexes = lView.getCheckedItemPositions();
			Boolean itemChecked = selectedIndexes.get(arg2);
			SiteManager siteMgr = SiteManager.getInstance();
			Site currentSite = siteMgr.getSite(siteIds[arg2]);

			if(itemChecked){
				// Check the access key of site
				String accessKey = currentSite.getAccessKey();
				if(accessKey == null || accessKey == "")
				{
					// Pass the account info to OAuthActivity
					Intent intent = new Intent(SetActivity.this, OAuthActivity.class);
					intent.putExtra("site", siteIds[arg2]);
					startActivity(intent);
				}
			}else{
				// TODO Invalidate the access key
			}
		}			
	};
}
