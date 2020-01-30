package hasses.magical.tools.specification;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;

import org.junit.Test;

public class IsGreaterThanTest
{

   @Test
   public void test() throws ParseException
   {
      assertTrue(new IsGreaterThan().is(true, "false"));
      assertFalse(new IsGreaterThan().is(false, "false"));
      assertFalse(new IsGreaterThan().is(false, "true"));
      
      assertTrue(new IsGreaterThan().is(10, "5"));
      assertFalse(new IsGreaterThan().is(5, "10"));
      assertFalse(new IsGreaterThan().is(10, "10"));

      assertTrue(new IsGreaterThan().is(new BigDecimal(5), "4.5"));
      assertFalse(new IsGreaterThan().is(5.5, "10"));
      assertFalse(new IsGreaterThan().is(10.50, "10.5"));
      
      assertTrue(new IsGreaterThan().is(new BigDecimal(5.55), "5.5"));

   }

}
