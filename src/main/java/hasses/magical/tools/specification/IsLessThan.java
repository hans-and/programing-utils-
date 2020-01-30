package hasses.magical.tools.specification;

import static hasses.magical.tools.specification.SpecificationHelp.stringToDate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

public class IsLessThan implements AttributeIs
{

   @Override
   public boolean is(Object attributeCandidate, String pattern) throws ParseException
   {
      if(attributeCandidate instanceof Byte) {
         return ((Byte)attributeCandidate).compareTo(new Byte(pattern))<0;
      }else if(attributeCandidate instanceof Date) {
         return ((Date)attributeCandidate).compareTo(stringToDate(pattern))<0;
      }else if(attributeCandidate instanceof Integer) {
         return ((Integer)attributeCandidate).compareTo(new Integer(pattern))<0;
      }else if(attributeCandidate instanceof Double) {
         return ((Double)attributeCandidate).compareTo(new Double(pattern))<0;
      }else if(attributeCandidate instanceof Boolean) {
         return ((Boolean)attributeCandidate).compareTo(new Boolean(pattern))<0;
      }else if(attributeCandidate instanceof String) {
         return ((String)attributeCandidate).compareTo(pattern)<0;
      }else if (attributeCandidate instanceof BigDecimal) {
         return ((BigDecimal)attributeCandidate).compareTo(new BigDecimal(pattern))<0;
      }else if (attributeCandidate instanceof Float) {
         return ((Float)attributeCandidate).compareTo(new Float(pattern))<0;
      }else {
         String candidateStr = ""+attributeCandidate;
         return candidateStr.compareTo(pattern)<0;
      }

   }

}
