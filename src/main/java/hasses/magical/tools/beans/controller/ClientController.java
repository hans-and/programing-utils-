package hasses.magical.tools.beans.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ClientController {
	
	
	private AtomicInteger counter = new AtomicInteger(); 
	private static final Logger LOGGER =Logger.getLogger(ClientController.class);
	
	
	public ClientController() {
		LOGGER.debug("ClientController created home mapped to @RequestMapping(\"/\")");
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		try {
			model.addAttribute("contdesc", "En genialisk hemsida hämtad:"+counter.incrementAndGet() +" sen senaste om starten");
			return "index";	
		} catch (Exception e) {
			LOGGER.error("Uhu"+e.getMessage(), e);
		}
		return "index";
            
	}
	

}
