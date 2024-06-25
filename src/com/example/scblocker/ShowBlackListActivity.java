package com.example.scblocker;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ShowBlackListActivity extends Activity {
	Context context = ShowBlackListActivity.this;
	SimpleCursorAdapter adapter;
	

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_blacklist);
		
		Button btnBack = (Button) findViewById(R.id.btnBack);
		
		btnBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ShowBlackListActivity.this,
						AddBlackList.class);
				startActivity(intent);
				ShowBlackListActivity.this.finish();
			}
		});
		
		

		DataAccess da = new DataAccess(context);
		Cursor c = da.GetAllBlackList();

		ListView lv;

		adapter = new SimpleCursorAdapter(context,
				R.layout.row_blacklist_contacts, c, new String[] { DataAccess.T_CN,
				DataAccess.T_NO, DataAccess.T_ID }, new int[] { R.id.txtBLCName,
						R.id.txtBLCNumber, R.id.txtBLCID });

		lv = (ListView) findViewById(R.id.lvBlackListContacts);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new BlacklistClick());
	
	Button btnDeleteAllResponder = (Button)findViewById(R.id.btnDelresponderlist);
	btnDeleteAllResponder.setOnClickListener(new View.OnClickListener() {
		

		public void onClick(View v) {
			
			Builder builder = new Builder(context);
			
			builder.setTitle("Delete Responder Contact")
					.setMessage("Do you want to del all blacklist ?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									
			                     DataAccess da = new DataAccess(context);
			                     Toast.makeText(ShowBlackListActivity.this,
					              da.RemoveAllBlacklist() + " Deleted",
				                 	Toast.LENGTH_LONG).show();
			                        da.CloseDataAccess();
			                        Intent intent= new Intent(ShowBlackListActivity.this,ShowBlackListActivity.class);
			                       startActivity(intent);
	                              	}
								
					               }).setNegativeButton("No", null).show();
		                       }
		
	                        });
		
                        
	
                       }

	public class BlacklistClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long arg3) {
			final String strNumber = ((TextView) view
					.findViewById(R.id.txtBLCNumber)).getText().toString();
			
			Builder builder = new Builder(context);
			builder.setTitle("Delete Contact?")
					.setMessage("Are you Sure?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									DataAccess da = new DataAccess(context);
									Contact contact = new Contact(0, strNumber,
											"", 0);
									da.RemoveBlackListByNumber(contact);
									Toast.makeText(context, "Contact Deleted",
											Toast.LENGTH_LONG).show();
									Intent intentCallHistory = new Intent ( ShowBlackListActivity.this, ShowBlackListActivity.class);
									startActivity( intentCallHistory);
									ShowBlackListActivity.this.finish();
									da.CloseDataAccess();
								}
							}).setNegativeButton("No", null).show();

		}
	}

}
