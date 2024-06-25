package com.example.scblocker;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CallHistoryOptions extends Activity {
	SimpleCursorAdapter adapter;
	Button btnDelCallH;
	Context context = CallHistoryOptions.this;
	String[] menuoptions = { "Options", };

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calls);
		LoadHistory();
	}

	@SuppressWarnings("deprecation")
	public void LoadHistory() {
		ListView lv = (ListView) findViewById(R.id.lvCallHistory);
		DataAccess da = new DataAccess(CallHistoryOptions.this);
		Cursor c = da.GetAllCallHistory();

		adapter = new SimpleCursorAdapter(CallHistoryOptions.this,
				R.layout.row_call_history, c, new String[] {
						DataAccess.Call_ID, DataAccess.Call_Number,
						DataAccess.Call_Date, DataAccess.Call_Time,
						DataAccess.Call_Contact }, new int[] { R.id.txtCallID,
						R.id.txtCallHNo, R.id.txtdate, R.id.texttime,
						R.id.txtcontactname });
		lv.setAdapter(adapter);
		da.CloseDataAccess();
		registerForContextMenu(lv);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		if (item.getTitle() == "Call") {
			// Toast.makeText(context, "Pos:" + info.position + " ID:" + info.id
			// , Toast.LENGTH_LONG).show();

			Intent intent = new Intent(this, CallActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// intent.putExtra("SMessage", callno.getText().toString().trim());
			intent.putExtra("CHID", (int) info.id);
			startActivity(intent);
			Toast.makeText(this, "Add invoked", Toast.LENGTH_SHORT).show();

		} else if (item.getTitle() == "Send SMS") {
			Intent intent = new Intent(this, SmsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			DataAccess da = new DataAccess(context);
			CallHistory ch = da.GetCallHistory((int) info.id);
			intent.putExtra("SMessage", ch.getCall_number());
			startActivity(intent);
			Toast.makeText(this, "sms sending", Toast.LENGTH_SHORT).show();
		}

		else if (item.getTitle() == "Delete All") {

			{
				Builder builder = new Builder(context);
				builder.setTitle("Delete Call History")
						.setMessage("Do you want to del all Call History?")
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										DataAccess da = new DataAccess(context);
										int i = da.ZapCallHistory();
										Toast.makeText(
												context,
												"All " + i
														+ " Calls Deleted...",
												Toast.LENGTH_LONG).show();
										da.CloseDataAccess();
										LoadHistory();
									}
								}).setNegativeButton("No", null).show();

			}
			Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
		}

		else if (item.getTitle() == "Delete") {
			DataAccess da = new DataAccess(context);
			int i = da.DeleteCallHistory(new CallHistory((int) info.id, null,
					null, null, null));
			Toast.makeText(context, i + " Call Records Deleted",
					Toast.LENGTH_SHORT).show();
			da.CloseDataAccess();
			LoadHistory();
			Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
		}

		else {
			{
				Builder builder = new Builder(context);
				builder.setTitle("Delete Call History")
						.setMessage("Do you want to del all Call History?")
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										DataAccess da = new DataAccess(context);
										int i = da.ZapCallHistory();
										Toast.makeText(
												context,
												"All " + i
														+ " Calls Deleted...",
												Toast.LENGTH_LONG).show();
										da.CloseDataAccess();
										LoadHistory();
									}
								}).setNegativeButton("No", null).show();

			}
			Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
			/*
			 * Intent intent =new Intent(this,ShowBlackListActivity.class);
			 * startActivity(intent);
			 */
			return false;
		}

		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.lvCallHistory) {
			String[] menuItems = { "Call", "Send SMS", "Delete All", "Delete" };
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}

	}
}
