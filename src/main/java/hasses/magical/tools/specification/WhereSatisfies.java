package hasses.magical.tools.specification;


public class WhereSatisfies
{  
   public ToSpecification where;

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((where == null) ? 0 : where.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      WhereSatisfies other = (WhereSatisfies) obj;
      if (where == null)
      {
         if (other.where != null) return false;
      }
      else if (!where.equals(other.where)) return false;
      return true;
   }  
}
