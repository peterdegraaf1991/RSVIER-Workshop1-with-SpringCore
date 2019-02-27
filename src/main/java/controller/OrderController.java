package controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.CustomerDaoImpl;
import dao.OrderDaoImpl;
import dao.OrderLineDaoImpl;
import dao.ProductDaoImpl;
import model_class.Customer;
import model_class.Order;
import model_class.OrderLine;
import model_class.Product;
import view.CustomerView;
import view.OrderLineView;
import view.OrderView;
import view.ProductView;

@Component
public class OrderController extends Controller {

	
	@Autowired OrderDaoImpl orderDao;
	@Autowired OrderLineDaoImpl orderLineDao;
	@Autowired ProductDaoImpl productDao;
	@Autowired CustomerDaoImpl customerDao;
	
	@Autowired CustomerController customerController;
	@Autowired ProductController productController;
	@Autowired OrderLineController orderLineController;

	@Autowired OrderView orderView;
	@Autowired CustomerView customerView;
	@Autowired OrderLineView orderLineView;
	@Autowired ProductView productView;
	

	@Override
	public void runController() {
		int keuze = 1;
		Controller.newView = true;
		do {
			if (Controller.newView == true) {
				orderView.clearTerminal();
				orderView.printMenuHeader();
				orderView.printMenuOptions();
				Controller.newView = false;
			}

			keuze = orderView.requestMenuOption();
			switch (keuze) {
			case 1:
				createOrder();
				requestNewMenu();
				break;
			case 2: viewAllOrders();
					requestNewMenu();
					break;
			case 3:
				Order order = selectOrderFromCustomer(selectCustomersWithOrder());
				if (order == null) {
					break;
				}
				editOrderMenu(order);
				break;
			case 9:
				keuze = 0;
				Controller.newView = true;
				break;
			
			case 0:
				orderView.logoutTimer();
				System.exit(0);
				break;
			default:
				orderView.invalidInput();
				break;
			}
		} while (keuze != 0);
	}

	private void viewAllOrders() {
		if (workerOrAdminPermission() == false)
			orderView.noPermission();
		List<Order> list = orderDao.readAllOrders();
		if (list.size() <= 0) {
			orderView.noOrdersFound();
			return;
		}
		orderView.printOrderListWithoutOption(list);
		}
	
	public void editOrderMenu(Order order) {
		int keuze = 1;
		Controller.newView = true;
		do {
			if (Controller.newView == true) {
				orderView.clearTerminal();
				orderView.printEditMenuHeader();
				orderView.printEditMenuOptions();
				Controller.newView = false;
			}
			keuze = orderView.requestMenuOption();
			switch (keuze) {
			case 1:
				viewProductsOfOrder(order);
				requestNewMenu();
				break;
			case 2:
				changeProducts(order);
				keuze = 0;
				requestNewMenu();
				break;
			case 3:
				if (workerOrAdminPermission() == true) {
					changeTotalCost(order);
					keuze = 0;
					Controller.newView = true;
				} else
					orderView.noPermission();
				requestNewMenu();
				break;
			case 4:
				deleteOrder(order);
				keuze = 0;
				requestNewMenu();
				break;
			case 9:
				keuze = 0;
				Controller.newView = true;
				break;
			case 0:
				orderView.logoutTimer();
				System.exit(0);
				break;
			default:
				orderView.invalidInput();
				break;
			}
		} while (keuze != 0);
	}

	private void viewProductsOfOrder(Order order) {
		List<OrderLine> listOrderLines = orderLineDao.readOrderLinesOfOrderId(order.getId());
			orderLineView.printOrderLineListWithoutOption(listOrderLines);
	}

	private void deleteOrder(Order order) {
		if (order != null) {
			orderDao.deleteOrder(order.getId());
			// add amount back to stock has to be added here
			orderView.orderSuccesfullyDeleted();
		}
	}

	public void createOrder() {
		Customer customer = customerController.selectCustomer();
		if (customer.getId() == 0)
			return;
		int orderId = createNewOrderId(customer);
		List<OrderLine> orderLineList = orderLineController
				.createOrderLines(orderId);
		BigDecimal totalCost = calculateTotalCost(orderLineList);
		productController.updateStock(orderLineList);
		Order order = orderDao.readOrderById(orderId);
		order.setTotalCost(totalCost);
		order.setDate(LocalDateTime.now());
		orderDao.updateOrder(order);
	}

