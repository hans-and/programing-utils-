package hasses.magical.xml.formatter;

import org.apache.commons.io.IOCase;
import org.junit.Test;

import junit.framework.TestCase;

public class MagicFileNameFilterTest extends TestCase
{
   @Test
   public void testAcceptSimpleWildCard()
   {
      MagicFileNameFilter ff = new MagicFileNameFilter(new FilesFilterParam()
      {

         @Override
         public boolean getRegex()
         {

            return false;
         }

         @Override
         public String getPatternsPlus()
         {
            return "";
         }

         @Override
         public String getPatterns()
         {

            return "*.txt";
         }

         @Override
         public String getExcludePatterns()
         {

            return "";
         }

         @Override
         public IOCase getCaseSensivity()
         {

            return IOCase.INSENSITIVE;
         }
      });

      assertTrue(ff.accept(null, "hej.Txt"));
      assertTrue(ff.accept(null, "hej.TXT"));
      assertTrue(ff.accept(null, "hej.txt"));
      assertTrue(ff.accept(null, ".txt"));
      assertTrue(ff.accept(null, "hej.xml.txt"));
      assertFalse(ff.accept(null, "hej.xml"));
      assertFalse(ff.accept(null, "hej.txt.xml"));

   }

   @Test
   public void testAcceptSimpleWildCard2()
   {
      MagicFileNameFilter ff = new MagicFileNameFilter(new FilesFilterParam()
      {

         @Override
         public boolean getRegex()
         {

            return false;
         }

         @Override
         public String getPatternsPlus()
         {
            return "";
         }

         @Override
         public String getPatterns()
         {

            return "hasse*2020.txt";
         }

         @Override
         public String getExcludePatterns()
         {

            return "";
         }

         @Override
         public IOCase getCaseSensivity()
         {

            return IOCase.INSENSITIVE;
         }
      });

      assertTrue(ff.accept(null, "Hassehej2020.Txt"));

      assertTrue(ff.accept(null, "hassehej.xml.2020.txt"));
      assertFalse(ff.accept(null, "hasse.xml.txt"));
      assertFalse(ff.accept(null, "hej.txt.2020.txt"));

   }

   @Test
   public void testAcceptPipeWildCard()
   {
      MagicFileNameFilter ff = new MagicFileNameFilter(new FilesFilterParam()
      {

         @Override
         public boolean getRegex()
         {

            return false;
         }

         @Override
         public String getPatternsPlus()
         {
            return "";
         }

         @Override
         public String getPatterns()
         {

            return "*.txt|*.dat";
         }

         @Override
         public String getExcludePatterns()
         {

            return "";
         }

         @Override
         public IOCase getCaseSensivity()
         {

            return IOCase.INSENSITIVE;
         }
      });

      assertTrue(ff.accept(null, "Hassehej2020.Txt"));

      assertTrue(ff.accept(null, "hassehej.xml.2020.dat"));
      assertFalse(ff.accept(null, "hasse.xml"));
      assertFalse(ff.accept(null, "hej.txt.2020.txt.csv"));

   }

   @Test
   public void testAcceptPipeWildCardPlus()
   {
      MagicFileNameFilter ff = new MagicFileNameFilter(new FilesFilterParam()
      {

         @Override
         public boolean getRegex()
         {

            return false;
         }

         @Override
         public String getPatternsPlus()
         {
            return "*.xml";
         }

         @Override
         public String getPatterns()
         {

            return "*.txt|*.dat";
         }

         @Override
         public String getExcludePatterns()
         {

            return "";
         }

         @Override
         public IOCase getCaseSensivity()
         {

            return IOCase.INSENSITIVE;
         }
      });

      assertTrue(ff.accept(null, "Hassehej2020.Txt"));

      assertTrue(ff.accept(null, "hassehej.xml.2020.dat"));
      assertTrue(ff.accept(null, "hasse.xml"));
      assertFalse(ff.accept(null, "hej.txt.2020.txt.csv"));

   }

   @Test
   public void testAcceptPipeWildCardPlusRejectHasse()
   {
      MagicFileNameFilter ff = new MagicFileNameFilter(new FilesFilterParam()
      {

         @Override
         public boolean getRegex()
         {

            return false;
         }

         @Override
         public String getPatternsPlus()
         {
            return "*.xml";
         }

         @Override
         public String getPatterns()
         {

            return "*.txt|*.dat";
         }

         @Override
         public String getExcludePatterns()
         {

            return "*hasse*.*";
         }

         @Override
         public IOCase getCaseSensivity()
         {

            return IOCase.INSENSITIVE;
         }
      });

      assertFalse(ff.accept(null, "Hassehej2020.Txt"));

      assertFalse(ff.accept(null, "hassehej.xml.2020.dat"));
      assertFalse(ff.accept(null, "Plumpenhasse.xml"));
      assertFalse(ff.accept(null, "hej.txt.2020.txt.csv"));
      assertTrue(ff.accept(null, "hej.txt.2020.txt.csv.dat"));

   }

