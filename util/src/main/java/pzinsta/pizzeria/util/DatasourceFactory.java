package pzinsta.pizzeria.util;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class DatasourceFactory {
	
	private static DataSource DATA_SOURCE;
	
	public static synchronized DataSource getDataSource() {
		if (DATA_SOURCE == null) {
			try {
				initializeDataSource();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return DATA_SOURCE;
	}
	
	private static void initializeDataSource() throws Exception {
		Properties properties = new Properties();
		properties.load(DatasourceFactory.class.getResourceAsStream("/db.properties"));
		Class.forName("org.postgresql.Driver");
		DATA_SOURCE = BasicDataSourceFactory.createDataSource(properties);
	}
}
