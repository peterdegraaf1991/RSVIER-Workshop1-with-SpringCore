package dao;

import model_class.AccountType;

public interface AccountTypeDao {

	public void createAccountType(AccountType accountType);

	public AccountType readAccountType(int id);

	public void updateAccountType(AccountType accounttype);

	public void deleteAccountType(int accounttype);

	// public void printAccountType();
	// public void printAccountType(int id);
}
