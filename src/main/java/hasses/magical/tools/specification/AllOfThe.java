package hasses.magical.tools.specification;

import java.util.List;



public class AllOfThe extends ToSpecification
{
   private List<ToSpecification> conditions;

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
