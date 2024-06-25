package com.example.scblocker;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class TabsActivityMain extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView ( R.layout.activity_tab);
		
		TabHost host = getTabHost();
		
		Drawable logo = null;
		host.addTab(host.newTabSpec("tag3")
				.setIndicator("",
				getResources().getDrawable(R.drawable.showblacklistimg))
				.setContent(new Intent ( this, 	AddBlackList.class))
				);
		host.addTab(host.newTabSpec("tag2")
				.setIndicator("",
						getResources().getDrawable(R.drawable.circle))
				.setContent(new Intent (this, TabHistoryActivity.class)));
		host.addTab(host.newTabSpec("tag1")
				.setIndicator("",
						getResources().getDrawable(R.drawable.set))
				.setContent(new Intent ( this, TabsActivity.class))
				);
		
	}
}
