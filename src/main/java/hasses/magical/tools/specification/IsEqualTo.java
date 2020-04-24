package hasses.magical.tools.specification;

import static hasses.magical.tools.specification.SpecificationHelp.stringToDate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

public class IsEqualTo implements AttributeIs
{

   @Override
   public boolean is(Object attributeCandidate, String pattern) throws ParseException
   {
      if(attributeCandidate instanceof Byte) {
         return attributeCandidate.equals(Byte.parseByte(pattern));
      }else if(attributeCandidate instanceof Date) {
         return attributeCandidate.equals(stringToDate(pattern));         
      }else if(attributeCandidate instanceof Integer) {
         return attributeCandidate.equals(Integer.parseInt(pattern)); 
      }else if(attributeCandidate instanceof Double) {
         return attributeCandidate.equals(Double.parseDouble(pattern));         
      }else if(attributeCandidate instanceof Boolean) {
         return attributeCandidate.equals(Boolean.parseBoolean(pattern));         
      }else if(attributeCandidate instanceof String) {
         return attributeCandidate.equals(pattern);
      }else if (attributeCandidate instanceof BigDecimal) {
         return attributeCandidate.equals(new BigDecimal(pattern));         
      }else if (attributeCandidate instanceof Float) {
         return attributeCandidate.equals(Float.parseFloat(pattern));         
      }else {
         String candidateStr = ""+attributeCandidate;
         return candidateStr.equals(pattern);
      }
   }

}

