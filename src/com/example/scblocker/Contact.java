package com.example.scblocker;

public class Contact {
	private int bl_id;
	private String bl_number;
	private String bl_contact;
	private int bl_option; //Call/SMS/BOTH
	
	public Contact() {
		super();
		this.bl_id = 0;
		this.bl_contact = "";
		this.bl_number = "";
		this.bl_option = 0; //0 not set..1 Call..2 SMS
	}

	public Contact(int bl_id, String bl_number, String bl_contact,
			int bl_option) {
		super();
		this.bl_id = bl_id;
		this.bl_number = bl_number;
		this.bl_contact = bl_contact;
		this.bl_option = bl_option;
	}

	public Contact(int bl_id, String bl_number, String bl_contact) {
		super();
		this.bl_id = bl_id;
		this.bl_number = bl_number;
		this.bl_contact = bl_contact;
	}

	public int getBl_id() {
		return bl_id;
	}

	public void setBl_id(int bl_id) {
		this.bl_id = bl_id;
	}

	public String getBl_number() {
		return bl_number;
	}

	public void setBl_number(String bl_number) {
		this.bl_number = bl_number;
	}

	public String getBl_contact() {
		return bl_contact;
	}

	public void setBl_contact(String bl_contact) {
		this.bl_contact = bl_contact;
	}

	public int getBl_option() {
		return bl_option;
	}

	public void setBl_option(int bl_option) {
		this.bl_option = bl_option;
	}
}
