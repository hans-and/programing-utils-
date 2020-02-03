package hasses.magical.tools.specification;

import static hasses.magical.tools.specification.AttributeValueHelper.getAttributeValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import static hasses.magical.tools.specification.SpecificationHelp.expressionToSpecification; 

public class ObjectListAttributeSpec<T> extends CompositeSpecification<T>
{
   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
      result = prime * result + ((condition == null) ? 0 : condition.hashCode());
      result = prime * result + ((operator == null) ? 0 : operator.hashCode());
      return result;
   }



   @Override
   public boolean equals(Object obj)
   {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      ObjectListAttributeSpec other = (ObjectListAttributeSpec) obj;
      if (attribute == null)
      {
         if (other.attribute != null) return false;
      }
      else if (!attribute.equals(other.attribute)) return false;
      if (condition == null)
      {
         if (other.condition != null) return false;
      }
      else if (!condition.equals(other.condition)) return false;
      if (operator != other.operator) return false;
      return true;
   }

   public enum ListOp {
      ATLEAST_ONE, ALL;
   }

   public ObjectListAttributeSpec(String attribute,String expression,ListOp op) {
      this.operator = op;
      this.attribute=attribute;
      condition = expressionToSpecification(expression);
   }
   private ListOp operator = ListOp.ATLEAST_ONE;

   private ISpecification<Object> condition;

   private String attribute;

   
   @SuppressWarnings("unchecked")
   @Override
   public boolean IsSatisfiedBy(T candidate)
   {
      Object attVal = getAttributeValue(candidate, attribute);
      if (attVal instanceof Iterable)
      {  
         return testAll(((Iterable<Object>) attVal));
      }
      else if (attVal instanceof Object[])
      {
         return testAll( new ArrayList<Object>(Arrays.asList(attVal)));
         
      }
      else
      {
         throw new RuntimeException("Specification error " + candidate + " attribute:" + attribute + " is not Iterable nor array");
      }
   }

   

   private boolean testAll(Iterable<Object> arrayList)
   {
      if (operator == ListOp.ATLEAST_ONE)
      {
         return testAtleastOne((Iterable<Object>) arrayList);
      }
      else
      {
         return testThatAll((Iterable<Object>) arrayList);
      }
   }



   private boolean testAtleastOne(Iterable<Object> candidates)
   {
      AtomicBoolean bool = new AtomicBoolean(false);
      candidates.forEach(object -> {
         if (condition.IsSatisfiedBy(object))
         {
            bool.set(true);
         }
      });
      return bool.get();
   }

   private boolean testThatAll(Iterable<Object> candidates)
   {
      AtomicBoolean bool = new AtomicBoolean(true);
      candidates.forEach(object -> {
         if (!condition.IsSatisfiedBy(object))
         {
            bool.set(false);
         }
      });
      return bool.get();

   }

}
