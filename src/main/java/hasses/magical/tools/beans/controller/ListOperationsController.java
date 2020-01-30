package hasses.magical.tools.beans.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hasses.magical.tools.dto.ListOperationResultDTO;
import hasses.magical.tools.dto.ListOperationsDTO;
import hasses.magical.tools.logic.ListOperatorHandler;

@Controller
public class ListOperationsController
{
   
    @RequestMapping(method = RequestMethod.POST, value="/list/operation")
    @ResponseBody
    public ListOperationResultDTO doOperation(ListOperationsDTO source) {
       if(source==null) {
          throw new RuntimeException("ListOperationsDTO source must not be null");
       }
       if(source.getOperation()!=null&&source.getOperation().equalsIgnoreCase("intersection")) {
          return ListOperatorHandler.getInstance().getIntersection(source);
       }else {
          throw new RuntimeException("Unknown Operation: "+source.getOperation()+" dto: "+source);
       }
    }
}
