package hu.ak_akademia.dal.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import hu.ak_akademia.property.PropertyFileReader;

public class ConnectionFactory {

	private static ConnectionFactory instance;
	private final PropertyFileReader propertyFileReader = new PropertyFileReader();
	private Connection connection;
	private Properties properties;

	public static ConnectionFactory getInstance() {
		if (instance == null) {
			instance = new ConnectionFactory();
		}
		return instance;
	}

	private Properties getProperties() {
		if (properties == null) {
			properties = propertyFileReader.getDBLoginProperties();
		}
		return properties;
	}

	public Connection connect() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(propertyFileReader.readPropertyValue("url"), getProperties());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
