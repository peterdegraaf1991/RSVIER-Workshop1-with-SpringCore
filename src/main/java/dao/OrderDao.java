package dao;


import java.util.List;
import model_class.Order;


public interface OrderDao {

	public int createOrder(Order order);

	public Order readOrderById(int id);

	public List<Order> readOrdersOfCustomerId(int customer_id);

	public List<Integer> readCustomerIdsWithOrder();

	public void updateOrder(Order order);

	public int deleteOrder(int id);

	public List<Order> readAllOrders();

}
