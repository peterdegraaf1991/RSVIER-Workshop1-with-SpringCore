package dao;

import java.util.ArrayList;
import java.util.List;

import model_class.Customer;

public class CustomerDaoImplMongo implements CustomerDao {

	@Override
	public void createCustomer(Customer customer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCustomer(Customer customer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCustomer(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCustomer(String lastname) {
		// TODO Auto-generated method stub

	}

	@Override
	public Customer readCustomerById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Customer> readCustomersByLastname(String lastname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int CustomerNameExists(Customer customer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Customer> readAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

}
