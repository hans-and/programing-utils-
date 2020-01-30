package hasses.magical.tools.specification;

public class OrSpecification<T> extends CompositeSpecification<T>
{
   protected ISpecification<T> left;
   protected ISpecification<T> right;
   
   public OrSpecification(ISpecification<T> left, ISpecification<T> right)
   {
      super();
      this.left = left;
      this.right = right;
   }
   
   @Override
   public boolean IsSatisfiedBy(T candidate)
   {
      return left.IsSatisfiedBy(candidate)||right.IsSatisfiedBy(candidate);
   }

}