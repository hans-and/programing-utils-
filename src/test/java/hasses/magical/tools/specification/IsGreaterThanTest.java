package hasses.magical.tools.specification;

import static org.junit.Assert.*;

import java.awt.Point;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Random;

import org.junit.Test;

public class IsGreaterThanTest
{

   private Random r = new Random();

   static final int[] PRIMES = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137,
            139, 149, 151, 157, 163, 167, 173};

   private Point publicKey;

   @Test
   public void genKey()
   {
      int primeA = PRIMES[r.nextInt(PRIMES.length)];
      int primeB = PRIMES[r.nextInt(PRIMES.length)];
      int modulusKey = primeA * primeB;
      publicKey.x = modulusKey;
      int phi = (primeA - 1) * (primeB - 1);
      int encrypt = getEncrypt(6,14);
      assertTrue(encrypt==5);

   }

   private int getEncrypt(int phi, int modulusKey)
   {
      for (int i = 2; i < phi; i++)
      {
         if (!(bigIntegerRelativelyPrime(i, phi) || bigIntegerRelativelyPrime(i, modulusKey)))
         {
          return i;  
         }
      }
      throw new RuntimeException("encrypt not found");

   }

   boolean bigIntegerRelativelyPrime(int a, int b)
   {
      return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).equals(BigInteger.ONE);
   }

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
