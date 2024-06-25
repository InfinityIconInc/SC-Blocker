package com.example.scblocker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AddBlackList extends Activity {
	public static final String TAG = "AddBlackList";
	ListView lvContacts;
	
	Context context = AddBlackList.this;
	ProgressBar pbContacts;
	TextView tvNOC;//title textview of activity
	Button btnshowblacklist;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addblacklist);

		tvNOC = ( TextView ) findViewById( R.id.tvNOC);//title of activity
		lvContacts = (ListView) findViewById(R.id.lstContacts);
		Button btnBack = (Button) findViewById(R.id.btnBack);
		Button btnAddNumberManually = (Button) findViewById(R.id.btnAddNumberManually);
		pbContacts = ( ProgressBar ) findViewById(R.id.pbContacts);//ProgressBar
		Button btnshowblacklist = (Button) findViewById(R.id.btnshowblacklist);
		

		
		btnshowblacklist.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddBlackList.this,
						ShowBlackListActivity.class);
				startActivity(intent);
			
			}
		});

		btnAddNumberManually.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentManual = new Intent(AddBlackList.this,
						AddManualBlacklistActivity.class);
				startActivity(intentManual);
				
			}
		});

		btnBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddBlackList.this,
						MainActivity.class);
				startActivity(intent);
				AddBlackList.this.finish();

			}
		});

		
		new ThreadedLoadContacts().execute();

	}

	public class AddToBlackList implements OnClickListener {

		@Override
		public void onClick(View v) {
			TextView tvNameRow = ((TextView) v.findViewById(R.id.tvNameRow));
			TextView tvNumberRow = ((TextView) v.findViewById(R.id.tvNumberRow));
			final ImageView imgAlert = ((ImageView) v
					.findViewById(R.id.imgAlert));

			final Contact contact = new Contact(0, tvNumberRow.getText()
					.toString(), tvNameRow.getText().toString(), 2);
			Builder builder = new Builder(AddBlackList.this);
			builder.setTitle("Add/Remove From BlackList")//
					.setMessage("Are you sure?")
					.setIcon(R.drawable.ic_launcher)//ic_launcher
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									DataAccess da = new DataAccess(
											AddBlackList.this);
									if (da.CheckExistingBlackListContact(contact)) { // if
																						// contact
																						// is
																						// in
																						// BL
										Log.d(TAG, "Contact already there");
										da.RemoveBlackListByNumber(contact);
										imgAlert.setImageResource(R.drawable.ic_launcher);//ic_launcher//img
										Toast.makeText(
												AddBlackList.this,
												"Contact "
														+ contact
																.getBl_contact()
														+ " Removed from BlackList",
												Toast.LENGTH_LONG).show();

									} else { // Contact not in BL
										int blid = da.GetMaxBlackListID();
										contact.setBl_id(blid);
										da.AddBlackList(contact);
										da.CloseDataAccess();
										imgAlert.setImageResource(R.drawable.bl);
										Toast.makeText(
												AddBlackList.this,
												"Contact: "
														+ contact
																.getBl_contact()
														+ " Added in BlackList",
												Toast.LENGTH_LONG).show();

									}
								}
							}).setNegativeButton("No", null).show();
		}

	}

	public class ThreadedLoadContacts extends AsyncTask<Void, Integer, List<Contact>> {
		int iTotalContacts; //for progressbar
		@Override
		protected List<Contact> doInBackground(Void... params) {
			Cursor c; // to hold contact list names
			Cursor cursor; // cursor to hold phone numbers for each contact
			List<Contact> contactList = new ArrayList<Contact>();// will store
																	// contacts from
																	// System
			DataAccess da = new DataAccess(AddBlackList.this);
			da.DeleteAllContacts();// delete contacts from contacts table
			String col[] = { ContactsContract.Contacts._ID,
					ContactsContract.Contacts.DISPLAY_NAME }; // What to select from
																// Contacts DB

			c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
					col, null, null, ContactsContract.Contacts.DISPLAY_NAME);
			// tv.setText("Total Contacts: " + c.getCount());
			c.moveToFirst();
			iTotalContacts = c.getCount();
			pbContacts.setMax(iTotalContacts);
			int i = 0;
			while (c.moveToNext()) {
				publishProgress ( i++);
				String strID = c.getString(c.getColumnIndex(Contacts._ID));
				String strName = c.getString(c
						.getColumnIndex(Contacts.DISPLAY_NAME));
				String strNumber = "";

				if (strID == null || strName == null)
					continue;

				cursor = context.getContentResolver().query(
						CommonDataKinds.Phone.CONTENT_URI, null,
						CommonDataKinds.Phone.CONTACT_ID + "=?",
						new String[] { strID }, null);

				if (cursor == null)
					continue;

				while (cursor.moveToNext()) {
					strNumber = cursor.getString(cursor
							.getColumnIndex(CommonDataKinds.Phone.NUMBER));
				}
				// Log.d(TAG, strID + " " + strName + " " + strNumber);
				Contact contact = new Contact();
				contact.setBl_id(Integer.parseInt(strID));
				contact.setBl_contact(strName);
				//contact.setBl_number(strNumber.replace("-", "")); // replaces - in
				String strNumber1 = strNumber.replace("-", "");
				contact.setBl_number(strNumber1.replace(" ", "")); // replaces
																	// Phone No
				da.AddContact(contact);
				contactList.add(contact);
				cursor.close();
			}
			return contactList;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			pbContacts.setProgress(values[0]);
			tvNOC.setText("Loading Contacts, Please Wait...");
		}
		
		@Override
		protected void onPostExecute(List<Contact> lc) {
			super.onPostExecute(lc);
			ContactsAdapter ca = new ContactsAdapter(AddBlackList.this,
					R.layout.row_contacts_list, lc);
			lvContacts.setAdapter(ca);
			tvNOC.setText("Tap to Add/Rem Contact from BlackList");
		}
	}

	private class ContactsAdapter extends ArrayAdapter<Contact> {
		private int layout_file;
		private List<Contact> contactList;

		public ContactsAdapter(Context context, int resource,
				List<Contact> objects) {
			super(context, resource, objects);
			layout_file = resource;
			contactList = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;

			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(layout_file, null);
			}

			Log.d(TAG, "After Inflater");

			TextView tvNameRow = ((TextView) row.findViewById(R.id.tvNameRow));
			TextView tvNumberRow = ((TextView) row
					.findViewById(R.id.tvNumberRow));
			ImageView imgAlert = ((ImageView) row.findViewById(R.id.imgAlert));

			tvNameRow.setText(contactList.get(position).getBl_contact());
			tvNumberRow.setText(contactList.get(position).getBl_number());
			if (contactList.get(position).getBl_number() != "") {
				// chk if phn no exists in contact
				// otherwise contacts w/o number were being blocked
				DataAccess da = new DataAccess(AddBlackList.this);
				imgAlert.setImageResource(R.drawable.ic_launcher);//ic_launcher//img
				if (da.CheckExistingBlackListContact(contactList.get(position))) {
					imgAlert.setImageResource(R.drawable.bl);//////////////////chnaging from logo
					Log.d(TAG, "Blocked Contact"
							+ contactList.get(position).getBl_number() + " "
							+ contactList.get(position).getBl_contact());
				} else
					Log.d(TAG, "Contact: "
							+ contactList.get(position).getBl_number() + " "
							+ contactList.get(position).getBl_contact());
				da.CloseDataAccess();
				row.setOnClickListener(new AddToBlackList());
			}//no exists if ends
			return row;
		}
	}
}
