package com.example.scblocker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class SMSService extends Service {
	public static final String TAG = "SMSService";
	public static boolean bSMSStarted;
	SMSListener smsListener = new SMSListener();
	NotificationManager mNotificationManager;
	private int count = 0;
	private static final int NOTIFY_ME_ID = 1337;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
		// SMSListener smsListener = new SMSListener();
		IntentFilter ifSMS = new IntentFilter(
				"android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(smsListener, ifSMS);
		bSMSStarted = true;
		Log.d(TAG, "BR Registered");
		setNotification("SMS Blocking Service Started");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(smsListener);
		super.onDestroy();
		bSMSStarted = false;
		Log.d(TAG, "onDestroy");
	}

	// Notification on TitleBar////
	private void setNotification(String notificationMessage) {
		int requestID = (int) System.currentTimeMillis();
		final NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification note = new Notification(R.drawable.s,
				notificationMessage, System.currentTimeMillis());

		Intent notificationIntent = new Intent(getApplicationContext(),
				MainActivity.class);

		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);

		PendingIntent i = PendingIntent.getActivity(this, requestID,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		note.setLatestEventInfo(this, "Call/SMS Blocker", notificationMessage,
				i);
		note.number = ++count;
		mNotificationManager.notify(NOTIFY_ME_ID, note);

	}
	// //////////////////////////////////
}
