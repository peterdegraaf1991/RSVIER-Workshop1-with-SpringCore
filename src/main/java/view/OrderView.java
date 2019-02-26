package view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import controller.OrderController;
import model_class.Order;

public class OrderView extends View {

	@Override
	public void printMenuHeader() {
		terminal.println("Order Menu\n");

	}

	public void printEditMenuHeader() {
		// TODO Auto-generated method stub

	}

	@Override
	public void printMenuOptions() {
		terminal.println("1. Add Order");
		terminal.println("2. View all Orders");
		terminal.println("3. Edit Order");
		terminal.println("9. Back");
		terminal.println("0. Logout & Exit");

	}

	public void printEditMenuOptions() {
		terminal.println("1. View Products of Order");
		terminal.println("2. Change Products of Order");
		terminal.println("3. Change Totalcost of Order");
		terminal.println("4. Delete Order");
		terminal.println("9. Back");
		terminal.println("0. Logout & Exit");

	}

	public void printCustomerNamesWithOrd(String string) {
		terminal.println(string);

	}

	public void printOrderList(List<Order> orderList) {
		clearTerminal();
		terminal.println(StringUtils.center(
				"Overview of Orders", 86));
		terminal.println("--------------------------------------------------------------------------------------");
		terminal.print(StringUtils.center("Option", 11)
				+ StringUtils.center("Order ID", 10)
				+ StringUtils.center("Customer Name", 25)
				+ StringUtils.center("Total Cost", 15)
				+ StringUtils.center("Date Ordered", 25));

		terminal.println();
		terminal.println("--------------------------------------------------------------------------------------");

		for (int i = 0; i < orderList.size(); i++) {
			OrderController orderController = new OrderController();
			String name = orderController.getCustomerNameOfOrder(orderList.get(i).getCustomer().getId());
			
			terminal.print(StringUtils.center(Integer.toString(i), 11)
					+ StringUtils.center(Integer.toString(orderList.get(i).getId()), 10)
					+ StringUtils.center(name,25)
					+ StringUtils.center(orderList.get(i).getTotalCost().toString(),15)
					+ StringUtils.center(orderList.get(i).getDate().toLocalDate().toString() + "  " + orderList.get(i).getDate().toLocalTime().toString(),25));
			terminal.println();
		}
		terminal.println("--------------------------------------------------------------------------------------");

	}

	public int chooseOrder(int size) {
		int option = textIO.newIntInputReader()
				.withMinVal(0)
				.withMaxVal(size - 1)
				.read("\nChoose the order you want to select");
		return option;
	}

	public void orderSuccesfullyDeleted() {
		terminal.println("Order Succesfully Deleted");

	}

	public BigDecimal requestTotalCost() {
		Double newTotalPrice = textIO.newDoubleInputReader().read(
				"\nEnter the new total price for this order:");
		return new BigDecimal(newTotalPrice).setScale(2, RoundingMode.HALF_UP);
	}

	public void totalCostUpdated() {
		terminal.println("Total Price for the order has succesfully been changed");

	}

	public void noOrdersFound() {
		terminal.println("No customers with an order found");

	}

	public int chooseProductFromOrderLine(int size) {
			int option = textIO.newIntInputReader()
					.withMinVal(0)
					.withMaxVal(size - 1)
					.read("\nChoose the order you want to select");
			return option;
		}
	
	public void printOrderListWithoutOption(List<Order> orderList) {
		clearTerminal();
		terminal.println(StringUtils.center(
				"Overview of Orders", 75));
		terminal.println("---------------------------------------------------------------------------");
		terminal.print(StringUtils.center("Order ID", 10)
				+ StringUtils.center("Customer Name", 25)
				+ StringUtils.center("Total Cost", 15)
				+ StringUtils.center("Date Ordered", 25));

		terminal.println();
		terminal.println("---------------------------------------------------------------------------");

		for (int i = 0; i < orderList.size(); i++) {
			OrderController orderController = new OrderController();
			String name = orderController.getCustomerNameOfOrder(orderList.get(i).getCustomer().getId());
			terminal.print(StringUtils.center(Integer.toString(orderList.get(i).getId()), 10)
					+ StringUtils.center(name,25)
					+ StringUtils.center(orderList.get(i).getTotalCost().toString(),15)
					+ StringUtils.center(orderList.get(i).getDate().toLocalDate().toString() + "  " + orderList.get(i).getDate().toLocalTime().toString(),25));
			terminal.println();
		}
		terminal.println("---------------------------------------------------------------------------");
	}
}

