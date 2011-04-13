package com.ade.soda;

import java.util.SortedSet;

import com.ade.restapi.FriendsTimelineInterface;
import com.ade.restapi.SinaFriendsTimeline;
import com.ade.site.SinaSite;
import com.ade.site.Site;
import com.ade.site.User;

import android.app.Activity;	
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;		
import android.util.Log;
import android.view.View;
import android.widget.Button;	
import android.widget.EditText;	


public class MainActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);					
     }    
}