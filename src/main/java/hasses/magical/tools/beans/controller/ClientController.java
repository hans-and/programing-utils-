package hasses.magical.tools.beans.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClientController {
	
	private AtomicInteger counter = new AtomicInteger(); 
	private static final Logger LOGGER =Logger.getLogger(ClientController.class);
	
	public ClientController() {
		LOGGER.debug("ClientController created home mapped to @RequestMapping(\"/\")");
	}
	@RequestMapping("/")
	public String home() {
		
		LOGGER.debug("Default page (index.html) called for the:"+counter.incrementAndGet()+" time since started");
		try {
			return "index.html";	
		} catch (Exception e) {
			LOGGER.error("Uhu"+e.getMessage(), e);
		}
		return null;
            
	}
}
