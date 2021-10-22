package hu.ak_akademia.property;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReader {

	private static final String FILE_PATH = "resources/config.properties";

	public String readPropertyValue(String key) {
		String property = null;
		try (FileReader reader = new FileReader(FILE_PATH)) {
			Properties properties = new Properties();
			properties.load(reader);
			property = properties.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return property;
	}

	public Properties getDBLoginProperties() {
		Properties properties = new Properties();
		properties.setProperty("user", readPropertyValue("username"));
		properties.setProperty("password", readPropertyValue("password"));
		return properties;
	}
}
