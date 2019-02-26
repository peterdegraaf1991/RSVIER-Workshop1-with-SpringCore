package dao;

import model_class.Address;

public interface AddressDao {

	public void createAddress(Address address);

	public Address readAddress(int id);

	public Address readAddressOfCustomer(int customer_id);

	public void updateAddress(Address address);

	public void deleteAddress(int id);
}
