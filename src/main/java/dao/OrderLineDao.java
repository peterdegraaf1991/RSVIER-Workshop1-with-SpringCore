package dao;

import java.util.List;

import model_class.OrderLine;

public interface OrderLineDao {

	public int createOrderLine(OrderLine orderLine);

	// only by id? maybe by product etc.?
	public OrderLine readOrderLineById(int id);

	public List<OrderLine> readOrderLinesOfOrderId(int id);

	public List<OrderLine> readAllOrderLines();

	public void updateOrderLine(OrderLine orderLine);

	public int deleteOrderLine(int id);
}
