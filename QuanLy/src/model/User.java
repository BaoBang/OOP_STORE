package model;

import java.sql.Timestamp;
import java.util.Date;

public class User {
	private int id;
	private String email;
	private String name;
	private String password;
	private String phone;
	private String address;
	private int level;
	private Timestamp createdAt;
	Date date= new java.util.Date();
	
	public User(int id,String email, String password , String name, String phone, String address, int level) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.level = level;
		this.createdAt = new Timestamp(date.getTime());
	}
	public User(String email, String password , String name, String phone, String address, int level) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.level = level;
		this.createdAt = new Timestamp(date.getTime());
	}
	public User() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	
}
