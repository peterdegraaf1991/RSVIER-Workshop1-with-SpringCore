package dao;

import java.util.List;

import model_class.Account;

public interface AccountDao {

	public int createAccount(Account account);

	public Account readAccountByCustomerId(int id);

	public Account readAccountById(int id);

	public Account readAccountByEmail(String email);
	
	public List<Account> readAllAccounts ();

	public void updateAccount(Account account);

	public int deleteAccount(int id);

	public String readHash(int id);
	
	// public void printAccount();
	// public void printAccount(String email);

}
