package com.ade.soda;

import com.ade.site.Blog;

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
	private Blog blog;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reandfw);
		Intent intent=getIntent();
		if (intent!=null){
			if (intent.hasExtra("CurrentBlog")){
				//主界面会把Blog对象通过Intent传递到这里
				blog=(Blog) intent.getSerializableExtra("CurrentBlog");
				Log.i("REandFW", blog.toString());
			}
		}
		else{//在开发时如果主界面还没开发完，可能传不过来Blog对象，可以使用这个else分支自己建一个Blog对象，但发布时不需要此else分支
			//TODO:Delete this on RELEASE
			blog=new Blog();
			blog.setID(1234567);
			blog.setText("仅供测试");
			//......
		}
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
