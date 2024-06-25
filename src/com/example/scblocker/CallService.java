package com.example.scblocker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class CallService extends Service {
	public static boolean bCallStarted;
	CallListener cl = new CallListener();
	private int count = 0;
	private static final int NOTIFY_ME_ID = 1338;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		IntentFilter filter = new IntentFilter(
				"android.intent.action.PHONE_STATE");
		//CallListener cl = new CallListener();
		registerReceiver(cl, filter);
		bCallStarted = true;
		Log.d ( "CallService", "Service Started BR");
		setNotification("Call Blocker Service Started");
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(cl);
		bCallStarted = false;
		super.onDestroy();
	}
	// Notification on TitleBar////
	private void setNotification(String notificationMessage) {
		int requestID = (int) System.currentTimeMillis();
		final NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification note = new Notification(R.drawable.calll,
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
