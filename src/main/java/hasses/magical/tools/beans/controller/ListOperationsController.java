package hasses.magical.tools.beans.controller;

import java.security.InvalidParameterException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hasses.magical.tools.dto.ListOperationResultDTO;
import hasses.magical.tools.dto.ListOperationsDTO;
import hasses.magical.tools.logic.listoperation.ListOperatorHandler;

@Controller
public class ListOperationsController {

	private static final Logger LOGGER =Logger.getLogger(ListOperationsController.class);
	
	
	public ListOperationsController() {
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/list/operation")
	@ResponseBody
	public ListOperationResultDTO doOperation(@RequestBody ListOperationsDTO source) {
		
		LOGGER.debug("invoked /list/operation with:"+source);
		
		if (source == null) {
			throw new InvalidParameterException("ListOperationsDTO source must not be null");    
		}
		try {
			return ListOperatorHandler.getInstance().getResult(source);	
		} catch (Exception e) {
			throw new InvalidParameterException("ListOperationsDTO source must not be null");
		}
		

	}

	@RequestMapping(method = RequestMethod.GET, value = "/greeting")
	@ResponseBody
	public ListOperationResultDTO doOperation() {
		LOGGER.debug("invoked /greeting");
		return new ListOperationResultDTO("Hej Hasse");

	}
	
	
}
