package model_class;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class Order {
	private int id;
	private BigDecimal totalCost;
	private LocalDateTime date;
	private Customer customer;

	// private List<OrderLine> orderLineList;

	public Order(BigDecimal totalCost, LocalDateTime date, Customer customer) {
		this.totalCost = totalCost;
		this.date = date;
		this.customer = customer;
		// this.orderLineList = orderLineList;
	}

	public Order() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "[id = " + id + ", totalCost=" + totalCost + ", date="
				+ date + "]";
	}

	// public List<OrderLine> getOrderLines() {
	// return orderLineList;
	// }

	// public void setOrderLines(List<OrderLine> orderLines) {
	// this.orderLineList = orderLines;
	// }

}
