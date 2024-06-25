package com.example.scblocker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddManualBlacklistActivity extends Activity {
	EditText etPhone;
	TextView text;
	EditText txtCN;
	Context context = AddManualBlacklistActivity.this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView ( R.layout.activity_add_manual_blacklist);
		
		txtCN = (EditText) findViewById(R.id.txtname);
		Button btnAddManualBL = ( Button ) findViewById(R.id.btnAddBL);
		etPhone = ( EditText ) findViewById(R.id.txtNumber);
		///////////////////
       // text = ( TextView ) findViewById( R.id.textView1 );
		
		//text.setText( Html.fromHtml("<a href=\"https://play.google.com/store/apps/details?id=com.infinityicon.newcellphoneprices\">Latest Mobile Phone Prices</a>"));
		//text.setMovementMethod(LinkMovementMethod.getInstance());
		/////////////////////
		btnAddManualBL.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if ( etPhone.getText().length() !=0 ) {
					String strPhone = etPhone.getText().toString().trim() ;
					String strname=txtCN.getText().toString().trim();
					
					DataAccess da = new DataAccess ( context );
					
					Contact contact = new Contact ( 0, strPhone, strname, 0);
					if ( ! da.CheckExistingBlackListContact(contact) ) {
						int iCID = da.GetMaxBlackListID();
						Contact c = new Contact ( iCID, strPhone, strname, 0);
						da.AddBlackList(c);
						Toast.makeText(context, "Contact Added", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(AddManualBlacklistActivity.this,
								ShowBlackListActivity.class);
						
						startActivity(intent);
						AddManualBlacklistActivity.this.finish();
					
					}
					else
						Toast.makeText(context, "Contact Already Added", Toast.LENGTH_LONG).show();
					
					da.CloseDataAccess();
				}
			}
		});
	}
	
}
