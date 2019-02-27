package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model_class.Account;
import model_class.Customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utility.DatabaseConnection;

@Component
public class AccountDaoImpl implements AccountDao {
	private static final Logger LOG = LoggerFactory
			.getLogger(AccountDaoImpl.class);
	
	@Autowired
	CustomerDao customerDaoImpl;

	@Override
	public int createAccount(Account account) {
		int affectedRows = 0;
		String query = "INSERT INTO account (email, account_type_id, customer_id, hash) VALUES(?, ?, ?, ?)";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, account.getEmail());
//			preparedStatement.setString(2, account.getPassword());
			preparedStatement.setInt(2, account.getAccountTypeId());
			preparedStatement.setInt(3, account.getCustomer().getId());
			preparedStatement.setString(4, account.getHash());
			LOG.warn("Creating account with new hash:" + account.getHash());
			LOG.info("Trying to create account with info:" + account.toString()
					+ "\n CustomerID = '" + account.getCustomer().getId() + "'");
			affectedRows = preparedStatement.executeUpdate();

			// ResultSet rs = preparedStatement.executeUpdate();
			// if (rs != null && rs.next()) {
			// affectedRows = rs.getInt(1);
			// }
			LOG.info("Account for: " + account.getEmail()
					+ " successfully created \n AffectedRows = " + affectedRows);
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return affectedRows;
	}

	@Override
	public void updateAccount(Account account) {
		String query = "UPDATE account SET email = ? , hash = ? , account_type_id = ? WHERE id = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, account.getEmail());
			// getter for password allowed?
			preparedStatement.setString(2, account.getHash());
			preparedStatement.setInt(3, account.getAccountTypeId());
			preparedStatement.setInt(4, account.getId());
			preparedStatement.executeUpdate();
			LOG.info("AccountID: " + account.getId() + " has been updated");
			LOG.info("Rows Affected: " + preparedStatement.getUpdateCount());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int deleteAccount(int id) {
		String query = "DELETE FROM account WHERE id = ?";
		int affectedRows = 0;
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setInt(1, id);
			affectedRows = preparedStatement.executeUpdate();
			LOG.info("Account with id: " + id + " successfully removed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return affectedRows;
	}

	@Override
	public Account readAccountById(int id) {
		Account account = new Account();
		String query = "SELECT * FROM account WHERE id = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.first();
			// ?password remains null?
			account.setId(resultSet.getInt("id"));
			account.setHash(resultSet.getString("hash"));
			account.setEmail(resultSet.getString("email"));
			account.setAccountTypeId(resultSet.getInt("account_type_id"));
			Customer customer = customerDaoImpl.readCustomerById(resultSet
					.getInt("customer_id"));
			account.setCustomer(customer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

	@Override
	public Account readAccountByEmail(String email) {
		Account account = new Account();
		String query = "SELECT * FROM account WHERE email = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				account.setId(resultSet.getInt("id"));
				account.setHash(resultSet.getString("hash"));
				account.setEmail(resultSet.getString("email"));
				account.setAccountTypeId(resultSet.getInt("account_type_id"));
				Customer customer = customerDaoImpl.readCustomerById(resultSet
						.getInt("customer_id"));
				account.setCustomer(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

	@Override
	public Account readAccountByCustomerId(int id) {
		Account account = new Account();
		String query = "SELECT * FROM account WHERE customer_id = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				account.setId(resultSet.getInt("id"));
				account.setHash(resultSet.getString("hash"));
				account.setEmail(resultSet.getString("email"));
				account.setAccountTypeId(resultSet.getInt("account_type_id"));
				Customer customer = customerDaoImpl.readCustomerById(resultSet
						.getInt("customer_id"));
				account.setCustomer(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

	@Override
	public String readHash(int id) {
		String query = "SELECT hash FROM account WHERE id = ?";
		String hash = null;
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.first();
			hash = resultSet.getString("hash");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hash;
	}

	@Override
	public List<Account> readAllAccounts() {
		List<Account> accountList = new ArrayList<>();
		String query = "SELECT * FROM account";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Account account = new Account();
				account.setId(resultSet.getInt("id"));
				account.setEmail(resultSet.getString("email"));
				account.setAccountTypeId(resultSet.getInt("account_type_id"));
				accountList.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountList;
	}
}

// public void printAccount(){
// try {
// Statement statement = Connect.getConnection().createStatement();
// ResultSet resultSet =
// statement.executeQuery("SELECT id, account_type_id, email FROM account");
// ResultSetMetaData metaData = resultSet.getMetaData();
//
// for (int i = 1; i <= metaData.getColumnCount(); i++){
// System.out.printf("%-12s\t", metaData.getColumnName(i));
// }
// System.out.println();
//
// while (resultSet.next()) {
// for (int i = 1; i <= metaData.getColumnCount(); i++)
// System.out.printf("%-12s\t", resultSet.getObject(i));
// System.out.println();
// }
// statement.close();
// }
// catch (SQLException e) {
// e.printStackTrace();
// }
// }
// public void printAccount(String email) {
// String query =
// "SELECT id, account_type_id, email FROM account WHERE email = ?";
// try {
// PreparedStatement preparedStatement =
// Connect.getConnection().prepareStatement(query);
// preparedStatement.setString(1, email);
// ResultSet resultSet = preparedStatement.executeQuery();
//
// ResultSetMetaData metaData = resultSet.getMetaData();
// for (int i = 1; i <= metaData.getColumnCount(); i++){
// System.out.printf("%-20s\t", metaData.getColumnName(i));
// }
// System.out.println();
//
// while (resultSet.next()) {
// for (int i = 1; i <= metaData.getColumnCount(); i++)
// System.out.printf("%-20s\t", resultSet.getObject(i));
// System.out.println();
// }
// preparedStatement.close();
// }
// catch (SQLException e) {
// e.printStackTrace();
// }
// }

// }

