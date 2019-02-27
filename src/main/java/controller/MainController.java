package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import view.MainView;

@Component
public class MainController extends Controller {
	@Autowired
	MainView mainView;
	
	@Autowired
	AccountController accountController;
	
	@Autowired
	OrderController orderController;
	
	@Autowired
	ProductController productController;
	
	@Autowired
	CustomerController customerController;
	
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
				accountController.runController();
				break;

			case 2: // Orders
				orderController.runController();
				break;

			case 3: // Products
				productController.runController();
				break;

			case 4: // Customer
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
