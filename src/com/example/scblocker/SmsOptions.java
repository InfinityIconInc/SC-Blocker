package com.example.scblocker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SmsOptions extends Activity{
	public static final String TAG = "SmsOptions";
	
	boolean bBlockAllStatus;
	boolean bContactsOnly;
	DataAccess da;
	Context context = SmsOptions.this;
	Button btnBlockAll;
	Button btnAllContacts;
	Button back;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smsoptions);
		///////////////////////////////
		da = new DataAccess (context );	
		//////////////
		RadioButton	rbRE = (RadioButton) findViewById(R.id.rbRE);
		RadioButton	rbRRL= (RadioButton) findViewById(R.id.rbRRL);	
		RadioButton	rbus= (RadioButton) findViewById(R.id.rbus);
		RadioButton	rbRC= (RadioButton) findViewById(R.id.rbRC);
		RadioGroup	rgOptions = (RadioGroup) findViewById(R.id.rgOptions);
		rgOptions.setOnCheckedChangeListener(new OnCheckedChangeListener () {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch ( checkedId ) {
			case R.id.rbRRL:
				da = new DataAccess(context);
				if (da.GetRespondStatus() == 0)
					Toast.makeText(context, "Status Already Set to Black List", Toast.LENGTH_SHORT).show();
				else {
					if  ( da.SetRespondStatus(0) > 0 )
						Toast.makeText(context, "Status set to Black List", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(context, "Error Setting Status to Black List", Toast.LENGTH_SHORT).show();
				}
				da.CloseDataAccess();
				break;
			
			case R.id.rbRE:
				da = new DataAccess(context);
				if (da.GetRespondStatus() == 1)
					Toast.makeText(context, "Status Already Set to Block Everyone", Toast.LENGTH_SHORT).show();
				else {
					if  ( da.SetRespondStatus(1) > 0 )
						Toast.makeText(context, "Status set to  Block Everyone", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(context, "Error Setting Status to  Block Everyone", Toast.LENGTH_SHORT).show();
				}
				da.CloseDataAccess();
				break;
				
			case R.id.rbRC:
				da = new DataAccess(context);
				if (da.GetRespondStatus() == 2)
					Toast.makeText(context, "Status Already Set to  Block Contacts", Toast.LENGTH_SHORT).show();
				else {
					if  ( da.SetRespondStatus(2) > 0 )
						Toast.makeText(context, "Status set to  Block Contacts", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(context, "Error Setting Status to  Block Contacts", Toast.LENGTH_SHORT).show();
				}
				da.CloseDataAccess();
				break;
/////////////
			case R.id.rbus:
				da = new DataAccess(context);
				if (da.GetRespondStatus() == 3)
					Toast.makeText(context, "Status Already Set to Unsaved Contacts", Toast.LENGTH_SHORT).show();
				else {
					if  ( da.SetRespondStatus(3) > 0 )
						Toast.makeText(context, "Status set to Unsaved Contacts", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(context, "Error Setting Status to Unsaved Contacts", Toast.LENGTH_SHORT).show();
				}
				da.CloseDataAccess();
				break;
			

		}
	}
	
});
		da = new DataAccess(context);
		if ( da.GetRespondStatus() == 0)
		  rbRRL.setChecked(true);
		else if ( da.GetRespondStatus() == 1 )
			rbRE.setChecked(true);
		else if ( da.GetRespondStatus() == 2 )
			rbRC.setChecked(true);
		else if ( da.GetRespondStatus() == 3 )
			rbus.setChecked(true);
		da.CloseDataAccess();
		
		
back=(Button)findViewById(R.id.bBack);
		
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SmsOptions.this,
						MainActivity.class);
				startActivity(intent);
				SmsOptions.this.finish();
			}
		});
		
		/*btnBlockAll = ( Button ) findViewById(R.id.btnBlkAll);
		btnAllContacts = ( Button ) findViewById(R.id.btnContactsOnly);
		
		btnBlockAll = ( Button ) findViewById(R.id.btnBlkAll);
		btnAllContacts = ( Button ) findViewById(R.id.btnContactsOnly);*/
/*
		///////////////////////////////////////////////////
		bContactsOnly = da.GetContactsOnlyStatus();
		
		if ( bContactsOnly )
			btnAllContacts.setText("Contacts Only Active");
		else
			btnAllContacts.setText("Contacts Only NotAct");
		
		btnAllContacts.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if ( da.GetContactsOnlyStatus()) {
					da.SetUnContactsOnly();
					btnAllContacts.setText("Contacts NotAct");
					da.SetUnBlockAllCalls();
					btnBlockAll.setText("Block NotAct");
				}
				else {
					da.SetContactsOnly();
					btnAllContacts.setText("Contacts Active");
					da.SetUnBlockAllCalls();
					btnBlockAll.setText("Block NotAct");
				}
			}
		});
		////////////////////////////////////////////////////
		bBlockAllStatus = da.GetBlockAllCallsStatus();
		
		if ( bBlockAllStatus )
			btnBlockAll.setText("Block Active");
		else
			btnBlockAll.setText("Block NotAct");
		
		btnBlockAll.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if ( da.GetBlockAllCallsStatus()) {
					da.SetUnBlockAllCalls();
					btnBlockAll.setText("Block NotAct");
					da.SetUnContactsOnly();
					btnAllContacts.setText("Contacts NotAct");
				}
				else {
					da.SetBlockAllCalls();
					btnBlockAll.setText("Block Active");
					da.SetUnContactsOnly();
					btnAllContacts.setText("Contacts NotAct");
				}
			}
		});
		/////////////////////////////////////////////////////
		da.CloseDataAccess();
///////////////////////////////////////////////////////////////////
		
	*/
	

}
}