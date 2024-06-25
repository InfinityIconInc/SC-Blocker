package com.example.scblocker;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class TabsActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView ( R.layout.activity_tab);
		
		TabHost host = getTabHost();
		
		host.addTab(host.newTabSpec("tag1")
				.setIndicator("Call Rejection mode")
				.setContent(new Intent ( this, Calloptions.class))
				);
		
		host.addTab(host.newTabSpec("tag2")
				.setIndicator("Sms Rejection mode")
				.setContent(new Intent (this, SmsOptions.class))
				);
		
		
		
	}
}
