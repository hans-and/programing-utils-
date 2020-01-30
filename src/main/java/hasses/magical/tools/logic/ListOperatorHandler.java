package hasses.magical.tools.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import hasses.magical.tools.dto.ListOperationResultDTO;
import hasses.magical.tools.dto.ListOperationsDTO;

public class ListOperatorHandler
{

   private static ListOperatorHandler lopHandler = null;

   private ListOperatorHandler()
   {

   }

   public static ListOperatorHandler getInstance()
   {
      if (lopHandler == null)
      {
         lopHandler = new ListOperatorHandler();
         return lopHandler;
      }
      else
      {
         return lopHandler;
      }
   }
   
   public ListOperationResultDTO getIntersection(ListOperationsDTO source) {
      getIntersection(stringToCollection(source.getListA()),stringToCollection(source.getListB()));
      return null;   
   }
   
   private Collection<String> stringToCollection(String listSource)
   {
      return new ArrayList<String>(Arrays.asList(listSource.split(";"))); 
   }

   private Collection<String> getIntersection(Collection<String> aSet,Collection<String> bSet)
   {
      Collection<String> intersection = new ArrayList<>(aSet); 
      intersection.retainAll(bSet);
      return intersection;
   }
}
