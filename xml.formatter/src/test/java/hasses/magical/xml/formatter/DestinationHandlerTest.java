package hasses.magical.xml.formatter;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;

public class DestinationHandlerTest
{

   @Test
   public void test() throws IOException
   {

      assertEquals("apa(1)",DestinationHandler.getFileName("apa", 1) );
      
      assertEquals("apa(1).txt",DestinationHandler.getFileName("apa.txt", 1) );
      
      assertEquals("apa(1).txt.bak",DestinationHandler.getFileName("apa.txt.bak", 1));
      
      assertEquals("apa(1)",DestinationHandler.getFileName(Paths.get("temp/apa") , 1));

      assertEquals("apa(1).txt",DestinationHandler.getFileName(Paths.get("user/local/temp/apa.txt"), 1) );
      
      
   }

}
