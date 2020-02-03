package hasses.magical.tools.specification;


import java.text.ParseException;
import static hasses.magical.tools.specification.AttributeValueHelper.getAttributeValue;
public class ObjectAttributeSpec<T> extends CompositeSpecification<T>
{

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
      result = prime * result + ((operator == null) ? 0 : operator.hashCode());
      result = prime * result + ((value == null) ? 0 : value.hashCode());
      return result;
   }


   @Override
   public boolean equals(Object obj)
   {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      ObjectAttributeSpec other = (ObjectAttributeSpec) obj;
      if (attribute == null)
      {
         if (other.attribute != null) return false;
      }
      else if (!attribute.equals(other.attribute)) return false;
      if (operator == null)
      {
         if (other.operator != null) return false;
      }
      else if (!operator.equals(other.operator)) return false;
      if (value == null)
      {
         if (other.value != null) return false;
      }
      else if (!value.equals(other.value)) return false;
      return true;
   }


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