   @Test
   public void testAcceptPipeWildCardPlusRejectHasseCaseSensitive()
   {
      MagicFileNameFilter ff = new MagicFileNameFilter(new FilesFilterParam()
      {

         @Override
         public boolean getRegex()
         {

            return false;
         }

         @Override
         public String getPatternsPlus()
         {
            return "*.xml";
         }

         @Override
         public String getPatterns()
         {

            return "*.txt|*.dat";
         }

         @Override
         public String getExcludePatterns()
         {

            return "*hasse*.*";
         }

         @Override
         public IOCase getCaseSensivity()
         {

            return IOCase.SENSITIVE;
         }
      });

      assertTrue(ff.accept(null, "Hassehej2020.txt"));

      assertFalse(ff.accept(null, "Hassehej.xml.2020.Dat"));
      assertFalse(ff.accept(null, "Plumpenhasse.xml"));
      assertFalse(ff.accept(null, "hej.txt.2020.txT"));
      assertTrue(ff.accept(null, "hej.txt.2020.txt.csv.dat"));

   }

   @Test
   public void testSimpleRegex()
   {
      MagicFileNameFilter ff = new MagicFileNameFilter(new FilesFilterParam()
      {

         @Override
         public boolean getRegex()
         {

            return true;
         }

         @Override
         public String getPatternsPlus()
         {
            return "";
         }

         @Override
         public String getPatterns()
         {

            return ".*hasse.*";
         }

         @Override
         public String getExcludePatterns()
         {

            return "";
         }

         @Override
         public IOCase getCaseSensivity()
         {

            return IOCase.SENSITIVE;
         }
      });

      assertTrue(ff.accept(null, "hassehej2020.txt"));
      assertFalse(ff.accept(null, "Hassehej.xml.2020.Dat"));
      assertFalse(ff.accept(null, "PlumpenhaSse.xml"));
      assertFalse(ff.accept(null, "hej.txt.2020.txT"));
      assertTrue(ff.accept(null, "hej.hassetxt.2020.txt.csv.dat"));

   }

   @Test
   public void testSimpleRegex2()
   {
      MagicFileNameFilter ff = new MagicFileNameFilter(new FilesFilterParam()
      {

         @Override
         public boolean getRegex()
         {

            return true;
         }

         @Override
         public String getPatternsPlus()
         {
            return "";
         }

         @Override
         public String getPatterns()
         {

            return ".*hasse.*";
         }

         @Override
         public String getExcludePatterns()
         {

            return "";
         }

         @Override
         public IOCase getCaseSensivity()
         {

            return IOCase.INSENSITIVE;
         }
      });

      assertTrue(ff.accept(null, "hassehej2020.txt"));
      assertTrue(ff.accept(null, "Hassehej.xml.2020.Dat"));
      assertTrue(ff.accept(null, "PlumpenhaSse.xml"));
      assertFalse(ff.accept(null, "hej.txt.2020.txT"));
      assertTrue(ff.accept(null, "hej.hassetxt.2020.txt.csv.dat"));

   }
   
   @Test
   public void testRegexExclusion()
   {
      MagicFileNameFilter ff = new MagicFileNameFilter(new FilesFilterParam()
      {

         @Override
         public boolean getRegex()
         {

            return true;
         }

         @Override
         public String getPatternsPlus()
         {
            return "";
         }

         @Override
         public String getPatterns()
         {

            return ".*hasse.*";
         }

         @Override
         public String getExcludePatterns()
         {

            return ".*hassehasse.*";
         }

         @Override
         public IOCase getCaseSensivity()
         {

            return IOCase.INSENSITIVE;
         }
      });

      assertTrue(ff.accept(null, "hassehej2020.txt"));
      assertFalse(ff.accept(null, "HasseHasse.xml.2020.Dat"));
      assertTrue(ff.accept(null, "PlumpenhaSse.xml"));
      assertFalse(ff.accept(null, "hej.txt.2020.txT"));
      assertTrue(ff.accept(null, "hej.hassetxt.hasse2020.txt.csv.dat"));

   }

}
