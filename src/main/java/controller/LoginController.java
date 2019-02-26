package controller;

import model_class.Account;
import model_class.Customer;
import dao.DaoFactory;
import view.LoginView;
import utility.Hashing;
import utility.Hashing.CannotPerformOperationException;
import utility.Hashing.InvalidHashException;

public class LoginController extends Controller {

	public static Account loggedInAccount = new Account();
	static Customer loggedInCustomer = new Customer();
	private LoginView loginView = new LoginView();


	public void runController() {
	loginView.useSQLOrMongo();
		
		int keuze = 1;
		Controller.newView = true;
		do {
			if (Controller.newView) {
				loginView.clearTerminal();
				loginView.printMenuHeader();
				loginView.printMenuOptions();
				Controller.newView = false;
			}
			keuze = loginView.requestMenuOption();
			switch (keuze) {
			case 1:
				checkAccountByEmail();
				;
				break;
			case 9:
				System.exit(0);
				break;
			default:
				loginView.invalidInput();
				break;
			}
		} while (keuze != 0);
	}

	public void checkAccountByEmail() {
		loggedInAccount = DaoFactory.getAccountDao().readAccountByEmail(loginView
				.requestInputUsername());
		int accountId = loggedInAccount.getId();
		if (loggedInAccount.getId() != 0) {
			String hash = DaoFactory.getAccountDao().readHash(accountId);
			try {
				if (Hashing.verifyPassword(loginView.requestInputPassword(), hash) == true) {
					loginView.loginSuccesfull();
					loggedInCustomer = loggedInAccount.getCustomer();
					MainController mainController = new MainController();
					mainController.runController();
				} else {
					loginView.incorrectEmailOrPassword();
					requestNewMenu();
				}
			} catch (CannotPerformOperationException | InvalidHashException e) {
				e.printStackTrace();
			}
		}
		else {
			loginView.incorrectEmailOrPassword();
			requestNewMenu();
		}
					
	}
}
