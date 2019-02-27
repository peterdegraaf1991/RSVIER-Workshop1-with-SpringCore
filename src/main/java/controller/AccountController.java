package controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import jline.internal.Log;
import model_class.Account;
import model_class.Customer;
import dao.DaoFactory;
import utility.Hashing;
import utility.Hashing.CannotPerformOperationException;
import view.AccountView;
import view.LoginView;


@Component
public class AccountController extends Controller {
	@Autowired 
	AccountView accountView;
	
	@Autowired 
	CustomerController customerController;
	
	@Autowired 
	LoginView loginView;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(AccountController.class);
	
	@Override
	public void runController() {
		int keuze = 1;
		Controller.newView = true;
		do {
			if (Controller.newView) {
				accountView.clearTerminal();
				accountView.printMenuHeader();
				accountView.printMenuOptions();
				Controller.newView = false;
			}
			keuze = accountView.requestMenuOption();

			switch (keuze) {
			case 1:
				createAccount();
				requestNewMenu();
				break;
			case 2:
				viewAllAccounts();
				requestNewMenu();
				break;
			case 3:
				changeEmail();
				requestNewMenu();
				break;
			case 4:
				changePassword();
				requestNewMenu();
				break;
			case 5:
				deleteAccount();
				requestNewMenu();
				break;
			case 9:
				keuze = 0;
				Controller.newView = true;
				break;
			case 0:
				accountView.logoutTimer();
				System.exit(0);
				break;
			default:
				accountView.invalidInput();
				break;
			}
		} while (keuze != 0);
	}

	private void viewAllAccounts() {
		if (!workerOrAdminPermission())
			accountView.noPermission();
		List<Account> accountList = DaoFactory.getAccountDao()
				.readAllAccounts();
		if (accountList.size() <= 0) {
			accountView.noAccountFound();
			return;
		}
		accountView.printAccountList(accountList);
	}

	private void deleteAccount() {
		if (!workerOrAdminPermission()) {
			if (accountView.confirmDeleteAccount())
				DaoFactory.getAccountDao().deleteAccount(
						LoginController.loggedInAccount.getId());
			System.exit(0);
		}
		if (workerOrAdminPermission()) {
			Customer customer = customerController.choosePersonFromList();
			Account account = DaoFactory.getAccountDao()
					.readAccountByCustomerId(customer.getId());
			if (account.equals(LoginController.loggedInAccount)) {
				if (accountView.confirmDeleteAccount()) {
					DaoFactory.getAccountDao().deleteAccount(account.getId());
					System.exit(0);
				}
			} else {
				DaoFactory.getAccountDao().deleteAccount(account.getId());
				accountView.accountDeleted();
			}
		}
	}

	public void createAccount() {
		if (!adminPermission()) {
			return;
		}
		Customer customer = customerController.choosePersonFromList();
		if (customer == null) {
			return;
		}
		if (DaoFactory.getAccountDao()
				.readAccountByCustomerId(customer.getId()).getId() != 0) {
			accountView.personAlreadyHasAccount();
			return;
		}
		Account account = new Account();
		account.setCustomer(customer);
		account.setEmail(accountView.requestInputUsername());
		String password = loginView.requestInputPassword();
		String hash = null;
		try {
			hash = utility.Hashing.createHash(password);
		} catch (CannotPerformOperationException e) {
			Log.info(e);
		}
		account.setHash(hash);
		int accountTypeId = accountView.requestAccountType();
		account.setAccountTypeId(accountTypeId);
		DaoFactory.getAccountDao().createAccount(account);
		accountView.accountCreated();
		Controller.newView = true;
	}

	public void changePassword() {
		Customer customer = customerController.choosePersonFromList();
		if (customer == null) {
			return;
		}
		Account account = DaoFactory.getAccountDao().readAccountByCustomerId(
				customer.getId());
		if (account.getId() == 0) {
			accountView.noAccountFound();
			return;
		}
		String hash = null;
		try {
			hash = Hashing.createHash(loginView.requestInputPassword());
		} catch (CannotPerformOperationException e) {
			LOG.info("Error",e);
		}
		account.setHash(hash);
		DaoFactory.getAccountDao().updateAccount(account);
		accountView.passwordChanged();
	}

	public void changeEmail() {
		Customer customer = customerController.choosePersonFromList();
		if (customer == null) {
			return;
		}
		Account account = DaoFactory.getAccountDao().readAccountByCustomerId(
				customer.getId());
		if (account.getId() == 0) {
			accountView.noAccountFound();
			return;
		}
		account.setEmail(accountView.requestInputUsername());
		DaoFactory.getAccountDao().updateAccount(account);
		accountView.emailChanged();
	}

}
