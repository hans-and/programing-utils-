package hasses.magical.tools.specification;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;



import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;

import com.fasterxml.jackson.databind.ObjectMapper;

import hasses.magical.tools.dto.ListOperationsDTO;

class ObjectAttributeSpecTest
{

   SpecToFromJson jsonCon;

   private static class MyChainedObJ
   {
      MyChainedObJ next;

      String id;

      Date createdDate;

      String status;
   }

   

   @Test
   public void testEq() throws ParseException
   {

   }

   @Test
   public void testLessThan() throws ParseException
   {

      MyChainedObJ obj0 = new MyChainedObJ();
      MyChainedObJ obj1 = new MyChainedObJ();
      MyChainedObJ obj2 = new MyChainedObJ();
      obj0.next = obj1;
      obj1.next = obj2;
      obj2.id = "hopp";
      obj2.createdDate = SpecificationHelp.stringToDate("2014-01-01 10:50");

      ISpecification<MyChainedObJ> chainedSpec = new ObjectAttributeSpec<MyChainedObJ>("next.next.id", "=", "hopp");

      assertTrue("chainedSpec id = hopp", chainedSpec.IsSatisfiedBy(obj0));

      chainedSpec = new ObjectAttributeSpec<MyChainedObJ>("next.next.createdDate", "<", "2014-01-01 10:55");

      assertTrue("createdDate < 2014-01-01 10:55 was not true", chainedSpec.IsSatisfiedBy(obj0));
   }

   @Test
   public void testGreaterThan() throws ParseException
   {

      MyChainedObJ obj0 = new MyChainedObJ();
      MyChainedObJ obj1 = new MyChainedObJ();
      MyChainedObJ obj2 = new MyChainedObJ();
      obj0.next = obj1;
      obj1.next = obj2;
      obj2.id = "hopp";
      obj2.createdDate = SpecificationHelp.stringToDate("2014-01-01 10:50");

      ISpecification<MyChainedObJ> chainedSpec = new ObjectAttributeSpec<MyChainedObJ>("next.next.id", "=", "hopp");

      assertTrue("chainedSpec id = hopp", chainedSpec.IsSatisfiedBy(obj0));

      chainedSpec = new ObjectAttributeSpec<MyChainedObJ>("next.next.createdDate", ">", "2014-01-01 10:45");

      assertTrue("createdDate < 2014-01-01 10:55 was not true", chainedSpec.IsSatisfiedBy(obj0));
   }

   @Test
   public void shapeTest() throws JsonGenerationException, JsonMappingException, IOException {
      AllOfThe v2 = new AllOfThe();
      List<ToSpecification> list = new ArrayList<ToSpecification>();
      list.add(new TheCondition("muff=hej"));
     
      v2.setConditions(list);

      AnyOfThe v3 = new AnyOfThe();
      list = new ArrayList<ToSpecification>();
      list.add(new TheCondition("this=that"));
      list.add(new TheCondition("maybe=another"));
      
      v3.setConditions(list);
      
      AllOfThe v = new AllOfThe();
      list = new ArrayList<ToSpecification>();
      list.add(new TheCondition("hej=hej"));
      list.add(new TheCondition("hej=hopp"));
      list.add(v2);
      list.add(v3);
      v.setConditions(list);

      System.out.println("-- serializing --");
      ObjectMapper om = new ObjectMapper();
      String s = om.writeValueAsString(v);
      System.out.println(s);

      System.out.println("-- deserializing --");
      AllOfThe view = om.readValue(s, AllOfThe.class);
      System.out.println(view);
      ListOperationsDTO dto = new ListOperationsDTO();
      dto.setListA("a,aa,aa");
      dto.setListB("b,aa,aa");
      dto.setOperation("op");
      s = om.writeValueAsString(dto);
   }
   
   @Test
   public void testSpecBuilder() throws ParseException, JsonGenerationException, JsonMappingException, IOException
   {
      jsonCon = new SpecToFromJson();
      WhereSatisfies where = new WhereSatisfies();
      //AndList dateBetween = newAndList("next.next.createdDate,>,2014-01-01 10:45", "next.next.createdDate,<,2014-01-01 10:55");
      AnyOfThe statuses = newOrList("next.next.status;=;Ok", "next.next.status;=;Hint");
      AllOfThe allCond = new AllOfThe();
      allCond.setConditions(new ArrayList<ToSpecification>());
      //allCond.specifiabels.add(dateBetween);
      allCond.getConditions().add(statuses);
      //allCond.specifiabels.add(newSpec("next.next.id;=;hopp"));
      where.where = allCond;
      //Specifiable sinspec = newSpec("next.next.id;=;hopp");
      String s = null;
      
      ToSpecification test = new TheCondition("next.next.id;=;hopp");
      ToSpecification listDto = newAndList("next.next.status;=;Ok", "next.next.status;=;Hint");
      ToSpecification between = newAndList("next.next.createdDate,>,2014-01-01 10:45", "next.next.createdDate,<,2014-01-01 10:55");
      
      ToSpecification contains = new ListContains();
      ((ListContains)contains).setAnItemThat("name=Hasse Andersson");
      ((ListContains)contains).setIn("customers");
      
      
      ((AllOfThe)listDto).getConditions().add(between);
      ((AllOfThe)listDto).getConditions().add(test);
      ((AllOfThe)listDto).getConditions().add(contains);
      allCond.getConditions().add(listDto);
      try
      {         
         s = jsonCon.toJson(where);
      }
      catch (Exception e)
      {
         System.out.print(e.getMessage());
      }

      Javers javers = JaversBuilder.javers().build();
      
      
      assertTrue(s != null);
      WhereSatisfies deser= jsonCon.getObjectMapper().readValue(s, WhereSatisfies.class);
      boolean eq = deser.equals(where);
      Diff diff = javers.compare(where, deser);
      
      
      
      System.out.println("APPAPPA "+diff.getChanges().size()+" has changes: "+diff.hasChanges());
      diff.getChanges().forEach(action->System.out.println("APPAPPA "+action.toString()));
      //System.out.print(diff);
     // System.out.print(diff.changesSummary());
      
     System.out.print(eq);
   }


   @Test
   public void testSpecFromFile() throws ParseException, JsonGenerationException, JsonMappingException, IOException
   {
      jsonCon = new SpecToFromJson();
      ClassLoader classLoader = this.getClass().getClassLoader();
      File file = new File(classLoader.getResource("TestQuery.json").getFile());
      // WhereSatisfies s = jsonCon.toWhereSatisfies(file);

      WhereSatisfies allCond = jsonCon.toWhereSatisfies(file);
      
      assertTrue(allCond != null);
      ISpecification<Object> spes =  allCond.where.toSpecification();
      spes.IsSatisfiedBy(jsonCon);
      
   }

   private AnyOfThe newOrList(String... expression)
   {
      AnyOfThe res = new AnyOfThe();
      res.setConditions(new ArrayList<ToSpecification>()); 
      for (String exp : expression)
      {
         res.getConditions().add(newSpec(exp));
      }
      return res;
   }

   private AllOfThe newAndList(String... expression)
   {
      AllOfThe res = new AllOfThe();
      res.setConditions(new ArrayList<ToSpecification>()); 
      for (String exp : expression)
      {
         res.getConditions().add(newSpec(exp));
      }
      return res;
   }

   private TheCondition newSpec(String expression)
   {
      TheCondition res = new TheCondition();
      
      res.setCondition(expression);
      return res;
   }

}
