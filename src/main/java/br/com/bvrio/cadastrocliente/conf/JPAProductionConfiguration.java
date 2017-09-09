package br.com.bvrio.cadastrocliente.conf;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Profile("prod")
public class JPAProductionConfiguration {
	
	@Autowired
	private Environment envionment;
	
	@Bean
	public Properties aditionalProperties() {
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		return props;
	}
	
	@Bean
	private DriverManagerDataSource dataSource() throws URISyntaxException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		
		//usuario:senha@host:port/path
		URI dbUrl = new URI(envionment.getProperty("DATABASE_URL"));
		
		dataSource.setUrl("jdbc:postgresql://"+dbUrl.getHost()+":"+dbUrl.getPort()+dbUrl.getPath());
		dataSource.setUsername(dbUrl.getUserInfo().split(":")[0]);
		dataSource.setPassword(dbUrl.getUserInfo().split(":")[1]);
		
		return dataSource;
	}

}