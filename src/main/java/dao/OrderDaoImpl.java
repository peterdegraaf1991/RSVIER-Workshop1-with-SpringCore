package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utility.DatabaseConnection;
import model_class.Customer;
import model_class.Order;

@Component
public class OrderDaoImpl implements OrderDao {

	private static final Logger LOG = LoggerFactory
			.getLogger(OrderDaoImpl.class);

	@Autowired
	CustomerDao customerDaoImpl;
	
	@Override
	public int createOrder(Order order) {
		String query = "INSERT INTO `order` (total_cost, date, customer_id) VALUES(?,?,?)";
		int generatedKey = 0;
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setBigDecimal(1, order.getTotalCost());
			preparedStatement.setObject(2, order.getDate());
			preparedStatement.setInt(3, order.getCustomer().getId());
			preparedStatement.execute();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next())
				;
			generatedKey = rs.getInt(1);
			LOG.info("order: " + generatedKey + " successfully created");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return generatedKey;
	}

	@Override
	public void updateOrder(Order order) {
		String query = "UPDATE `order` SET total_cost = ? , date = ?, customer_id = ?  WHERE id = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setBigDecimal(1, order.getTotalCost());
			preparedStatement.setObject(2, order.getDate());
			preparedStatement.setInt(3, order.getCustomer().getId());
			preparedStatement.setInt(4, order.getId());
			preparedStatement.executeUpdate();
			LOG.info("order with id '" + order.getId() + "' has been updated");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int deleteOrder(int id) {
		String query = "DELETE FROM `order` WHERE id = ?";
		int affectedRows = 0;
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setInt(1, id);
			affectedRows = preparedStatement.executeUpdate();
			LOG.info("Order with id: " + id + " successfully removed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return affectedRows;
	}

	@Override
	public Order readOrderById(int id) {
		Order order = new Order();
		String query = "SELECT * FROM `order` WHERE id = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.first();
			order.setId(resultSet.getInt("id"));
			order.setTotalCost(resultSet.getBigDecimal("total_cost"));
			order.setDate(resultSet.getTimestamp("date").toLocalDateTime());

			Customer customer = customerDaoImpl.readCustomerById(resultSet
					.getInt("customer_id"));
			order.setCustomer(customer);
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public List<Order> readOrdersOfCustomerId(int customer_id) {
		List<Order> orderList = new ArrayList<>();
		String query = "SELECT * FROM `order` WHERE Customer_id = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setInt(1, customer_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Order order = new Order();
				order.setId(resultSet.getInt("id"));
				order.setTotalCost(resultSet.getBigDecimal("total_cost"));
				order.setDate(resultSet.getTimestamp("date").toLocalDateTime());
				Customer customer = new Customer();
				customer.setId(customer_id);
				order.setCustomer(customer);
				orderList.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	@Override
	public List<Integer> readCustomerIdsWithOrder() {
		List<Integer> list = new ArrayList<>();
		String query = "SELECT DISTINCT customer_id FROM `order`";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				list.add(resultSet.getInt("customer_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Order> readAllOrders() {
		List<Order> orderList = new ArrayList<>();
		String query = "SELECT * FROM `order`";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Order order = new Order();
				order.setId(resultSet.getInt("id"));
				order.setTotalCost(resultSet.getBigDecimal("total_cost"));
				Customer customer = new Customer();
				customer.setId(resultSet.getInt("customer_id"));
				order.setCustomer(customer);
				order.setDate(resultSet.getTimestamp("date").toLocalDateTime());
				orderList.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}
}
