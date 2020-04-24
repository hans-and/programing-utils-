package hasses.magical.tools;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@SpringBootApplication(scanBasePackages = {"hasses.magical.tools" })
public class ProgramingUtilsApplication {

	private static final Logger LOGGER = Logger.getLogger(ProgramingUtilsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProgramingUtilsApplication.class, args);
		LOGGER.debug("Startat ProgramingUtilsApplication.class with args:" + args);

		org.apache.log4j.RollingFileAppender appnder = (RollingFileAppender) LOGGER.getRootLogger().getAppender("rollingFile");
		
		File myFile = new File(appnder.getFile());
		new File(myFile.getParent()).mkdirs();
		
		LOGGER.debug("Loggfile:" +myFile.getAbsolutePath());
	}
}