	public int createNewOrderId(Customer customer) {
		Order order = new Order();
		order.setCustomer(customer);
		order.setDate(LocalDateTime.now());
		order.setTotalCost(new BigDecimal(0));
		int generatedId = orderDao.createOrder(order);
		return generatedId;
	}

	public BigDecimal calculateTotalCost(List<OrderLine> list) {
		BigDecimal totalCost = new BigDecimal(0);
		for (int i = 0; i < list.size(); i++) {
			int productId = list.get(i).getProduct().getId();
			Product product = productDao.readProductById(productId);
			totalCost = totalCost.add(product.getPrice().multiply(
					new BigDecimal(list.get(i).getAmount())));
		}
		return totalCost;
	}

	private Customer selectCustomersWithOrder() {
		List<Integer> customerIdList = orderDao.readCustomerIdsWithOrder();
		if (workerOrAdminPermission() == false) {
			if (customerIdList.contains(LoginController.loggedInCustomer
					.getId()) == true)
				return LoginController.loggedInCustomer;
			else
				return null;
		}
		if (customerIdList.isEmpty()) {
			orderView.noOrdersFound();
			return null;
		}
		List<Customer> customerList = new ArrayList<>();
		for (int i = 0; i < customerIdList.size(); i++) {
			Customer customer = customerDao.readCustomerById(customerIdList
					.get(i));
			customerList.add(customer);
		}

		customerView.printPersonList(customerList);
//		for (int i = 0; i < customerList.size(); i++) {
//			orderView.printCustomerNamesWithOrd(i + ". "
//					+ customerList.get(i).toString());
		
		int option = customerView.choosePerson(customerIdList.size());
		return customerList.get(option);
	}

	private Order selectOrderFromCustomer(Customer customer) {
		if (customer == null) {
			return null;
		}
		List<Order> orderList = orderDao.readOrdersOfCustomerId(customer
				.getId());
			orderView.printOrderList(orderList);
		int option = orderView.chooseOrder(orderList.size());
		return orderList.get(option);
	}

	private void changeTotalCost(Order order) {
		BigDecimal newTotalCost = orderView.requestTotalCost();
		order.setTotalCost(newTotalCost);
		orderDao.updateOrder(order);
		orderView.totalCostUpdated();
	}

	private void updateTotalCostOfOrder(Order order) {
		List<OrderLine> listOrderLines = orderLineDao.readOrderLinesOfOrderId(order.getId());
		BigDecimal newTotalCost = calculateTotalCost(listOrderLines);
		order.setTotalCost(newTotalCost);
		orderDao.updateOrder(order);
	}

	private void changeProducts(Order order) {
		OrderLine orderLine = selectOrderLineFromOrder(order);
		int oldProductId = orderLine.getProduct().getId();
		int oldAmount = orderLine.getAmount();
		Product oldProduct = productDao.readProductById(oldProductId);
		Product newProduct = productController.selectProductFromList();
		int newProductCurrentStock = newProduct.getStock();

		// Update Stocks:
		if (oldProduct.equals(newProduct) == false) {
			int newProductAmount = orderLineView.requestAmount(newProduct
					.getStock());
			orderLine.setAmount(newProductAmount);
			int oldProductStock = oldProduct.getStock();
			oldProduct.setStock(oldProductStock + oldAmount);
			productDao.updateProduct(oldProduct);
			newProduct.setStock(newProductCurrentStock - newProductAmount);
			productDao.updateProduct(newProduct);
		}
		if (oldProduct.equals(newProduct) == true) {
			int newProductAmount = orderLineView.requestAmount(oldProduct
					.getStock() + oldAmount);
			orderLine.setAmount(newProductAmount);
			newProduct.setStock(newProductCurrentStock + (oldAmount - newProductAmount));
			productDao.updateProduct(newProduct);
		}
		orderLine.setProduct(newProduct);
		orderLineDao.updateOrderLine(orderLine);
		updateTotalCostOfOrder(order);
	}

	// OrderLine Controller
	private OrderLine selectOrderLineFromOrder(Order order) {
		List<OrderLine> listOrderLines = orderLineDao.readOrderLinesOfOrderId(order.getId());
		orderLineView.printOrderLineList(listOrderLines);
					
		int option = orderView
				.chooseProductFromOrderLine(listOrderLines.size());
		return listOrderLines.get(option);
	}

	public String getCustomerNameOfOrder(int customerId) {
		Customer customer = customerDao.readCustomerById(customerId);
		return customer.toString();
	}
}
