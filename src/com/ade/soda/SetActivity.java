package com.ade.soda;

import java.util.List;

import com.ade.site.Site;
import com.ade.site.SiteManager;
import com.ade.site.User;

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
	private final int AUTHREQUESTCODE = 0;
	private ListView mListView;
	private List<Site> sites;
	private	String[] siteNames;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set);
		
		// Initialize ListView
		this.mListView = (ListView) findViewById(R.id.ListView01);
		sites = SiteManager.getInstance().getSites();
		siteNames = new String[sites.size()];
		for(int i = 0; i < sites.size(); i++){
			siteNames[i] = sites.get(i).getName();
		}
		
		this.mListView.setAdapter(
			new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_multiple_choice, 
				siteNames
			)
		);
		this.mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		this.mListView.setOnItemClickListener(this.mListCheckListener);
		
		loadListViewCheckState();
	}
	
	// Helper method	
	// Load ListView state
	private void loadListViewCheckState() {
		for(int i = 0; i < mListView.getCount(); i++){
			mListView.setItemChecked(i, siteAuthenticated(sites.get(i)));
		}
	}
	
	// Helper method
	// Check if a site is logged in.
	private Boolean siteAuthenticated(Site site){
		Boolean result = false;

		if(!site.isLoggedIn()){
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
				if(!siteAuthenticated(sites.get(arg2))) {
					// Pass the site ID to OAuthActivity
					Intent intent = new Intent(SetActivity.this, OAuthActivity.class);
					intent.putExtra("siteID", sites.get(arg2).getSiteID());
					intent.putExtra("user", new User());
					startActivityForResult(intent, AUTHREQUESTCODE);
				}
			} else {
				// Confirm to invalidate the access key
				AlertDialog.Builder bld = new AlertDialog.Builder(SetActivity.this);
				bld.setMessage(String.format(getString(R.string.dialogConfirmInvalidateAuthentication), sites.get(arg2).getName()));
				bld.setPositiveButton(getString(R.string.dialogOK), new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		            	Site site = sites.get(arg2);
		            	if(site != null)
		            	{
		            		site.logOut();
		            		setResult(RESULT_OK);
		            		loadListViewCheckState();
		            	}
		            }
		        });
		        bld.setNegativeButton(getString(R.string.dialogCancel), new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		            	//Toast.makeText(SetActivity.this, getString(R.string.dialogCancel), Toast.LENGTH_SHORT).show();
						loadListViewCheckState();
		            }
		        });
		        
				bld.show();
			}
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == AUTHREQUESTCODE) {
			loadListViewCheckState();
			Toast.makeText(this,
					resultCode == RESULT_OK ? 
							getString(R.string.authenticationSucceeded) : 
							getString(R.string.authenticationFailed),
					Toast.LENGTH_LONG).show();
			if (resultCode==RESULT_OK)
				setResult(RESULT_OK);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}