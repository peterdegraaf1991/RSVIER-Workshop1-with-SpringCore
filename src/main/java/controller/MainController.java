package controller;

import view.MainView;

public class MainController extends Controller {
	MainView mainView = new MainView();

	@Override
	public void runController() {
		int keuze = 1;
		Controller.newView = true;

		do {
			if (Controller.newView == true) {
				mainView.clearTerminal();
				mainView.printMenuHeader();
				mainView.printMenuOptions();
				Controller.newView = false;
			}

			keuze = mainView.requestMenuOption();
			switch (keuze) {
			case 1: // Account Management
				AccountController accountController = new AccountController();
				accountController.runController();
				break;

			case 2: // Orders
				OrderController orderController = new OrderController();
				orderController.runController();
				break;

			case 3: // Products
				ProductController productController = new ProductController();
				productController.runController();
				break;

			case 4: // Customer
				CustomerController customerController = new CustomerController();
				customerController.runController();
				break;

			case 9: // Logout
				keuze = 0;
				Controller.newView = true;
				mainView.logoutTimer();
				break;

			default:
				mainView.invalidInput();
				break;
			}
		} while (keuze != 0);
	}

}
