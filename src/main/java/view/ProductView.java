package view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import model_class.Product;

public class ProductView extends View {

	@Override
	public void printMenuHeader() {
		terminal.println("Product Menu\n");
	}

	@Override
	public void printMenuOptions() {
		terminal.println("1. Show productlist");
		terminal.println("2. Add product");
		terminal.println("3. Update product");
		terminal.println("4. Delete product");
		terminal.println("9. Back");
		terminal.println("0. Logout & Exit");
	}

	public void printProductList(List<Product> productList) {
		clearTerminal();
		terminal.println(StringUtils.center("Overview of Products", 61));
		terminal.println("-------------------------------------------------------------");
		terminal.print(StringUtils.center("Option", 11)
				+ StringUtils.center("Name", 30)
				+ StringUtils.center("Price", 10)
				+ StringUtils.center("Stock", 10));
		terminal.println();
		terminal.println("-------------------------------------------------------------");

		for (int i = 0; i < productList.size(); i++) {
			terminal.print(StringUtils.center(Integer.toString(i), 11)
					+ StringUtils.center(productList.get(i).getName(), 30)
					+ StringUtils.center(productList.get(i).getPrice().toString(), 10)
					+ StringUtils.center(Integer.toString(productList.get(i).getStock()), 10));
			terminal.println();
		}
		terminal.println("-------------------------------------------------------------");

	}

	public int requestProductNumber(int size) {
		int index = textIO.newIntInputReader().withMinVal(0)
				.withMaxVal(size - 1).read("Select Product:");
		return index;
	}

	public String requestName() {
		String productName = textIO.newStringInputReader().read(
				"Enter Productname:");
		return productName;
	}

	public BigDecimal requestPrice() {
		Double productPrice = textIO.newDoubleInputReader().read(
				"Enter ProductPrice:");
		return new BigDecimal(productPrice).setScale(2, RoundingMode.HALF_UP);
	}

	public int requestStock() {
		int stock = textIO.newIntInputReader().withMinVal(0)
				.read("Enter amount of products currently in stock");
		return stock;
	}

	public void noProductFound() {
		terminal.println("No product(s) found in the store");
	}

	public void printMessage(String error) {
		terminal.println(error);
		
	}
	public void printProductListWithoutOption(List<Product> productList) {
		clearTerminal();
		terminal.println(StringUtils.center("Overview of Products", 50));
		terminal.println("--------------------------------------------------");
		terminal.print(StringUtils.center("Name", 30)
				+ StringUtils.center("Price", 10)
				+ StringUtils.center("Stock", 10));
		terminal.println();
		terminal.println("--------------------------------------------------");

		for (int i = 0; i < productList.size(); i++) {
			terminal.print(StringUtils.center(productList.get(i).getName(), 30)
					+ StringUtils.center(productList.get(i).getPrice().toString(), 10)
					+ StringUtils.center(Integer.toString(productList.get(i).getStock()), 10));
			terminal.println();
		}
		terminal.println("--------------------------------------------------");

	}
}
