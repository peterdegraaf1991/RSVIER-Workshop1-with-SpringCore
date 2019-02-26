package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utility.DatabaseConnection;
import model_class.AddressType;

public class AddressTypeDaoImpl implements AddressTypeDao {
	private static final Logger LOG = LoggerFactory
			.getLogger(AddressTypeDaoImpl.class);

	@Override
	public void createAddressType(AddressType addressType) {
		String query = "INSERT INTO address_type (id, description) VALUES( ?, ?)";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setInt(1, addressType.getId());
			preparedStatement.setString(2, addressType.getDescription());
			preparedStatement.executeUpdate();
			LOG.info("AddressTypeId: " + addressType.getId()
					+ " successfully created");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAddressType(AddressType addressType) {
		String query = "UPDATE address_type SET description = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, addressType.getDescription());
			preparedStatement.executeUpdate();
			LOG.info("AddressTypeId: " + addressType.getId()
					+ " successfully updated");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAddressType(int id) {
		String query = "DELETE FROM address_type WHERE id = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query,
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			LOG.info("AddressType with id: " + id + " successfully removed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public AddressType readAddressType(int id) {
		AddressType addressType = new AddressType();
		String query = "SELECT * FROM address_type WHERE id = ?";
		try (Connection connection = DatabaseConnection.INSTANCE
				.getConnectionSQL();
				PreparedStatement preparedStatement = connection
						.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.first();
			addressType.setId(resultSet.getInt("id"));
			addressType.setDescription(resultSet.getString("description"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressType;
	}
}
