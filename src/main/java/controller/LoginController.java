package controller;

import model_class.Account;
import model_class.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.DaoFactory;
import view.LoginView;
import utility.Hashing;
import utility.Hashing.CannotPerformOperationException;
import utility.Hashing.InvalidHashException;

@Component
public class LoginController extends Controller {
// Geen autowire op static methodes?
	public static Account loggedInAccount = new Account();
	public static Customer loggedInCustomer = new Customer();
	
	
	@Autowired
	private MainController mainController;
	
	@Autowired
	private LoginView loginView;


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
