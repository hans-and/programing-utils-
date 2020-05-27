package hasses.magical.tools;

import static org.apache.log4j.Logger.getRootLogger;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "hasses.magical.tools" })
public class ProgramingUtilsApplication {

	private static final Logger LOGGER = Logger.getLogger(ProgramingUtilsApplication.class);

	public static void main(String[] args) {

		org.apache.log4j.RollingFileAppender appnder = (RollingFileAppender) getRootLogger()
				.getAppender("rollingFile");

		File myFile = new File(appnder.getFile());

		new File(myFile.getParent()).mkdirs();

		if (!myFile.exists()) {
			try {
				myFile.createNewFile();
			} catch (IOException e) {
				LOGGER.error("kunde inte skapa log fil",e);
			}
		}

		SpringApplication.run(ProgramingUtilsApplication.class, args);
		LOGGER.debug("Startat ProgramingUtilsApplication.class with args:" + args);

		LOGGER.debug("Loggfile:" + myFile.getAbsolutePath());
	}
}