package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utility.DatabaseConnection;
import model_class.OrderLine;
import model_class.Product;

public class OrderLineDaoImpl implements OrderLineDao {
	private static final Logger LOG = LoggerFactory
			.getLogger(OrderLineDaoImpl.class);

	@Override
	public int createOrderLine(OrderLine orderLine) {
		String query = "INSERT INTO order_line (id, product_id, amount, order_id) VALUES( ?, ?, ?,?)";
		int affectedRows = 0;
		int generatedId = 0;
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setInt(1, orderLine.getId());
			preparedStatement.setInt(2, orderLine.getProduct().getId());
			preparedStatement.setInt(3, orderLine.getAmount());
			preparedStatement.setInt(4, orderLine.getOrderId());
			affectedRows = preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.first();
			generatedId = rs.getInt(1);
			LOG.info("OrderLine with id: '" + generatedId
					+ "' successfully created");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return affectedRows;
	}

	@Override
	public OrderLine readOrderLineById(int id) {
		OrderLine orderLine = new OrderLine();
		String query = "SELECT * FROM order_line WHERE id = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.first();
			orderLine.setId(resultSet.getInt("id"));
			orderLine.setAmount(resultSet.getInt("amount"));
			orderLine.setOrderId(resultSet.getInt("order_id"));
			ProductDao productDaoImpl = new ProductDaoImpl();
			// uses resultSet from readOrderLine or readProduct?
			Product product = productDaoImpl.readProductById(resultSet
					.getInt("product_id"));
			orderLine.setProduct(product);
			LOG.info("ProductLine with id '" + id + "' read");
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return orderLine;
	}

	@Override
	public List<OrderLine> readOrderLinesOfOrderId(int id) {
		List<OrderLine> orderLineList = new ArrayList<>();
		String query = "SELECT * FROM order_line WHERE order_id = ?";
		int i = 0;
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query);) {

			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				LOG.info("BeforeError1 (1/3)");
				OrderLine orderLine = new OrderLine();
				orderLine.setId(resultSet.getInt("id"));
				orderLine.setAmount(resultSet.getInt("amount"));
				orderLine.setOrderId(resultSet.getInt("order_id"));
				Product product = new Product();
				product.setId(resultSet.getInt("product_id"));
				orderLine.setProduct(product);
				orderLineList.add(orderLine);
				LOG.info("BeforeError1 (2/3)");
				i = i + 1;
				LOG.info("i =" + i);
			}
			LOG.info("AfterError1 (3/3)");
			LOG.info("OrderLineList with " + i + " orderlines read");
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		return orderLineList;
	}

	public List<OrderLine> readAllOrderLines() {
		List<OrderLine> orderLineList = new ArrayList<>();
		String query = "SELECT * FROM order_line";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				LOG.info("BeforeError2 1/3");
				OrderLine orderLine = new OrderLine();
				orderLine.setId(resultSet.getInt("id"));
				orderLine.setAmount(resultSet.getInt("amount"));
				orderLine.setOrderId(resultSet.getInt("order_id"));
				Product product = new Product();
				product.setId(resultSet.getInt("product_id"));
				orderLine.setProduct(product);
				orderLineList.add(orderLine);
				LOG.info("BeforeError2 2/3");
			}
			LOG.info("AfterError2 3/3");
			LOG.info("'" + orderLineList.size() + "' OrderLines read");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderLineList;
	}

	@Override
	public void updateOrderLine(OrderLine orderLine) {
		String query = "UPDATE order_line SET product_id = ?, amount = ?, order_id = ? WHERE id = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setInt(1, orderLine.getProduct().getId());
			preparedStatement.setInt(2, orderLine.getAmount());
			preparedStatement.setInt(3, orderLine.getOrderId());
			preparedStatement.setInt(4, orderLine.getId());
			preparedStatement.executeUpdate();
			LOG.info("OrderLineId " + orderLine.getId()
					+ " successfully updated");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int deleteOrderLine(int id) {
		String query = "DELETE FROM order_line WHERE id = ?";
		int affectedRows = 0;
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setInt(1, id);
			affectedRows = preparedStatement.executeUpdate();
			LOG.info("OrderLine with id: " + id + " successfully removed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return affectedRows;
	}

}
