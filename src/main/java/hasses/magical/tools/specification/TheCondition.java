package hasses.magical.tools.specification;



public class TheCondition extends ToSpecification
{
   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((condition == null) ? 0 : condition.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      TheCondition other = (TheCondition) obj;
      if (condition == null)
      {
         if (other.condition != null) return false;
      }
      else if (!condition.equals(other.condition)) return false;
      return true;
   }

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
