package hasses.magical.tools.specification;

public class AndSpecification<T> extends CompositeSpecification<T>
{

   protected ISpecification<T> left;
   protected ISpecification<T> right;
   @Override
   public boolean IsSatisfiedBy(T candidate)
   {
      
      return left.IsSatisfiedBy(candidate) && right.IsSatisfiedBy(candidate) ;
   }
   
   public AndSpecification(ISpecification<T> left, ISpecification<T> right)
   {
      super();
      this.left = left;
      this.right = right;
   }

}