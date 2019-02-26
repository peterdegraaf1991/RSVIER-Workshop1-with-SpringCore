package utility;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoDb {
	ServerAddress serverAddress = null;
	
	public void createConnection(){
	MongoCredential workshop1Auth = MongoCredential.createPlainCredential("username", "workshop1", "password".toCharArray());

	List<MongoCredential> auths = new ArrayList<MongoCredential>();
	auths.add(workshop1Auth);

	try {
		serverAddress = new ServerAddress("localhost", 27017);
	} catch (UnknownHostException e) {
		e.printStackTrace();
	}
	MongoClient mongoClient = new MongoClient(serverAddress, auths);
	DB db = mongoClient.getDB("workshop1");
	}
}
