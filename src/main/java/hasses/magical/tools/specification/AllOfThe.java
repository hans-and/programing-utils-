package hasses.magical.tools.specification;

import java.util.List;



public class AllOfThe extends ToSpecification
{
   private List<ToSpecification> conditions;

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((conditions == null) ? 0 : conditions.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      AllOfThe other = (AllOfThe) obj;
      if (conditions == null)
      {
         if (other.conditions != null) return false;
      }
      else if (!conditions.equals(other.conditions)) return false;
      return true;
   }

   public List<ToSpecification> getConditions()
   {
      return conditions;
   }

   public void setConditions(List<ToSpecification> shapes)
   {
      this.conditions = shapes;
   }

   @Override
   public ISpecification<Object> toSpecification()
   {
      ISpecification<Object> result =null;
      for (ToSpecification aSpecDto : conditions)
      {
         if(result==null) {
            result= aSpecDto.toSpecification();
         }else {
            result = result.And(aSpecDto.toSpecification());
         }
      }
      return result;
   }
}
