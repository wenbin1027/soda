package com.ade.soda;

import java.util.SortedSet;

import com.ade.restapi.FriendsTimelineInterface;
import com.ade.restapi.SinaFriendsTimeline;
import com.ade.site.SinaSite;
import com.ade.site.Site;
import com.ade.site.User;

import android.app.Activity;	//���������
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;		//���������
import android.util.Log;
import android.view.View;
import android.widget.Button;	//���������
import android.widget.EditText;	//���������


public class MainActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {	//��дonCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);					//���õ�ǰ��Ļ
 /*       final Button OkButton = (Button) findViewById(R.id.Button01);		//��ȡȷ����ť����
        final Button cancelButton = (Button) findViewById(R.id.Button02);	//��ȡȡ����ť����
        final EditText uid=(EditText)findViewById(R.id.EditText01);			//��ȡ�û����ı������
        final EditText pwd=(EditText)findViewById(R.id.EditText02);			//��ȡ�����ı������
        final EditText log=(EditText)findViewById(R.id.EditText03);			//��ȡ��¼��־�ı������
        OkButton.setOnClickListener(//OnClickListenerΪView���ڲ��ӿڣ���ʵ���߸������������¼�
        		new View.OnClickListener(){ 
        			public void onClick(View v){ 					//��дonClick����
//        				String uidStr=uid.getText().toString();		//��ȡ�û����ı��������
//        				String pwdStr=pwd.getText().toString();		//��ȡ�����ı��������
//        				log.append("�û�����"+uidStr+" ���룺"+pwdStr+"\n");
        				Site site=new SinaSite();
        				User user=new User(1425507814);
        				user.setToken("ced38c8cf187af214d858f07cbd4cb88");
        				user.setTokenSecret("fc94636e3d4d5490a4ed142c5e3cbd5b");
        				site.logIn(user);
        				FriendsTimelineInterface fi=new SinaFriendsTimeline();
        				site.setFriendsTimeline(fi);
        				site.friendsTimeline();
        			} 
        		}); 
        cancelButton.setOnClickListener(//OnClickListenerΪView���ڲ��ӿڣ���ʵ���߸������������¼�
                new View.OnClickListener(){ 
                	public void onClick(View v){ 		//��дonClick����
                		uid.setText("");				//����û����ı�������
                		pwd.setText("");				//��������ı�������
                	} 
                }); 
 */ 
        
    }
    
}