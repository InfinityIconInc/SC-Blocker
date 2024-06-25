package com.example.scblocker;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



public class ShowBlackListOptions extends Activity implements
OnItemClickListener {
	Context context = ShowBlackListOptions.this;
	SimpleCursorAdapter adapter;
	String[] menuoptions = {
			"Options",			
	};

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
				Intent intent = new Intent(ShowBlackListOptions.this,
						AddBlackList.class);
				startActivity(intent);
				ShowBlackListOptions.this.finish();
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
		registerForContextMenu(lv);
		
	}
	 @Override
		public void onCreateContextMenu(ContextMenu menu, View v,
		    ContextMenuInfo menuInfo) {super.onCreateContextMenu(menu, v, menuInfo);
			menu.setHeaderTitle("Context Menu");
			
			menu.add(0, v.getId(), 0, "Delete ALL");
			menu.add(0, v.getId(), 0, "Delete");
		  if (v.getId()==R.id.lvCallHistory) {
		    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		    menu.setHeaderTitle(menuoptions[info.position]);
		    String[] menuItems = getResources().getStringArray(R.array.management);
		    for (int i = 0; i<menuItems.length; i++) {
		      menu.add(Menu.NONE, i, i, menuItems[i]);
		    }
		  }
		 
		}		
	 public boolean onContextItemSelected(MenuItem item) {
		    
		    if (item.getTitle() == "Delete") {
		    	
		    	
		    	
			} 
		    
		    else if (item.getTitle() == "Delete ALL") {
				
				{
					Builder builder = new Builder(context);
					
					builder.setTitle("Delete Responder Contact")
							.setMessage("Do you want to del all blacklist ?")
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog,
												int which) {
											
					                     DataAccess da = new DataAccess(context);
					                     Toast.makeText(ShowBlackListOptions.this,
							              da.RemoveAllBlacklist() + " Deleted",
						                 	Toast.LENGTH_LONG).show();
					                        da.CloseDataAccess();
					                        Intent intent= new Intent(ShowBlackListOptions.this,ShowBlackListActivity.class);
					                       startActivity(intent);
			                              	}
										
							               }).setNegativeButton("No", null).show();
				                       }
				
			                        
			                        Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
	
		    }
		    else if (item.getTitle() == "Delete") {
				
					TextView  txtBLnumber = (TextView)findViewById(R.id.txtBLCNumber);
					final String strNumber = txtBLnumber.getText().toString();
					
					Builder builder = new Builder(context);
					builder.setTitle("Delete Contact?")
							.setMessage("Are you Sure?")
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {

										public void onClick(DialogInterface dialog,
												int which) {
											DataAccess da = new DataAccess(context);
											Contact contact = new Contact(0, strNumber,
													"", 0);
											da.RemoveBlackListByNumber(contact);
											Toast.makeText(context, "Contact Deleted",
													Toast.LENGTH_LONG).show();
											Intent intentCallHistory = new Intent ( ShowBlackListOptions.this, ShowBlackListOptions.class);
											startActivity( intentCallHistory);
											ShowBlackListOptions.this.finish();
											da.CloseDataAccess();
										}
									}).setNegativeButton("No", null).show();
					
					
			
				/*Intent intent =new Intent(this,CallHistoryActivity.class);
				startActivity(intent);*/
				Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
			} 
		    
			else {
				{
Builder builder = new Builder(context);
					
					builder.setTitle("Delete Responder Contact")
							.setMessage("Do you want to del all blacklist ?")
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog,
												int which) {
											
					                     DataAccess da = new DataAccess(context);
					                     Toast.makeText(ShowBlackListOptions.this,
							              da.RemoveAllBlacklist() + " Deleted",
						                 	Toast.LENGTH_LONG).show();
					                        da.CloseDataAccess();
					                        Intent intent= new Intent(ShowBlackListOptions.this,ShowBlackListActivity.class);
					                       startActivity(intent);
			                              	}
										
							               }).setNegativeButton("No", null).show();
				                       }
				
			                        
			                        Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
	
			}
		    	
			return true;
		   
	 }
	
	 
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}}