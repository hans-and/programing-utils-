package hasses.magical.tools.specification;


import java.text.ParseException;
import static hasses.magical.tools.specification.AttributeValueHelper.getAttributeValue;
public class ObjectAttributeSpec<T> extends CompositeSpecification<T>
{

   protected String attribute;
   protected AttributeIs operator; 
   
   public ObjectAttributeSpec(String attribute,String operator, String value)
   {
      super();
      this.attribute = attribute;
      this.value = value;
      if("<".equals(operator.trim())) {
         this.operator = new IsLessThan();
      }else if(">".equals(operator.trim())) {
         this.operator = new IsGreaterThan();
      }else {
         this.operator = new IsEqualTo();
      }
   }


   protected String value;
   
   
   @Override
   public boolean IsSatisfiedBy(T candidate) 
   {
      
      Object attVal = getAttributeValue(candidate, attribute);
     
      try
      {
         return operator.is(attVal, value);
      }
      catch (ParseException e)
      {
        throw new RuntimeException("unable to parse an eval: "+attribute+"op"+value, e);
      }
   }



}
