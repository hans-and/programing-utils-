package hasses.magical.xml.formatter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DestinationHandler
{

   public static final String OVER_WRITE = "over write";

   private Path outDir;

   public DestinationHandler(String pOutdir) throws IOException
   {
      if (!OVER_WRITE.equals(pOutdir))
      {

         outDir = Files.createDirectories(Paths.get(pOutdir));
         if (!Files.isWritable(outDir))
         {
            throw new IOException("Unable to write to out dir path: " + pOutdir);
         }

      }
      else
      {
         outDir = null;
      }

   }

   public File getDestination(Path path) throws IOException
   {
      return (outDir == null) ? path.toFile() : getOutDirFile(path, 0);
   }

   private File getOutDirFile(Path path, int i) throws IOException
   {

      String filename = getFileName(path, i);
      File result = Paths.get(outDir.toString(), filename).toFile();
      if (result.exists())
      {
         return getOutDirFile(path, i + 1);
      }
      else
      {
         if(result.createNewFile()) {
            return result;   
         }else {
            throw new IOException("Unable to create file: "+result);
         }
         
      }
   }

   public static final String getFileName(Path path, int i)
   {
      String filename = path.getFileName().toString();

      return (i == 0) ? filename : getFileName(filename, i);
   }

   public static final String getFileName(String filename, int i)
   {

      List<String> strings = new ArrayList<>(Arrays.asList(filename.split("\\.")));
      String first = strings.remove(0);
      strings.add(0, first + "(" + i + ")");
      return String.join(".", strings);

   }

}
