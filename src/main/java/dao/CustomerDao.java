package dao;

import java.util.ArrayList;
import java.util.List;

import model_class.Customer;

public interface CustomerDao {
	public void createCustomer(Customer customer);

	public void updateCustomer(Customer customer);

	public void deleteCustomer(int id);

	public void deleteCustomer(String lastname);

	public Customer readCustomerById(int id);

	public ArrayList<Customer> readCustomersByLastname(String lastname);

	public int CustomerNameExists(Customer customer);

	public List<Customer> readAllCustomers();

}
