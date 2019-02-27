package model_class;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class Product {

	private String name;
	private int id;
	private int stock;
	private BigDecimal price;

	public Product(int id, String name, BigDecimal price, int stock) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public Product() {
	}

	public Product(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + stock;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (stock != other.stock)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[name=" + name + ", stock=" + stock
				+ ", price=" + price + "]";
	}

}
