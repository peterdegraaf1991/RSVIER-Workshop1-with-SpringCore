package controller;

import model_class.Account;
import model_class.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.AccountDaoImpl;
import view.LoginView;
import utility.Hashing;
import utility.Hashing.CannotPerformOperationException;
import utility.Hashing.InvalidHashException;

@Component
public class LoginController extends Controller {
	public static Account loggedInAccount = new Account();
	public static Customer loggedInCustomer = new Customer();
	
	@Autowired AccountDaoImpl accountDao;
	
	@Autowired
	private MainController mainController;
	
	@Autowired
	private LoginView loginView;


	public void runController() {
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
		loggedInAccount = accountDao.readAccountByEmail(loginView
				.requestInputUsername());
		int accountId = loggedInAccount.getId();
		if (loggedInAccount.getId() != 0) {
			String hash = accountDao.readHash(accountId);
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
