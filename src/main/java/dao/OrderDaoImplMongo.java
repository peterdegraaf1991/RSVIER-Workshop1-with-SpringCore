package dao;

import java.util.List;

import model_class.Order;

public class OrderDaoImplMongo implements OrderDao {

	@Override
	public int createOrder(Order order) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Order readOrderById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> readOrdersOfCustomerId(int customer_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> readCustomerIdsWithOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateOrder(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public int deleteOrder(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Order> readAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

}
