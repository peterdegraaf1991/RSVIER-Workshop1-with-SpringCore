package utility;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.zaxxer.hikari.HikariDataSource;

public enum DatabaseConnection {
	INSTANCE;

	private static final Logger LOG = LoggerFactory
			.getLogger(DatabaseConnection.class);

	private String username;
	private String password;
	private String url ;
	private Connection connection;
	private HikariDataSource dataSource = new HikariDataSource();
	static ServerAddress serverAddress = null;

	// MongoDB Connection managed by MongoClient
	public DB getConnectionMongo() {
		
		MongoCredential workshop1Auth = MongoCredential.createPlainCredential(
				"username", "workshop1", "password".toCharArray());
		List<MongoCredential> auths = new ArrayList<MongoCredential>();
		auths.add(workshop1Auth);
		try {
			serverAddress = new ServerAddress("localhost", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("localhost not found");
		}
		MongoClient mongoClient = new MongoClient(serverAddress);
		DB db = mongoClient.getDB("workshop1");
		System.out.println("returning db");
		return db;
	}

	// SQL Connection managed by Hikari
	public Connection getConnectionSQL() {
		if (username == null || password == null || url == null) {
			getLoginDetails();
			dataSource.setJdbcUrl(url);
			dataSource.setUsername(username);
			dataSource.setPassword(password);
		}
		try {
			if (connection == null || connection.isClosed()) {
				connection = dataSource.getConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	private void getLoginDetails() {
		ClassLoader cl = this.getClass().getClassLoader();
		try (java.io.InputStream xmlFile = cl.getResourceAsStream("utility/LoginDetails.xml")){
			Document document = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder()		
					.parse(xmlFile);
			document.getDocumentElement().normalize();
			username = document.getElementsByTagName("username").item(0)
					.getTextContent();
			password = document.getElementsByTagName("password").item(0)
					.getTextContent();
			url = document.getElementsByTagName("url").item(0).getTextContent();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	// SQL Connection managed by DriverManager
	/*
	 * public Connection getConnection() { if (username == null || password ==
	 * null || url == null) getLoginDetails();
	 * 
	 * try { if (connection == null || connection.isClosed()) { connection =
	 * DriverManager.getConnection(url, username, password); } } catch
	 * (SQLException e) { e.printStackTrace(); } return connection; }
	 */
}
