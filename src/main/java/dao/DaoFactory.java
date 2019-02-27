package dao;

public class DaoFactory {

	private static AccountDao accountDao = null;
	private static AccountTypeDao accountTypeDao = null;
	private static CustomerDao customerDao = null;
	private static OrderDao orderDao = null;
	private static OrderLineDao orderLineDao = null;
	private static ProductDao productDao = null;
	
	
	public DaoFactory(int databaseOption) {
		switch (databaseOption) {

		case 1:
			System.out.println("entering DAOFactory case 1");
			accountDao = new AccountDaoImpl();
			accountTypeDao = new AccountTypeDaoImpl();
			customerDao = new CustomerDaoImpl();
			orderDao = new OrderDaoImpl();
			orderLineDao = new OrderLineDaoImpl();
			productDao = new ProductDaoImpl();
			break;
			
		default:
			//temp.
			System.out.println("Invalid input");

			break;
		}
	}

	public static AccountDao getAccountDao() {
		return accountDao;
	}


	public static AccountTypeDao getAccountTypeDao() {
		return accountTypeDao;
	}


	public static CustomerDao getCustomerDao() {
		return customerDao;
	}


	public static OrderDao getOrderDao() {
		return orderDao;
	}


	public static OrderLineDao getOrderLineDao() {
		return orderLineDao;
	}


	public static ProductDao getProductDao() {
		return productDao;
	}
	
}
