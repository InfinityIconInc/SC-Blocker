package com.example.scblocker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	
	Context context = MainActivity.this;
	ToggleButton tbCallBlock;
	ToggleButton tbSMSBlock;
	DataAccess da;
	Button btnarrow;
	
	public static final String TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tbCallBlock = ( ToggleButton ) findViewById(R.id.tbCallBlock);
		tbSMSBlock = ( ToggleButton ) findViewById(R.id.tbSMSBlock);
		Button btntaketour = (Button) findViewById(R.id.taketour);
		Button btnarrow =(Button)findViewById(R.id.btnarrow);
		
		
		btnarrow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent ( MainActivity.this, TabsActivityMain.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity ( intent);
				//MainActivity.this.finish();
			}
		});

		btntaketour.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						FliperActivity.class);
				startActivity(intent);
				MainActivity.this.finish();

			}
		});

///////////TOGGLE BUTTONS SERVICE STATUS
	if ( CallService.bCallStarted )
		tbCallBlock.setChecked(true);
	else
		tbCallBlock.setChecked(false);
	
	if ( SMSService.bSMSStarted )
		tbSMSBlock.setChecked(true);
	else
		tbSMSBlock.setChecked(false);
	
	}	
	///////////////
	
	public void CallBlockingToggleClick(View v) {
		boolean on = ((ToggleButton) v).isChecked();
		
		if ( on ) {
			Intent intentService = new Intent ( MainActivity.this, CallService.class);
			startService ( intentService );
			Log.d ( TAG, "Call Service Strted in MainActivity");
			Toast.makeText(MainActivity.this, "Call Blocker Service Started", Toast.LENGTH_LONG).show();
		} else {
			Intent intentService = new Intent ( MainActivity.this, CallService.class);
			stopService ( intentService );
			Log.d ( TAG, "Call Service Stopped in MainActivity");
			Toast.makeText(MainActivity.this, "Call Blocker Service Stopped", Toast.LENGTH_LONG).show();			
		}
	}

	public void SMSBlockingToggleClick(View v) {
		boolean on = ((ToggleButton) v).isChecked();

		if (on) {
			Intent intentSMS = new Intent (MainActivity.this, SMSService.class);
			startService ( intentSMS );
			Log.d ("MA", "Service Started in MA");
			Toast.makeText(MainActivity.this, "SMS Blocker Service Started",
					Toast.LENGTH_SHORT).show();
		} else {
			Intent intentSMS = new Intent (MainActivity.this, SMSService.class);
			stopService ( intentSMS );
			Log.d ("MA", "SMS Service Stopped in MA");
			Toast.makeText(MainActivity.this, "SMS Blocker Service Stopped",
					Toast.LENGTH_SHORT).show();		
			
		}
	}
}