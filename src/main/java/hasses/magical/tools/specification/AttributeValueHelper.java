package hasses.magical.tools.specification;

import java.lang.reflect.Field;

public class AttributeValueHelper
{

   private AttributeValueHelper() {
      
   }
   public static Object getAttributeValue(Object source, String attribute)   
   {
      if (attribute.equals("this")){
         return ""+source;
      }
      
      if (attribute.startsWith("this.")){
         attribute = attribute.substring(5);
      }
      
      String[] attributes = attribute.split("\\.");
      Object result = source;
      for (int i = 0; i < attributes.length; i++)
      {
         result = getAttVal(result, attributes[i],attribute);
      }
      return result;
   }

   private static Object getAttVal(Object source, String attributeName,String attribute)
   {
      Field myField = null;
      try
      {
         myField = source.getClass().getDeclaredField(attributeName);
         myField.setAccessible(true);
         return myField.get(source);
      }
      catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
      {
         throw new RuntimeException("unable to parse: " + attribute, e);
      }

   }
}
