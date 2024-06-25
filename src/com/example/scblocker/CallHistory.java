package com.example.scblocker;

public class CallHistory {
	private int call_id;
	private String call_number;
	private String call_contact;
	private String Call_date;
	private String call_time;

	public CallHistory() {
		super();
		this.setCall_id(0);
		this.setCall_number("");
		this.setCall_contact("");
		this.setCall_date("");
		this.setCall_time("");
	}

	public CallHistory(int call_id, String call_number, String call_contact,
			String call_date, String call_time) {
		super();
		this.setCall_id(call_id);
		this.setCall_number(call_number);
		this.setCall_contact(call_contact);
		this.setCall_date(call_date);
		this.setCall_time(call_time);

	}

	public CallHistory(String call_number, String call_contact,
			String call_date, String call_time) {
		super();
		this.setCall_number(call_number);
		this.setCall_contact(call_contact);
		this.setCall_date(call_date);
		this.setCall_time(call_time);

	}

	public int getCall_id() {
		return this.call_id;
	}

	public void setCall_id(int call_id) {
		this.call_id = call_id;
	}

	public String getCall_number() {
		return this.call_number;
	}

	public void setCall_number(String call_number) {
		this.call_number = call_number;
	}

	public String getCall_contact() {
		return this.call_contact;
	}

	public void setCall_contact(String call_contact) {
		this.call_contact = call_contact;
	}

	public String getCall_date() {
		return this.Call_date;
	}

	public void setCall_date(String call_date) {
		Call_date = call_date;
	}

	public String getCall_time() {
		return this.call_time;
	}

	public void setCall_time(String call_time) {
		this.call_time = call_time;
	}

}
