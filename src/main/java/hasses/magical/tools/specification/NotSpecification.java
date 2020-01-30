package hasses.magical.tools.specification;

public class NotSpecification<T> extends CompositeSpecification<T>
{
   protected ISpecification<T> other;
   public NotSpecification(ISpecification<T> other)
   {
      super();
      this.other = other;
   }

   @Override
   public boolean IsSatisfiedBy(T candidate)
   {
      return !other.IsSatisfiedBy(candidate);
   }

}
