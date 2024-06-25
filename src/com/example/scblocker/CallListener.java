package com.example.scblocker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;


public class CallListener extends BroadcastReceiver {
	public static final String TAG = "CallListenerBR";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		//Log.d (TAG, "Inside Call Listener BR");
//		if (intent.getAction()
//				.equals("android.provider.Telephony.CALL_RECEIVED")) {
//			Log.d(TAG, "CallListener Inside getAction");
			
			if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
				Bundle bundle = intent.getExtras();
				String strState = bundle.getString(TelephonyManager.EXTRA_STATE);

			if (strState.equals(TelephonyManager.EXTRA_STATE_RINGING)) { //if phone is ringing than perform these steps
				//Toast.makeText(context, "Ringing", Toast.LENGTH_SHORT).show();
				TelephonyManager telephonyManager = (TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE);
//				Contact contact = new Contact(0, Call_from, null, 0);
//				if (da.CheckExistingBlackListContact(contact)) {
//					Log.d ( TAG, "Contact is in BlackList");
//					CallHistory CallHistory = new CallHistory (0, Call_from, null, null, null );
//					da.AddCallHistory(CallHistory);
//					abortBroadcast(); // Block calls to go to Inbox
//					Toast.makeText(context, "Call Blocked", Toast.LENGTH_LONG).show();
				String strNumberOriginal = bundle.getString("incoming_number");
				String strNumber = strNumberOriginal.substring(4);
				//Log.d ( TAG, "New: " + strNumber);
				DataAccess da = new DataAccess(context);
				
				Contact contact = new Contact ( 0, strNumber, null, 0 );
				Class clazz;
				try {
					clazz = Class
							.forName(telephonyManager.getClass().getName());
					Method method;
					method = clazz.getDeclaredMethod("getITelephony");
					method.setAccessible(true);
					ITelephony telephonyService = (ITelephony) method
							.invoke(telephonyManager);
					if( da.CheckExistingBlackListContact(contact) || da.GetRespondStatus()==1){
						telephonyService.endCall();
						int chid = da.GetMaxCallHistoryID();
						SimpleDateFormat sdf = new SimpleDateFormat ( "yy-MM-dd");
						SimpleDateFormat sdf2 = new SimpleDateFormat ( "hh:mm:ss");
						String strDate = sdf.format( new Date ( ));
						String strTime = sdf2.format(new Date ( ));
						Log.d ( TAG, "Adding DB Call Info");
						String strCName = da.GetContactNameFronNumber(strNumber);
						CallHistory history = new CallHistory (chid, strNumberOriginal, strCName, strDate, strTime );
						da.AddCallHistory(history);
						Log.d ( TAG, "Call Info Added");
					}else if(da.GetRespondStatus()==2){
						if(da.CheckExistingContact(strNumber)){
							telephonyService.endCall();
							int chid = da.GetMaxCallHistoryID();
							SimpleDateFormat sdf = new SimpleDateFormat ( "yy-MM-dd");
							SimpleDateFormat sdf2 = new SimpleDateFormat ( "hh:mm:ss");
							String strDate = sdf.format( new Date ( ));
							String strTime = sdf2.format(new Date ( ));
							Log.d ( TAG, "Adding DB Call Info");
							String strCName = da.GetContactNameFronNumber(strNumber);
							CallHistory history = new CallHistory (chid, strNumberOriginal, strCName, strDate, strTime );
							da.AddCallHistory(history);
							Log.d ( TAG, "Call Info Added");
							Toast.makeText(context, "Call Blocked", Toast.LENGTH_LONG)
									.show();	
						}}
					else if(da.GetRespondStatus()==3){
						if(!da.CheckExistingContact(strNumber)){
							telephonyService.endCall();
							int chid = da.GetMaxCallHistoryID();
							SimpleDateFormat sdf = new SimpleDateFormat ( "yy-MM-dd");
							SimpleDateFormat sdf2 = new SimpleDateFormat ( "hh:mm:ss");
							String strDate = sdf.format( new Date ( ));
							String strTime = sdf2.format(new Date ( ));
							Log.d ( TAG, "Adding DB Call Info");
							String strCName = da.GetContactNameFronNumber(strNumber);
							CallHistory history = new CallHistory (chid, strNumberOriginal, strCName, strDate, strTime );
							da.AddCallHistory(history);
							Log.d ( TAG, "Call Info Added");
							Toast.makeText(context, "Call Blocked", Toast.LENGTH_LONG)
									.show();	
						}}
					da.CloseDataAccess();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	
	}
  }
//}