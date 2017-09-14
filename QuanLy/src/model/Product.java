package model;


import java.sql.Timestamp;
import java.util.Date;


public class Product {

	private int id;
	private String name;
	private String model;
	private Brand brand = new Brand();
	private Category category = new Category();
	private int stock;
	private int price;
	private int discount;
	private String description = "";
	private String  image;
	private Timestamp createdAt;
	Date date= new java.util.Date();

	public Product(String name, String model, Brand brand, Category category, int stock, int price, int discount,String image, String description) {
		this.name = name;
		this.model = model;
		this.stock = stock;
		this.price = price;		
		this.discount = discount;
		this.image = image;
		this.description = description;
		this.brand = brand;
		this.category = category;
		this.createdAt = new Timestamp(date.getTime());
		
	}

	public Product() {
		
	}
	public Product(int id2, String name2, int stock2, int price2, String image2, int discount2) {
		this.id = id2;
		this.name = name2;
		this.stock = stock2;
		this.price = price2;		
		this.discount = discount2;
		this.image = image2;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	
	
	
}
