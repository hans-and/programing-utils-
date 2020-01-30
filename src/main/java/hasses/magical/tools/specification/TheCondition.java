package hasses.magical.tools.specification;



public class TheCondition extends ToSpecification
{
   private String condition;
   
   public TheCondition(String condition)
   {
      super();
      this.condition = condition;
   }

   public String getCondition()
   {
      return condition;
   }

   public void setCondition(String condition)
   {
      this.condition = condition;
   }

 

   public TheCondition()
   {
      super();
   }

   @Override
   public ISpecification<Object> toSpecification()
   {
      String[] tokens = condition.split(";");
      if (tokens.length > 3)
      {
         if (tokens[0].equalsIgnoreCase("not"))
         {
            return new ObjectAttributeSpec<>(tokens[1], tokens[2], tokens[3]).Not();
         }
         else
         {
            throw new RuntimeException("To Many tokens: " + condition);
         }

      }
      else if (tokens.length == 3)
      {
         return new ObjectAttributeSpec<>(tokens[0], tokens[1], tokens[2]);
      }
      else
      {
         throw new RuntimeException("To Few tokens: " + condition);
      }
   }

   

}
