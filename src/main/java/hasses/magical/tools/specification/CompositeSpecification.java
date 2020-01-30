package hasses.magical.tools.specification;

public abstract class CompositeSpecification<T> implements ISpecification<T>
{

   @Override
   public abstract boolean IsSatisfiedBy(T candidate);

   @Override
   public ISpecification<T> And(ISpecification<T> other)
   {  
      return new AndSpecification<T>(this, other);
   }

   @Override
   public ISpecification<T> Or(ISpecification<T> other)
   {
      return new OrSpecification<T>(this,other);
   }


   @Override
   public ISpecification<T> Not()
   {
      return new NotSpecification<T>(this);
   }

}
