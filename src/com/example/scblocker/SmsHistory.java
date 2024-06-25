package com.example.scblocker;

public class SmsHistory {
	private int msg_id;
	private String msg_number;
	private String msg_contact;
	private String msg_body;
	private String sms_date;
	private String sms_time;
	
	public SmsHistory() {
		super();
		this.msg_id = 0;
		this.msg_contact = "";
		this.msg_number = "";
		this.msg_body="";
		this.sms_date="";
		this.sms_time="";
	}

	public SmsHistory(int msg_id, String msg_number, String msg_contact,
			String msg_body, String sms_date, String sms_time) {
		super();
		this.msg_id = msg_id;
		this.msg_number = msg_number;
		this.msg_contact = msg_contact;
		this.msg_body=msg_body;
		this.sms_date=sms_date;
		this.sms_time=sms_time;
		
	}

	public SmsHistory(String msg_number, String msg_contact, String msg_body, String sms_date, String sms_time) {
		super();
		this.msg_number = msg_number;
		this.msg_contact = msg_contact;
		this.msg_body=msg_body;
		this.sms_date=sms_date;
		this.sms_time=sms_time;
		
	}

	public int getmsg_id() {
		return this.msg_id;
	}

	public void setmsg_id(int msg_id) {
		this.msg_id = msg_id;
	}

	public String getmsg_number() {
		return this.msg_number;
	}

	public void setmsg_number(String msg_number) {
		this.msg_number = msg_number;
	}

	public String getmsg_contact() {
		return this.msg_contact;
	}

	public void setmsg_contact(String msg_contact) {
		this.msg_contact = msg_contact;
	
	}

	public String getmsg_body() {
		return this.msg_body;
	}

	public void setmsg_body(String msg_body) {
		this.msg_body = msg_body;
	}

	public String getsms_date() {
		return this.sms_date;
	}

	public void setsms_date(String sms_date) {
		this.sms_date = sms_date;
	}

	public String getsms_time() {
		return this.sms_time;
	}

	public void setsms_time(String sms_time) {
		this.sms_time = sms_time;
	}

}
