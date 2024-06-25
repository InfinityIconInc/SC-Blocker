package com.example.scblocker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataAccess {
	public static final String TAG = "DataAccess";

	public static final String DB_NAME = "blocker.dat";
	public static final int DB_VERSION = 1;
	// black list table
	public static final String T_NAME = "blacklist";
	public static final String T_ID = "_id";
	public static final String T_NO = "bl_number"; // Number
	public static final String T_CN = "bl_name"; // Contact Name
	public static final String T_OP = "bl_option"; // SMS/CALL/BOTH
	// sms history table
	public static final String Table_NAME = "SmsHistory";
	public static final String Msg_ID = "_id";
	public static final String Msg_number = "msg_number";
	public static final String Msg_contact = "msg_contact";
	public static final String Msg_body = "msg_body";
	public static final String Msg_date = "msg_date";
	public static final String Msg_time = "msg_time";
	// call history table
	public static final String Tab_NAME = "CallHistory";
	public static final String Call_ID = "_id";
	public static final String Call_Number = "call_number";
	public static final String Call_Contact = "call_contact";
	public static final String Call_Date = "call_date";
	public static final String Call_Time = "call_time";
	// phone contacts table
	DBHelper dbHelper;
	public static final String TBL_NAME = "contacts";
	public static final String C_ID = "_id";
	public static final String C_NAME = "contact_name";
	public static final String C_NUMBER = "contact_number";
	// options
	public static final String T_OPTIONS = "options";
	public static final String O_ID = "_id";// 1=All Block 2=Alow From Contacts
											// 3 unsaved
											// Only
	public static final String O_VALUE = "o_value";
	// setting password
	public static final String Ta_name = "settingpassword";
	public static final String PasswordID = "_id";
	public static final String StrEnterPassword = "enter_password";
	public static final String strConfrmPasswor = "confrm_password";
	Context context;
	SQLiteDatabase db;

	// data access k constructor k andar db helper ka constructor hay for
	// security
	public DataAccess(Context context) {
		this.context = context;
		dbHelper = new DBHelper(context);
	}

	// //////
	public int EditBlacklist(Contact sit) {
		db = dbHelper.getWritableDatabase();
		Log.d(TAG, "Inside EditSITUATION");

		ContentValues cv = new ContentValues();
		// cv.put(S_ID, sit.getSituation_id());
		cv.put(C_ID, sit.getBl_id());
		cv.put(C_NAME, sit.getBl_contact());
		cv.put(C_NUMBER, sit.getBl_number());
		// String strSQL = String.format(
		// "UPDATE %s SET %s = %s, %s = %s WHERE %s = %s", T_SITUATION,
		// S_NAME, sit.getSituation_name(), S_MSG, sit.getSituation_msg(),
		// S_ID, sit.getSituation_id());
		// db.execSQL(String.format("UPDATE %s SET %s = '%s', %s = '%s' WHERE %s = %s",
		// T_SITUATION, S_NAME, sit.getSituation_name(), S_MSG,
		// sit.getSituation_msg(), S_ID, sit.getSituation_id()));
		return db.update(TBL_NAME, cv, C_ID + " = " + sit.getBl_id(), null);
	}

	// ///////
	public boolean GetBlockAllStatus() {
		db = dbHelper.getReadableDatabase();
		boolean bStatus;
		Cursor c = db.query(T_OPTIONS, new String[] { O_VALUE }, O_ID + " = "
				+ 1, null, null, null, null);
		c.moveToFirst();
		if (c.getInt(c.getColumnIndex(O_VALUE)) == 0)
			bStatus = false;
		else
			bStatus = true;

		return bStatus;
	}

	public int SetBlockALLStatus(boolean status) {
		db = dbHelper.getWritableDatabase();
		int i = 0;
		if (status) {
			ContentValues cvtrue = new ContentValues();
			cvtrue.put(O_ID, 1);
			cvtrue.put(O_VALUE, 0);
			i = db.update(T_OPTIONS, cvtrue, O_ID + " = " + 1, null);
		} else {
			ContentValues cvfalse = new ContentValues();
			cvfalse.put(O_ID, 1);
			cvfalse.put(O_VALUE, 0);
			i = db.update(T_OPTIONS, cvfalse, O_ID + " = " + 1, null);
		}
		return i;
	}

	public boolean GetunsavedStatus() {
		db = dbHelper.getReadableDatabase();
		boolean bStatus;
		Cursor c = db.query(T_OPTIONS, new String[] { O_VALUE }, O_ID + " = "
				+ 1, null, null, null, null);
		c.moveToFirst();
		if (c.getInt(c.getColumnIndex(O_VALUE)) == 1)
			bStatus = false;
		else
			bStatus = true;

		return bStatus;
	}

	public int SetunsavedStatus(boolean status) {
		db = dbHelper.getWritableDatabase();
		int i = 0;
		if (status) {
			ContentValues cvtrue = new ContentValues();
			cvtrue.put(O_ID, 1);
			cvtrue.put(O_VALUE, 1);
			i = db.update(T_OPTIONS, cvtrue, O_ID + " = " + 1, null);
		} else {
			ContentValues cvfalse = new ContentValues();
			cvfalse.put(O_ID, 1);
			cvfalse.put(O_VALUE, 0);
			i = db.update(T_OPTIONS, cvfalse, O_ID + " = " + 1, null);
		}
		return i;
	}

	public boolean GetcontactsStatus() {
		db = dbHelper.getReadableDatabase();
		boolean bStatus;
		Cursor c = db.query(T_OPTIONS, new String[] { O_VALUE }, O_ID + " = "
				+ 1, null, null, null, null);
		c.moveToFirst();
		if (c.getInt(c.getColumnIndex(O_VALUE)) == 2)
			bStatus = false;
		else
			bStatus = true;

		return bStatus;
	}

	public int SetcontactsStatus(boolean status) {
		db = dbHelper.getWritableDatabase();
		int i = 0;
		if (status) {
			ContentValues cvtrue = new ContentValues();
			cvtrue.put(O_ID, 1);
			cvtrue.put(O_VALUE, 2);
			i = db.update(T_OPTIONS, cvtrue, O_ID + " = " + 1, null);
		} else {
			ContentValues cvfalse = new ContentValues();
			cvfalse.put(O_ID, 1);
			cvfalse.put(O_VALUE, 0);
			i = db.update(T_OPTIONS, cvfalse, O_ID + " = " + 1, null);
		}
		return i;
	}

	// ////////// iqra
	public int GetRespondStatus() {
		db = dbHelper.getReadableDatabase();
		int iStatus;// 0 respond list, 1 respond everyone, 2 respond contacts
		Cursor c = db.query(T_OPTIONS, new String[] { O_VALUE }, O_ID + " = "
				+ 1, null, null, null, null);
		c.moveToFirst();
		if (c.getInt(c.getColumnIndex(O_VALUE)) == 0)
			iStatus = 0;// Respnod List only
		else if (c.getInt(c.getColumnIndex(O_VALUE)) == 1)
			iStatus = 1;// Respond Everyone
		else if (c.getInt(c.getColumnIndex(O_VALUE)) == 2)
			iStatus = 2;// Respond Everyone

		else
			iStatus = 3;// Respond Contacts Only

		return iStatus;
	}

	public int SetRespondStatus(int status) {
		db = dbHelper.getWritableDatabase();
		int i = 0;
		if (status == 1) {// Respond Everyone /// Block everyone
			ContentValues cvtrue = new ContentValues();
			cvtrue.put(O_ID, 1);
			cvtrue.put(O_VALUE, 1);
			i = db.update(T_OPTIONS, cvtrue, O_ID + " = " + 1, null);
		} else if (status == 2) {// Contacts Only/// block contacts only
			ContentValues cvfalse = new ContentValues();
			cvfalse.put(O_ID, 1);
			cvfalse.put(O_VALUE, 2);
			i = db.update(T_OPTIONS, cvfalse, O_ID + " = " + 1, null);
		} else if (status == 3) {// Contacts Only/// block unsaved only
			ContentValues cvfalse = new ContentValues();
			cvfalse.put(O_ID, 1);
			cvfalse.put(O_VALUE, 3);
			i = db.update(T_OPTIONS, cvfalse, O_ID + " = " + 1, null);

		} else {// Respond ResponderList
			ContentValues cvfalse = new ContentValues();
			cvfalse.put(O_ID, 1);
			cvfalse.put(O_VALUE, 0);
			i = db.update(T_OPTIONS, cvfalse, O_ID + " = " + 1, null);
		}
		return i;
	}

	// ////////////// iqra

	public Cursor GetAllBlackList() {
		db = dbHelper.getReadableDatabase();
		return db.query(T_NAME, null, null, null, null, null, null);
	}

	public Cursor GetAllSmsHistory() {
		db = dbHelper.getReadableDatabase();
		return db.query(Table_NAME, null, null, null, null, null, null);
	}

	public Cursor GetAllCallHistory() {
		db = dbHelper.getReadableDatabase();
		return db.query(Tab_NAME, null, null, null, null, null, null);
	}

	public CallHistory GetCallHistory(int iCHID) {
		db = dbHelper.getReadableDatabase();
		Cursor c = db.query(Tab_NAME, null, Call_ID + " = " + iCHID, null,
				null, null, null);
		c.moveToFirst();
		CallHistory ch = null;
		if (c.getCount() > 0) {
			ch = new CallHistory(c.getInt(c.getColumnIndex(Call_ID)),
					c.getString(c.getColumnIndex(Call_Number)), c.getString(c
							.getColumnIndex(Call_Contact)), c.getString(c
							.getColumnIndex(Call_Date)), c.getString(c
							.getColumnIndex(Call_Time)));
		}
		return ch;
	}

	public int GetMaxBlackListID() {
		db = dbHelper.getReadableDatabase();
		int blid = 0;
		Cursor c = db.rawQuery(
				String.format("SELECT MAX(_id) FROM %s", T_NAME), null);
		c.moveToFirst();
		Log.d(TAG, "COUT: " + c.getCount());
		if (c.getCount() > 0) {
			if (c.getString(c.getColumnIndex("MAX(_id)")) == null)
				blid = 0;
			else
				blid = Integer.parseInt(c.getString(c
						.getColumnIndex("MAX(_id)")));
		} else
			blid = 0;

		blid = blid + 1;
		return blid;
	}

	public int GetMaxCallHistoryID() {
		db = dbHelper.getReadableDatabase();
		int callid = 0;
		Cursor c = db.rawQuery(
				String.format("SELECT MAX(_id) FROM %s", Tab_NAME), null);
		c.moveToFirst();
		Log.d(TAG, "COUT: " + c.getCount());
		if (c.getCount() > 0) {
			if (c.getString(c.getColumnIndex("MAX(_id)")) == null)
				callid = 0;
			else
				callid = Integer.parseInt(c.getString(c
						.getColumnIndex("MAX(_id)")));
		} else
			callid = 0;

		callid = callid + 1;
		return callid;
	}

	public int GetMaxSmsHistoryID() {
		db = dbHelper.getReadableDatabase();
		int smsid = 0;
		Cursor c = db.rawQuery(
				String.format("SELECT MAX(_id) FROM %s", Table_NAME), null);
		c.moveToFirst();
		Log.d(TAG, "COUT: " + c.getCount());
		if (c.getCount() > 0) {
			if (c.getString(c.getColumnIndex("MAX(_id)")) == null)
				smsid = 0;
			else
				smsid = Integer.parseInt(c.getString(c
						.getColumnIndex("MAX(_id)")));
		} else
			smsid = 0;

		smsid = smsid + 1;
		return smsid;
	}

	// add in a black list
	public long AddBlackList(Contact blacklist) {
		long l;
		db = dbHelper.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(T_ID, blacklist.getBl_id());
		cv.put(T_CN, blacklist.getBl_contact());
		cv.put(T_NO, blacklist.getBl_number());
		cv.put(T_OP, blacklist.getBl_option());

		l = db.insert(T_NAME, null, cv);
		return l;
	}

	// add in a sms history
	public long AddSmsHistory(SmsHistory history) {
		long smshistory;
		db = dbHelper.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(Msg_ID, this.GetMaxSmsHistoryID());
		cv.put(Msg_number, history.getmsg_number());
		cv.put(Msg_contact, history.getmsg_contact());
		cv.put(Msg_body, history.getmsg_body());
		cv.put(Msg_date, history.getsms_date());
		cv.put(Msg_time, history.getsms_time());

		smshistory = db.insert(Table_NAME, null, cv);
		return smshistory;
	}

	// add in a call history
	public long AddCallHistory(CallHistory history) {
		long callhistory;
		db = dbHelper.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(Call_ID, history.getCall_id());
		cv.put(Call_Number, history.getCall_number());
		cv.put(Call_Contact, history.getCall_contact());
		cv.put(Call_Date, history.getCall_date());
		cv.put(Call_Time, history.getCall_time());

		callhistory = db.insert(Tab_NAME, null, cv);
		return callhistory;
	}

	// zap is use to delete all in foxpro language
	public int ZapCallHistory() {
		db = dbHelper.getWritableDatabase();
		return db.delete(Tab_NAME, "1", null);
	}

	public int ZapSMSHistory() {
		db = dbHelper.getWritableDatabase();
		return db.delete(Table_NAME, "1", null);
	}

	public int DeleteSMSHistory(SmsHistory sh) {
		db = dbHelper.getWritableDatabase();
		return db.delete(Table_NAME, Msg_ID + " = " + sh.getmsg_id(), null);
	}

	public int DeleteCallHistory(CallHistory ch) {
		db = dbHelper.getWritableDatabase();
		return db.delete(Tab_NAME, Call_ID + " = " + ch.getCall_id(), null);
	}

	// ///////////
	public int DeleteBlackListById(Contact ch) {
		db = dbHelper.getWritableDatabase();
		return db.delete(T_NAME, C_ID + " = " + ch.getBl_id(), null);
	}

	public boolean CheckExistingBlackListContact(Contact contact) {
		db = dbHelper.getReadableDatabase();
		boolean bExists = false;
		Cursor c = db.rawQuery("SELECT * FROM " + T_NAME + " WHERE " + T_NO
				+ " LIKE '%" + contact.getBl_number() + "%'", null);
		if (c.getCount() == 0) {
			Log.d(TAG, "No Match Found " + c.getCount());
			bExists = false;
		} else {
			Log.d(TAG, "Match Found " + c.getCount());
			bExists = true;
		}
		return bExists;
	}

	public String GetContactNameFronNumber(String strNo) {
		db = dbHelper.getReadableDatabase();

		Cursor c = db.rawQuery("SELECT * FROM " + TBL_NAME + " WHERE "
				+ C_NUMBER + " LIKE '%" + strNo + "%'", null);
		c.moveToFirst();
		if (c.getCount() == 0)
			return "Unknown Contact";
		else
			return c.getString(c.getColumnIndex(C_NAME)).toString();
	}

	// edit in a black list,sms history,call history

	public int EditBlackList(Contact blacklist) {
		db = dbHelper.getWritableDatabase();
		// it take 2 values one queion one its answer answer may be in any data
		// type be carefull while choosing it type
		ContentValues cv = new ContentValues();
		cv.put(T_ID, blacklist.getBl_id());
		cv.put(T_CN, blacklist.getBl_contact());
		cv.put(T_NO, blacklist.getBl_number());
		cv.put(T_OP, blacklist.getBl_option());

		return db.update(T_NAME, cv, T_ID,
				new String[] { Integer.toString(blacklist.getBl_id()) });
	}

	public int EditSmsHistory(SmsHistory history) {
		db = dbHelper.getWritableDatabase();

		ContentValues ch = new ContentValues();
		ch.put(Msg_ID, history.getmsg_id());
		ch.put(Msg_number, history.getmsg_number());
		ch.put(Msg_contact, history.getmsg_contact());
		ch.put(Msg_body, history.getmsg_body());
		ch.put(Msg_date, history.getsms_date());
		ch.put(Msg_time, history.getsms_time());

		return db.update(Table_NAME, ch, Msg_ID,
				new String[] { Integer.toString(history.getmsg_id()) });
	}

	public int EditCallHistory(CallHistory history) {
		db = dbHelper.getWritableDatabase();

		ContentValues ch = new ContentValues();
		ch.put(Call_ID, history.getCall_id());
		ch.put(Call_Number, history.getCall_number());
		ch.put(Call_Contact, history.getCall_contact());
		ch.put(Call_Date, history.getCall_date());
		ch.put(Call_Time, history.getCall_time());

		return db.update(Tab_NAME, ch, Call_ID,
				new String[] { Integer.toString(history.getCall_id()) });
	}

	// remove all blacklist,sms history,call history.

	public void RemoveAllBlackList() {
		db = dbHelper.getWritableDatabase();
		String strSQL = String.format("DELETE FROM %s", T_NAME);
		db.execSQL(strSQL);
	}

	// //
	public int RemoveAllBlacklist() {
		db = dbHelper.getWritableDatabase();
		// String strSQL = String.format("DELETE FROM %s", T_RESPONDER);
		// return db.execSQL(strSQL);
		return db.delete(T_NAME, "1", null);
	}

	public void RemoveAllSmsHistory() {
		db = dbHelper.getWritableDatabase();
		String strSQL = String.format("DELETE FROM %s", Table_NAME);
		db.execSQL(strSQL);
	}

	public void RemoveAllCallHistory() {
		db = dbHelper.getWritableDatabase();
		String strSQL = String.format("DELETE FROM %s", Tab_NAME);
		db.execSQL(strSQL);
	}

	public void RemoveBlackListByNumber(Contact contact) {
		db = dbHelper.getWritableDatabase();

		db.execSQL(String.format("DELETE FROM %s WHERE %s = '%s'", T_NAME,
				T_NO, contact.getBl_number()));
	}

	public void RemoveSmsHistoryByMSGNumber(SmsHistory history) {
		db = dbHelper.getWritableDatabase();

		db.execSQL(String.format("DELETE FROM %s WHERE %s = '%s'", Table_NAME,
				Msg_number, history.getmsg_number()));
	}

	public void RemoveCallHistoryByMSGNumber(CallHistory history) {
		db = dbHelper.getWritableDatabase();

		db.execSQL(String.format("DELETE FROM %s WHERE %s = '%s'", Tab_NAME,
				Msg_number, history.getCall_number()));
	}

	public void RemoveBlackListByID(Contact blacklist) {
		db = dbHelper.getWritableDatabase();
		String strSQL = String.format("DELETE FROM %s WHERE %s = %s", T_NAME,
				T_ID, blacklist.getBl_id());
		db.execSQL(strSQL);
	}

	public void RemoveSmsHistoryByID(SmsHistory history) {
		db = dbHelper.getWritableDatabase();
		String strSQL = String.format("DELETE FROM %s WHERE %s = %s",
				Table_NAME, Msg_ID, history.getmsg_id());
		db.execSQL(strSQL);
	}

	public void RemoveCallHistoryByID(CallHistory history) {
		db = dbHelper.getWritableDatabase();
		String strSQL = String.format("DELETE FROM %s WHERE %s = %s",
				Table_NAME, Msg_ID, history.getCall_id());
		db.execSQL(strSQL);
	}

	public Cursor DisplayAllContacts() {
		db = dbHelper.getReadableDatabase();
		return db.query(TBL_NAME, null, null, null, null, null, null);
	}

	public long AddContact(Contact contact) {
		db = dbHelper.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(C_ID, contact.getBl_id());
		cv.put(C_NAME, contact.getBl_contact());
		cv.put(C_NUMBER, contact.getBl_number());

		return db.insert(TBL_NAME, null, cv);
	}

	public int GetMaximumContactID() {
		db = dbHelper.getReadableDatabase();
		int iCID = 0;

		String strSQL = String.format("SELECT MAX(_id) FROM %s", TBL_NAME);
		Cursor c = db.rawQuery(strSQL, null);

		if (c.getCount() > 0)
			iCID = c.getInt(c.getColumnIndex("MAX(_id)"));

		iCID = iCID + 1;
		return iCID;
	}

	public boolean CheckExistingContact(Contact contact) {
		db = dbHelper.getReadableDatabase();
		boolean bExists = false;
		Cursor c = db.rawQuery("SELECT * FROM " + TBL_NAME + " WHERE "
				+ C_NUMBER + " LIKE '%" + contact.getBl_number() + "%'", null);

		if (c.getCount() == 0) {
			Log.d(TAG, "No Match Found " + c.getCount());
			bExists = false;
		} else {
			Log.d(TAG, "Match Found " + c.getCount());
			bExists = true;
		}
		return bExists;
	}

	// ////////////////iqra
	public boolean CheckExistingContact(String strContact) {
		db = dbHelper.getReadableDatabase();
		Log.d(TAG, "Inside CheckExistingContact");

		boolean rExists = false;
		Cursor c = db.rawQuery("SELECT * FROM " + TBL_NAME + " WHERE "
				+ C_NUMBER + " LIKE '%" + strContact + "%'", null);
		if (c.getCount() == 0) {
			Log.d(TAG, "No Match Found " + c.getCount());
			rExists = false;
		} else {
			Log.d(TAG, "Match Found " + c.getCount());
			rExists = true;
		}
		return rExists;
	}

	// ////////////

	public void DeleteAllContacts() {
		db = dbHelper.getWritableDatabase();
		db.execSQL(String.format("DELETE FROM %s", TBL_NAME));
	}

	public void SetBlockAllCalls() {
		db = dbHelper.getWritableDatabase();
		db.execSQL(String.format("UPDATE %s SET %s = 1 WHERE %s = 1",
				T_OPTIONS, O_VALUE, O_ID));
	}

	public void SetUnBlockAllCalls() {
		db = dbHelper.getWritableDatabase();
		db.execSQL(String.format("UPDATE %s SET %s = 0 WHERE %s = 1",
				T_OPTIONS, O_VALUE, O_ID));
	}

	public void SetContactsOnly() {
		db = dbHelper.getWritableDatabase();
		db.execSQL(String.format("UPDATE %s SET %s = 1 WHERE %s = 2",
				T_OPTIONS, O_VALUE, O_ID));
	}

	public void SetUnContactsOnly() {
		db = dbHelper.getWritableDatabase();
		db.execSQL(String.format("UPDATE %s SET %s = 0 WHERE %s = 2",
				T_OPTIONS, O_VALUE, O_ID));
	}

	public boolean GetBlockAllCallsStatus() {
		db = dbHelper.getReadableDatabase();
		boolean bStatus = false;

		Cursor c = db.query(T_OPTIONS, null, O_ID + " = 1 AND " + O_VALUE
				+ " = 1", null, null, null, null);
		if (c.getCount() > 0)
			bStatus = true;

		return bStatus;
	}

	public boolean GetContactsOnlyStatus() {// calls accept withing addressbook
		db = dbHelper.getReadableDatabase();
		boolean bStatus = false;

		Cursor c = db.query(T_OPTIONS, null, O_ID + " = 2 AND " + O_VALUE
				+ " = 1", null, null, null, null);
		if (c.getCount() > 0)
			bStatus = true;

		return bStatus;
	}

	// ///////////////////

	// ////////////

	public void CloseDataAccess() {
		dbHelper.close();
	}

	private class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION); // null is extra
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(String
					.format("CREATE TABLE %s ( %s int PRIMARY KEY, %s text, %s text, %s int )",
							T_NAME, T_ID, T_NO, T_CN, T_OP));
			// ////
			db.execSQL(String.format(
					"CREATE TABLE %s ( %s int PRIMARY KEY, %s text, %s text )",
					Ta_name, PasswordID, StrEnterPassword, strConfrmPasswor));
			// ///
			db.execSQL(String
					.format("CREATE TABLE %s ( %s int PRIMARY KEY, %s text,%s int, %s text,%s text,%s text)",
							Table_NAME, Msg_ID, Msg_number, Msg_contact,
							Msg_body, Msg_date, Msg_time));
			db.execSQL(String
					.format("CREATE TABLE %s ( %s int PRIMARY KEY, %s text,%s int, %s text,%s text)",
							Tab_NAME, Call_ID, Call_Number, Call_Contact,
							Call_Date, Call_Time));
			db.execSQL(String
					.format("CREATE TABLE %s ( %s int PRIMARY KEY,  %s text, %s text )",
							TBL_NAME, C_ID, C_NAME, C_NUMBER));
			db.execSQL(String.format(
					"CREATE TABLE %s ( %s int PRIMARY KEY, %s int)", T_OPTIONS,
					O_ID, O_VALUE));
			db.execSQL(String.format(
					"INSERT INTO %s ( %s, %s ) VALUES ( %s, %s )", T_OPTIONS,
					O_ID, O_VALUE, 1, 0));
			db.execSQL(String.format(
					"INSERT INTO %s ( %s, %s ) VALUES ( %s, %s )", T_OPTIONS,
					O_ID, O_VALUE, 2, 0));
		}

		// database version updation by changing the version 1 to 2
		// thats why we use drop table

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(String.format("DROP TABLE %s", T_NAME));
			db.execSQL(String.format("DROP TABLE %s", Table_NAME));
			db.execSQL(String.format("DROP TABLE %s", Tab_NAME));
			db.execSQL(String.format("DROP TABLE %s", TBL_NAME));
			db.execSQL(String.format("DROP TABLE %s", T_OPTIONS));
			db.execSQL(String.format("DROP TABLE %s", Ta_name));

			onCreate(db);
		}

	}

}
