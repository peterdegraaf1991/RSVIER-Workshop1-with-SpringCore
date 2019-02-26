package view;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import controller.OrderLineController;
import model_class.OrderLine;

public class OrderLineView extends View {

	public void productAlreadyAdded() {
		terminal.println("This Product already has been added to this order");
	}

	public Boolean addMoreProducts() {
		char option = textIO.newCharInputReader()
				.withInlinePossibleValues('y', 'n')
				.read("Do you wish to add another product to the order?");
		if (option == 'y') {
			System.out.println("Chosen option = y (true))");
			return true;
		} else
			System.out.println("Chosen option = n (false)");
		return false;
	}

	public int requestAmount(int inStock) {
		int minAmount;
		if (inStock == 0)
			minAmount = 0;
		else
			minAmount = 1;
		int amount = textIO
				.newIntInputReader()
				.withMinVal(minAmount)
				.withMaxVal(inStock)
				.read("\nCurrently in Stock: '" + inStock + "'\n"
						+ "How many do you wish to order? \n");
		return amount;
	}

	@Override
	public void printMenuHeader() {
	}

	@Override
	public void printMenuOptions() {
	}

	public void printOrderLineList(List<OrderLine> orderLineList) {
		clearTerminal();
		String header = "Overview of products from order with ID: "
				+ orderLineList.get(0).getOrderId();
		terminal.println(StringUtils.center(header, 51));
		terminal.println("---------------------------------------------------");
		terminal.print(StringUtils.center("Option", 11)
				+ StringUtils.center("Name", 30)
				+ StringUtils.center("Amount", 11));
		terminal.println();
		terminal.println("---------------------------------------------------");
		OrderLineController orderLineController = new OrderLineController();
		for (int i = 0; i < orderLineList.size(); i++) {

			terminal.print(StringUtils.center(Integer.toString(i), 11)
					+ StringUtils.center(orderLineController
							.getProductNameOfOrderLine(orderLineList.get(i)),
							30)
					+ StringUtils.center(
							Integer.toString(orderLineList.get(i).getAmount()),
							11));
			terminal.println();
		}
		terminal.println("-----------------------------------------");
	}
	public void printOrderLineListWithoutOption(List<OrderLine> orderLineList) {
		clearTerminal();
		String header = "Overview of products from order with ID: "
				+ orderLineList.get(0).getOrderId();
		terminal.println(StringUtils.center(header, 41));
		terminal.println("-----------------------------------------");
		terminal.print(StringUtils.center("Name", 30)
				+ StringUtils.center("Amount", 11));
		terminal.println();
		terminal.println("-----------------------------------------");
		OrderLineController orderLineController = new OrderLineController();
		for (int i = 0; i < orderLineList.size(); i++) {

			terminal.print(StringUtils.center(orderLineController
							.getProductNameOfOrderLine(orderLineList.get(i)),
							30)
					+ StringUtils.center(
							Integer.toString(orderLineList.get(i).getAmount()),
							11));
			terminal.println();
		}
		terminal.println("---------------------------------------------------");
	}
}
