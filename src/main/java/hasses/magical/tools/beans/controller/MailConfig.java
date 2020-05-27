package hasses.magical.tools.beans.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {
	@Value("${gmail.username}") 
	private String username;
	
	@Value("${gmail.password}") 
	private String password;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
