package com.example.scblocker;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSListener extends BroadcastReceiver {
	public static final String TAG = "SMSListener";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "SMSListener Inside onReceive");
		if (intent.getAction()
				.equals("android.provider.Telephony.SMS_RECEIVED")) {
			Log.d(TAG, "SMSListener Inside getAction");
			// ////////receiving sms text and sender//////////
			Bundle bundle = intent.getExtras();
			SmsMessage[] msg = null;
			String msg_from = "";
			String msg_body = "";
			String msg_fromOriginal = "";
			if (bundle != null) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				msg = new SmsMessage[pdus.length];
				for (int i = 0; i < msg.length; i++) {
					msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					msg_fromOriginal = msg[i].getOriginatingAddress();
					msg_body = msg[i].getMessageBody();
				}
String msg_senderSub=msg_from;
				Log.d ( TAG, "FROM: " + msg_from);
				
				DataAccess da = new DataAccess(context);
				msg_from = msg_fromOriginal.substring(3);///EXTRACTING NUMBER
				Log.d ( TAG, "new NUMB: " + msg_from);
				Contact contact = new Contact(0, msg_from, null, 0);
//				if (da.CheckExistingBlackListContact(contact)) {
				if( da.CheckExistingBlackListContact(contact) || da.GetRespondStatus()==1){

					abortBroadcast(); // Block SMS to go to Inboxs
					SimpleDateFormat sdf = new SimpleDateFormat ( "yy-MM-dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat ( "hh:mm:ss");
					String strDate = sdf.format( new Date ( ));
					String strTime = sdf2.format(new Date ( ));
					Log.d ( TAG, "Contact is in BlackList");
					String strCName = da.GetContactNameFronNumber(msg_from);
					SmsHistory smsHistory = new SmsHistory (0, msg_fromOriginal, strCName, msg_body,strDate,strTime);
					da.AddSmsHistory(smsHistory);
					//abortBroadcast(); // Block SMS to go to Inboxs
					Toast.makeText(context, "SMS Blocked", Toast.LENGTH_LONG)
							.show();
				}else if(da.GetRespondStatus()==2){
					if(da.CheckExistingContact(msg_senderSub)){
						abortBroadcast(); // Block SMS to go to Inboxs
						SimpleDateFormat sdf = new SimpleDateFormat ( "yy-MM-dd");
						SimpleDateFormat sdf2 = new SimpleDateFormat ( "hh:mm:ss");
						String strDate = sdf.format( new Date ( ));
						String strTime = sdf2.format(new Date ( ));
						Log.d ( TAG, "Contact is in BlackList");
						String strCName = da.GetContactNameFronNumber(msg_from);
						SmsHistory smsHistory = new SmsHistory (0, msg_fromOriginal, strCName, msg_body,strDate,strTime);
						da.AddSmsHistory(smsHistory);
						//abortBroadcast(); // Block SMS to go to Inboxs
						Toast.makeText(context, "SMS Blocked", Toast.LENGTH_LONG)
								.show();	
					}}
				else if(da.GetRespondStatus()==3){
					if(!da.CheckExistingContact(msg_senderSub)){
						abortBroadcast(); // Block SMS to go to Inboxs
						SimpleDateFormat sdf = new SimpleDateFormat ( "yy-MM-dd");
						SimpleDateFormat sdf2 = new SimpleDateFormat ( "hh:mm:ss");
						String strDate = sdf.format( new Date ( ));
						String strTime = sdf2.format(new Date ( ));
						Log.d ( TAG, "Contact is in BlackList");
						String strCName = da.GetContactNameFronNumber(msg_from);
						SmsHistory smsHistory = new SmsHistory (0, msg_fromOriginal, strCName, msg_body,strDate,strTime);
						da.AddSmsHistory(smsHistory);
						//abortBroadcast(); // Block SMS to go to Inboxs
						Toast.makeText(context, "SMS Blocked", Toast.LENGTH_LONG)
								.show();	
					}}
					da.CloseDataAccess();
				}
			}
		}
	}

