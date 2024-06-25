package com.example.scblocker;

import android.app.Activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;


@SuppressWarnings("deprecation")
public class TabHistoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView ( R.layout.activity_history_buttons);
		
		 Button btnSmsHistory = ( Button ) findViewById(R.id.btnSmsHistory);
			Button btnCallHistory = (Button ) findViewById(R.id.btnCallHistory);
			
			btnSmsHistory.setOnClickListener( new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intentSH = new Intent ( TabHistoryActivity.this, SmsHistoryOptions.class);
				intentSH.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity( intentSH);
					//MainActivity.this.finish();
				}
			});
			
			
			btnCallHistory.setOnClickListener( new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intentCallHistory = new Intent ( TabHistoryActivity.this, CallHistoryOptions.class);
					intentCallHistory.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity( intentCallHistory);
					//MainActivity.this.finish();
				}
			});


		
		
	}
}
