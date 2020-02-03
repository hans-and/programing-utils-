package hasses.magical.tools.specification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "anItemThat", "in"})
public class ListContains extends ToSpecification
{

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((anItemThat == null) ? 0 : anItemThat.hashCode());
      result = prime * result + ((in == null) ? 0 : in.hashCode());
      return result;
   }
   @Override
   public boolean equals(Object obj)
   {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      ListContains other = (ListContains) obj;
      if (anItemThat == null)
      {
         if (other.anItemThat != null) return false;
      }
      else if (!anItemThat.equals(other.anItemThat)) return false;
      if (in == null)
      {
         if (other.in != null) return false;
      }
      else if (!in.equals(other.in)) return false;
      return true;
   }
   
   private String in;
   @JsonProperty("an-item-that") 
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
