package hasses.magical.tools.dto;

public class ListOperationsDTO
{

   public ListOperationsDTO()
   {
      super();
      
   }

   public ListOperationsDTO(String listA, String listB, String operation)
   {
      super();
      this.listA = listA;
      this.listB = listB;
      this.operation = operation;
   }

   private String listA;

   private String listB;

   private String operation;

   public String getListA()
   {

      return listA;
   }

   public String getListB()
   {

      return listB;
   }

   public String getOperation()
   {
      // TODO Auto-generated method stub
      return operation;
   }

   public void setListA(String listA)
   {
      this.listA = listA;
   }

   public void setListB(String listB)
   {
      this.listB = listB;
   }

   public void setOperation(String operation)
   {
      this.operation = operation;
   }

}
