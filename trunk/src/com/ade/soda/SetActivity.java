package com.ade.soda;

import com.ade.site.Site;
import com.ade.site.SiteManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;	
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SetActivity extends Activity {
	private ListView mListView;
	private	String[] siteNames;
	private int[] siteIds;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set);
		
		// Initialize ListView
		siteNames = new String[] { getString(R.string.sina), getString(R.string.sohu)};
		siteIds = new int[] {SiteManager.SINA, SiteManager.SOHU};
		
		mListView = (ListView) findViewById(R.id.ListView01);
		mListView.setAdapter(
			new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_multiple_choice, siteNames
			)
		);
		mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mListView.setOnItemClickListener(this.mListCheckListener);
		
		loadListViewState();
	}
	
	// helper method	
	// Load ListView state
	private void loadListViewState() {
		for(int i = 0; i < mListView.getCount(); i++){
			mListView.setItemChecked(i, siteAuthenticated(i));
		}
	}
	
	// helper method
	// Check if a site has an access key.
	private Boolean siteAuthenticated(int siteIndex){
		Boolean result = false;
		
		// TODO Please confirm that this is a valid method to do this
		SiteManager siteMgr = SiteManager.getInstance();
		Site currentSite = siteMgr.getSite(siteIds[siteIndex]);
		String accessKey = currentSite.getAccessKey();
		if(accessKey == null || accessKey == ""){
			result = false;
		}else{
			result = true;
		}
		
		return result;
	}

	private OnItemClickListener mListCheckListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
			// Get item check state
			SparseBooleanArray selectedIndexes = mListView.getCheckedItemPositions();
			Boolean itemChecked = selectedIndexes.get(arg2);

			if(itemChecked) {
				if(!siteAuthenticated(arg2)) {
					// Pass the site ID to OAuthActivity
					Intent intent = new Intent(SetActivity.this, OAuthActivity.class);
					intent.putExtra("site", siteIds[arg2]);
					startActivityForResult(intent, RESULT_OK);
					
					loadListViewState();
				}
			} else {
				// Confirm to invalidate the access key
				AlertDialog.Builder bld = new AlertDialog.Builder(SetActivity.this);
				
				// TODO Move the strings to resource
				bld.setTitle(String.format("确定禁用\"%s\"帐户吗?\r\n再次启用需要重新验证。", siteNames[arg2]));
				bld.setPositiveButton("确定", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		            	invalidateAuthentication(arg2);
		            }
		        });
		        bld.setNegativeButton("取消", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		            	Toast.makeText(SetActivity.this, "取消", Toast.LENGTH_SHORT).show();
		            }
		        });
		        
				bld.show();
			}
		}
	};
	
	// helper method
	// Invalidate the authentication for selected site
	private void invalidateAuthentication(int siteIndex) {
		// TODO Invalidate the access key
		Toast.makeText(SetActivity.this, "TODO: Invalidate access key " + siteNames[siteIndex], Toast.LENGTH_SHORT).show();
	}
}