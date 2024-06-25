package com.example.scblocker;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SmsHistoryActivity extends Activity implements OnItemClickListener {
	SimpleCursorAdapter adapter;
	Button btnDeleteSMSH;
	Context context = SmsHistoryActivity.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms_history);

		Button btnDelSMSH = (Button) findViewById(R.id.btnDelSMSH);
		btnDelSMSH.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
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
		});

		LoadHistory();
	}

	@SuppressWarnings("deprecation")
	public void LoadHistory() {
		ListView lv = (ListView) findViewById(R.id.lvSH);
		lv.setOnItemClickListener(this);
		DataAccess da = new DataAccess(SmsHistoryActivity.this);
		Cursor c = da.GetAllSmsHistory();

		adapter = new SimpleCursorAdapter(SmsHistoryActivity.this,
				R.layout.row_sms_history, c, new String[] { DataAccess.Msg_ID,
						DataAccess.Msg_number, DataAccess.Msg_body,DataAccess.Msg_contact,
						DataAccess.Msg_date, DataAccess.Msg_time }, new int[] {
						R.id.txtSMSID, R.id.txtSMSHNo, R.id.txtSMS,
						R.id.txtcontactname,
						R.id.txtdate, R.id.texttime });
		lv.setAdapter(adapter);
		da.CloseDataAccess();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long arg) {
		final int iSMSID = Integer.parseInt(((TextView) view
				.findViewById(R.id.txtSMSID)).getText().toString());

		Builder builder = new Builder(context);
		builder.setTitle("Delete SMS History")
				.setMessage("Do you want to del this SMS History?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								DataAccess da = new DataAccess(context);
								int i = da.DeleteSMSHistory(new SmsHistory(
										iSMSID, null, null, null, null, null));
								Toast.makeText(context,
										"All " + i + " Messages Deleted...",
										Toast.LENGTH_LONG).show();
								da.CloseDataAccess();
								LoadHistory();
							}
						}).setNegativeButton("No", null).show();
	}
}
