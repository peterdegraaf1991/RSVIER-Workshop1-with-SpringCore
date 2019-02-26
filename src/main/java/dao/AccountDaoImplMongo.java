package dao;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;





import utility.DatabaseConnection;
import model_class.Account;
import model_class.Customer;

public class AccountDaoImplMongo implements AccountDao {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(AccountDaoImplMongo.class);
	
	
	private static DBObject createDBObject(Account account) {
		BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
								
		docBuilder.append("_id", account.getId());
		docBuilder.append("email", account.getEmail());
		docBuilder.append("account_type_id", account.getAccountTypeId());
		docBuilder.append("hash", account.getHash());
		docBuilder.append("customer_id", account.getCustomer().getId());
		return docBuilder.get();
	}
	
	@Override
	public int createAccount(Account account) {
		DB db = DatabaseConnection.INSTANCE.getConnectionMongo();
		DBCollection collection = db.getCollection("account");
		DBObject doc = createDBObject(account);
		collection.insert(doc);
		// probably have to close connection somewhere
		// returning 1 for affected rows
		return 1;
	}

	@Override
	public Account readAccountByCustomerId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account readAccountById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account readAccountByEmail(String email) {
		DB db = DatabaseConnection.INSTANCE.getConnectionMongo();
		DBCollection collection = db.getCollection("account");
		DBObject query = BasicDBObjectBuilder.start().add("email", email).get();
		DBCursor cursor = collection.find(query);
		Account account = new Account();
		if(cursor.hasNext()){
		BasicDBObject object = (BasicDBObject)cursor.next();

		int id = object.getInt("_id");
		String hash = object.getString("hash");
		int accountTypeId = object.getInt("account_type_id");
		int customerId = object.getInt("customer_id)");

		account = new Account();
		account.setId(id);
		account.setAccountTypeId(accountTypeId);
		account.setHash(hash);
		account.setEmail(email);
		Customer customer = new Customer();
		customer.setId(customerId);
		account.setCustomer(customer);
	}
	return account;
	}

	@Override
	public List<Account> readAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateAccount(Account account) {
		// TODO Auto-generated method stub

	}

	@Override
	public int deleteAccount(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String readHash(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
