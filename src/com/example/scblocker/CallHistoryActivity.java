package com.example.scblocker;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
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

public class CallHistoryActivity extends Activity implements
		OnItemClickListener {
	SimpleCursorAdapter adapter;
	Button btnDelCallH;
	Context context = CallHistoryActivity.this;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_history);

		btnDelCallH = (Button) findViewById(R.id.btnDelCallH);
		btnDelCallH.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
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
		});

		LoadHistory();
	}

	@SuppressWarnings("deprecation")
	public void LoadHistory() {
		ListView lv = (ListView) findViewById(R.id.lvCallHistory);
		lv.setOnItemClickListener(this);
		DataAccess da = new DataAccess(CallHistoryActivity.this);
		Cursor c = da.GetAllCallHistory();

		adapter = new SimpleCursorAdapter(CallHistoryActivity.this,
				R.layout.row_call_history, c, new String[] {
						DataAccess.Call_ID, DataAccess.Call_Number,
						DataAccess.Call_Date, DataAccess.Call_Time,DataAccess.Call_Contact },
				new int[] { R.id.txtCallID, R.id.txtCallHNo, R.id.txtdate,
						R.id.texttime,R.id.txtcontactname });
		lv.setAdapter(adapter);
		da.CloseDataAccess();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long arg) {
		final int iCallID = Integer.parseInt(((TextView) view
				.findViewById(R.id.txtCallID)).getText().toString());
		Builder builder = new Builder(context);
		builder.setMessage("Do you want to del this Call History?")
				.setTitle("Call History Delete")
				.setIcon(R.drawable.callhistory)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								DataAccess da = new DataAccess(context);
								int i = da.DeleteCallHistory(new CallHistory(iCallID,
										null, null, null, null));
								Toast.makeText(context, i + " Call Records Deleted", Toast.LENGTH_SHORT).show();
								da.CloseDataAccess();
								LoadHistory ( );
							}
						}).setNegativeButton("No", null).show();
	}
}
