package hasses.magical.tools.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileHelper
{
   private FileHelper() {
      
   }
   public static String readFileAsString(File file) throws IOException
   { 
     return new String(Files.readAllBytes(file.toPath()));  
   } 
}
