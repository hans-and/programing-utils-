package hasses.magical.tools.beans.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClientController {
	@RequestMapping("/")
	public String home() {
        return "index.html";    
	}
}
