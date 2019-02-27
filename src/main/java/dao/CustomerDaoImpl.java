package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import utility.DatabaseConnection;
import model_class.Customer;

@Component
public class CustomerDaoImpl implements CustomerDao {
	private static final Logger LOG = LoggerFactory
			.getLogger(CustomerDaoImpl.class);

	@Override
	public void createCustomer(Customer customer) {
		String query = "INSERT INTO customer (id, firstname, middlename, surname) VALUES( ?, ?, ?, ?)";
		int generatedId = 0;
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setInt(1, customer.getId());
			preparedStatement.setString(2, customer.getFirstname());
			preparedStatement.setString(3, customer.getMiddlename());
			preparedStatement.setString(4, customer.getSurname());
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				generatedId = rs.getInt(1);
				LOG.info("Customer with id '" + generatedId
						+ "' successfully created");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCustomer(Customer customer) {
		String query = "UPDATE customer SET firstname = ?, middlename = ?, surname = ? WHERE id = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, customer.getFirstname());
			preparedStatement.setString(2, customer.getMiddlename());
			preparedStatement.setString(3, customer.getSurname());
			preparedStatement.setInt(4, customer.getId());
			preparedStatement.executeUpdate();
			LOG.info("customerid: " + customer.getId() + " has been updated");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCustomer(int id) {
		String query = "DELETE FROM customer WHERE id = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			LOG.info("Customer with id: " + id + " successfully removed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Fiddling around with SQL JOIN Queries
	@Override
	public Customer readCustomerById(int id) {
		Customer customer = new Customer();
		String query = "SELECT * FROM customer LEFT JOIN account ON customer.id = account.customer_id LEFT JOIN account_type ON account.account_type_id = account_type.id WHERE customer.id = ? ";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.first();
			customer.setId(resultSet.getInt("id"));
			customer.setFirstname(resultSet.getString("firstname"));
			customer.setMiddlename(resultSet.getString("middlename"));
			customer.setSurname(resultSet.getString("surname"));
			customer.setAccountDescription(resultSet.getString("description"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	//Fiddling around with SQL JOIN Queries
	@Override
	public ArrayList<Customer> readCustomersByLastname(String lastname) {
		ArrayList<Customer> listOfCustomers = new ArrayList<>();
		String query = "SELECT * FROM customer LEFT JOIN account ON customer.id = account.customer_id LEFT JOIN account_type ON account.account_type_id = account_type.id WHERE customer.surname = ? ";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setString(1, lastname);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Customer customer = new Customer();
				customer.setId(resultSet.getInt("id"));
				customer.setFirstname(resultSet.getString("firstname"));
				customer.setMiddlename(resultSet.getString("middlename"));
				customer.setSurname(resultSet.getString("surname"));
				customer.setAccountDescription(resultSet.getString("description"));
				listOfCustomers.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfCustomers;
	}

	@Override
	public void deleteCustomer(String lastname) {
		String query = "DELETE FROM customer WHERE surname = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, lastname);
			preparedStatement.executeUpdate();
			LOG.info("Person successfully removed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int CustomerNameExists(Customer customer) {
		String query = "SELECT id FROM customer WHERE firstname=? AND middlename = ? AND surname = ?";
		int rowCount = 0;
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setString(1, customer.getFirstname());
			preparedStatement.setString(2, customer.getMiddlename());
			preparedStatement.setString(3, customer.getSurname());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
				rowCount++;
			System.out.println("Rows Found = " + rowCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	//Fiddling around with SQL JOIN Queries
	@Override
	public List<Customer> readAllCustomers() {
		List<Customer> customerList = new ArrayList<>();
		String query = "SELECT * FROM customer LEFT JOIN account ON customer.id = account.customer_id LEFT JOIN account_type ON account.account_type_id = account_type.id";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Customer customer = new Customer();
				customer.setId(resultSet.getInt("id"));
				customer.setFirstname(resultSet.getString("firstname"));
				customer.setMiddlename(resultSet.getString("middlename"));
				customer.setSurname(resultSet.getString("surname"));
				customer.setAccountDescription(resultSet.getString("description"));
				customerList.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerList;
	
	}
}
