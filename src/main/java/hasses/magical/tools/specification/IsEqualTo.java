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
         return attributeCandidate.equals(new Byte(pattern));
      }else if(attributeCandidate instanceof Date) {
         return attributeCandidate.equals(stringToDate(pattern));         
      }else if(attributeCandidate instanceof Integer) {
         return attributeCandidate.equals(new Integer(pattern)); 
      }else if(attributeCandidate instanceof Double) {
         return attributeCandidate.equals(new Double(pattern));         
      }else if(attributeCandidate instanceof Boolean) {
         return attributeCandidate.equals(new Boolean(pattern));         
      }else if(attributeCandidate instanceof String) {
         return attributeCandidate.equals(pattern);
      }else if (attributeCandidate instanceof BigDecimal) {
         return attributeCandidate.equals(new BigDecimal(pattern));         
      }else if (attributeCandidate instanceof Float) {
         return attributeCandidate.equals(new Float(pattern));         
      }else {
         String candidateStr = ""+attributeCandidate;
         return candidateStr.equals(pattern);
      }
   }

}

