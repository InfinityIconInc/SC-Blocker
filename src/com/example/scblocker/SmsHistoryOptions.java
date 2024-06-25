package com.example.scblocker;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

public class SmsHistoryOptions extends Activity implements OnItemClickListener {
		SimpleCursorAdapter adapter;
		Button btnDeleteSMSH;
		Context context = SmsHistoryOptions.this;
		String[] menuoptions = {
				"Options",			
		};
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_sms);
			LoadHistory();
		}

		@SuppressWarnings("deprecation")
		public void LoadHistory() {
			ListView lv = (ListView) findViewById(R.id.lvSH);
			lv.setOnItemClickListener(this);
			DataAccess da = new DataAccess(SmsHistoryOptions.this);
			Cursor c = da.GetAllSmsHistory();

			adapter = new SimpleCursorAdapter(SmsHistoryOptions.this,
					R.layout.row_sms_history, c, new String[] { DataAccess.Msg_ID,
							DataAccess.Msg_number, DataAccess.Msg_body,DataAccess.Msg_contact,
							DataAccess.Msg_date, DataAccess.Msg_time }, new int[] {
					
							R.id.txtSMSID, R.id.txtSMSHNo, R.id.txtSMS,
							R.id.txtcontactname,
							R.id.txtdate, R.id.texttime });
			lv.setAdapter(adapter);
			da.CloseDataAccess();
			registerForContextMenu(lv);
		}
		@Override
		  public boolean onContextItemSelected(MenuItem item) {
		    
		    if (item.getTitle() == "Call") {
		    	
		    	TextView  callno = (TextView)findViewById(R.id.txtSMSHNo);
		    	
				Intent intent =new Intent(this,CallActivity.class);
				
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
				intent.putExtra("SMessage", callno.getText().toString().trim());
				//intent.putExtra("SID",
				  // Integer.parseInt(callno.getText().toString().trim()));
				startActivity(intent);
				Toast.makeText(this, "Calling", Toast.LENGTH_SHORT).show();
			} 
		    else if (item.getTitle() == "Send Sms") {
		    	TextView  smsno = (TextView)findViewById(R.id.txtSMSHNo);
		    	
				Intent intent =new Intent(this,SmsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("SMessage",smsno.getText().toString().trim());
				startActivity(intent);
				Toast.makeText(this, "Sending SMS", Toast.LENGTH_SHORT).show();
			} 
		    
		    else if (item.getTitle() == "Delete ALL") {
				
				{
					Builder builder = new Builder(context);
					builder.setTitle("Delete SMS History")
							.setMessage("Do you want to del all SMS History?")
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog,
												int which) {
											DataAccess da = new DataAccess(context);
											int i = da.ZapSMSHistory();
											Toast.makeText(
													context,
													"All "
															+ i
															+ " Messages Deleted...",
													Toast.LENGTH_LONG).show();
											da.CloseDataAccess();
											LoadHistory();
										}
									}).setNegativeButton("No", null).show();
				
				}
				Toast.makeText(this, "Deleting", Toast.LENGTH_SHORT).show();
			}
		    
			else if (item.getTitle() == "Delete") {
				/*public void onItemClick(AdapterView<?> parent, View view, int pos, long arg)*/ {
					View view;
					TextView  txtSmsID = (TextView)findViewById(R.id.txtSMSID);
					final int iSmsID = (Integer.parseInt(txtSmsID.getText().toString()));
					Builder builder = new Builder(context);
					builder.setMessage("Do you want to del this Sms History?")
							.setTitle("Sms History Delete")
							.setIcon(R.drawable.smshistory)
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog,
												int which) {
											DataAccess da = new DataAccess(context);
											 int i = da.DeleteSMSHistory(new SmsHistory(iSmsID,
													null, null, null, null,null));
											Toast.makeText(context, i + " Call Records Deleted", Toast.LENGTH_SHORT).show();
											da.CloseDataAccess();
											LoadHistory ( );
										}
									}).setNegativeButton("No", null).show();
				}
				/*Intent intent =new Intent(this,CallHistoryActivity.class);
				startActivity(intent);*/
				Toast.makeText(this, "Deleting", Toast.LENGTH_SHORT).show();
			} 
		    
			else {
				{
					Builder builder = new Builder(context);
					builder.setTitle("Delete SMS History")
							.setMessage("Do you want to del all SMS History?")
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog,
												int which) {
											DataAccess da = new DataAccess(context);
											int i = da.ZapSMSHistory();
											Toast.makeText(
													context,
													"All "
															+ i
															+ " Messages Deleted...",
													Toast.LENGTH_LONG).show();
											da.CloseDataAccess();
											LoadHistory();
										}
									}).setNegativeButton("No", null).show();
				
				}
				Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
				/*Intent intent =new Intent(this,ShowBlackListActivity.class);
				startActivity(intent);*/
				return false;
			}
		    	
		    return true;
		  }
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
		    ContextMenuInfo menuInfo) {super.onCreateContextMenu(menu, v, menuInfo);
			menu.setHeaderTitle("Context Menu");
			menu.add(0, v.getId(), 0, "Call");
			menu.add(0, v.getId(), 0, "Send Sms");
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
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
		}
		}
