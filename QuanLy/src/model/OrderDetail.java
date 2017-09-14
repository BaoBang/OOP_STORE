package model;

public class OrderDetail {
	private int id;
	private Product product = new Product();
	private Order order = new Order();
	private int qty;
	private int price;
	private int discount;
	
	
	public OrderDetail(int id, Product product, Order order, int qty, int price, int discount) {
		super();
		this.id = id;
		this.product = product;
		this.order = order;
		this.qty = qty;
		this.price = price;
		this.discount = discount;
	}
	
	public OrderDetail() {
		super();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
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
	
	
	
	
}
