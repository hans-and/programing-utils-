package hasses.magical.tools.beans.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

class ListOperationsControllerTest
{
   private Random r = new Random();

   static final int[] PRIMES = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137,
            139, 149, 151, 157, 163, 167, 173};

   private Point publicKey = new Point();
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
      
      encrypt = getEncrypt(phi,modulusKey);
      publicKey.y = encrypt;
      
      int decrypt = getDecrypt(phi);
      
      BigInteger encryptedNumber = getEncryptNumber();
      System.out.println("modulusKey="+modulusKey);
      
      System.out.println("ncryptedNumber="+encryptedNumber);
      System.out.println("encrypt="+encrypt);
      System.out.println("decrypt="+decrypt);
      
      BigInteger powerOfDecrypt = encryptedNumber.pow(decrypt);
      
      
      BigInteger decrypted = powerOfDecrypt.mod(BigInteger.valueOf(publicKey.x));//  encryptedNumber.modPow(BigInteger.valueOf(decrypt), ) ;// new BigInteger(encryptedNumber); //Math.pow(encryptedNumber, decrypt); 
      
      //double decrypted = power2 % publicKey.x;
      System.out.println("decrypted="+decrypted);
   }

   private int getDecrypt(int phi)
   {
      int i=1;
      while (true) {
         i++;
         if((i%phi)==1) {
            return i;
         }
      }
   }
   
   private int getEncrypt(int phi, int modulusKey)
   {
    //  List<Integer> encrypts = new ArrayList<>();
      for (int i = 2; i < phi; i++)
      {
         if ((bigIntegerRelativelyPrime(i, phi) && bigIntegerRelativelyPrime(i, modulusKey)))
         {
            return i;
          
         }
      }
      throw new RuntimeException();
      //return encrypts.get(r.nextInt(encrypts.size())).intValue();
      
   }

   private BigInteger getEncryptNumber() {
      int secretNumer = 4;
      BigInteger powerOfEncrypt = BigInteger.valueOf(secretNumer).pow(publicKey.y);
      
      return  powerOfEncrypt.mod(BigInteger.valueOf(publicKey.x));//     modPow(BigInteger.valueOf(publicKey.y), );
//      double power = Math.pow(secretNumer, publicKey.y); 
  //    return power%publicKey.x;
   }
   
   boolean bigIntegerRelativelyPrime(int a, int b)
   {
      return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).equals(BigInteger.ONE);
   }

}
