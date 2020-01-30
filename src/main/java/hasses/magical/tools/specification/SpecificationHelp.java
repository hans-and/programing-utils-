package hasses.magical.tools.specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SpecificationHelp
{
   final static SimpleDateFormat DATE_ONLY = new SimpleDateFormat("yyyy-MM-dd");

   final static SimpleDateFormat DATE_TIME = new SimpleDateFormat("yyyy-MM-dd hh:mm");

   static Date stringToDate(String date) throws ParseException
   {
      if (date.length() > 10)
      {
         return DATE_TIME.parse(date);
      }
      else
      {
         return DATE_ONLY.parse(date);
      }

   }
   
   public static ISpecification<Object> expressionToSpecification(String expression)
   {
      String[] tokens = expression.split(";");
      if (tokens.length > 3)
      {
         if (tokens[0].equalsIgnoreCase("not"))
         {
            return new ObjectAttributeSpec<>(tokens[1], tokens[2], tokens[3]).Not();
         }
         else
         {
            throw new RuntimeException("To Many tokens: " + expression);
         }

      }
      else if (tokens.length == 3)
      {
         return new ObjectAttributeSpec<>(tokens[0], tokens[1], tokens[2]);
      }
      else
      {
         throw new RuntimeException("To Few tokens: " + expression);
      }
   }
}

