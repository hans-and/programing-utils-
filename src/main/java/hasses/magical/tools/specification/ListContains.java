package hasses.magical.tools.specification;

public class ListContains extends ToSpecification
{

   private String in;
   private String anItemThat;
   public String getIn()
   {
      return in;
   }
   public void setIn(String in)
   {
      this.in = in;
   }
   public String getAnItemThat()
   {
      return anItemThat;
   }
   public void setAnItemThat(String anItemThat)
   {
      this.anItemThat = anItemThat;
   }
   @Override
   public ISpecification<Object> toSpecification()
   {
      return new ObjectListAttributeSpec<Object>(in, anItemThat, ObjectListAttributeSpec.ListOp.ATLEAST_ONE);
   }

}
