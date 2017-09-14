package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Order {
	private int id;
	private User user = new  User();
	private ArrayList<OrderDetail> listOrderDetail = new ArrayList<OrderDetail>();
	private long total;
	private String address;
	private Timestamp createdAt;
	Date date= new java.util.Date();
	private int status;
	
	
	
	public Order(int id, User user, long total, String address, int status) {
		super();
		this.id = id;
		this.user = user;
		this.total = total;
		this.address = address;
		this.status = status;
		this.createdAt = new Timestamp(date.getTime());
	}
	
	public Order(User user, long total, String address, int status) {
		super();
		this.user = user;
		this.total = total;
		this.address = address;
		this.status = status;
		this.createdAt = new Timestamp(date.getTime());
	}
	
	public Order() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
