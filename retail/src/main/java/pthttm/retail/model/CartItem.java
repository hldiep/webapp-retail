package pthttm.retail.model;
public class CartItem {
	private String productId;
	private String name;
	private Long price;
	private Integer quantity;

	public CartItem(String productId, String name, Long price, Integer quantity) {
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public CartItem() {
		this.price =Long.valueOf(0);
	}

	public double getTotalAmount() {
		return price * quantity;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CartItem{" +
				"productId='" + productId + '\'' +
				", name='" + name + '\'' +
				", price=" + price +
				", quantity=" + quantity +
				'}';
	}
}