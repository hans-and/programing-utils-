package hasses.magical.tools.beans.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hasses.magical.tools.dto.BusResultDTO;
import hasses.magical.tools.logic.SLBus55;
import hasses.magical.tools.repository.SlApiDataRepository;

@Controller
public class SLBusController {

	private static final Logger LOGGER = Logger.getLogger(SLBusController.class);
	@Autowired
	private SlApiDataRepository slData;
	private SLBus55 bus55Handler = null;

	private SLBus55 getHandler() {
		if (bus55Handler == null) {
			bus55Handler = new SLBus55(slData);
		}
		return bus55Handler;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/bus55")
	@ResponseBody
	public BusResultDTO getBus55() {
		try {
			return getHandler().getMinutesToDeparture();
		} catch (Exception e) {
			LOGGER.error("Hoppsan kerstin getBus55 got Error"+e);
			return new BusResultDTO("Error", "Aj Aj Aj", "Error");
		}
		
		

	}

	

}
