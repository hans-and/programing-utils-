package hasses.magical.xml.formatter;

import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class MagicalFileWalker
{

   private DirectoryWalkerParam settings;

   private FilenameFilter fileFilter;

   private PathHandler consumer;

   public MagicalFileWalker(DirectoryWalkerParam settings, FilenameFilter fileFilter, PathHandler consumer)
   {
      super();
      this.settings = settings;
      this.fileFilter = fileFilter;
      this.consumer = consumer;
   }

   public void excecute() throws IOException
   {
      
      try (Stream<Path> walk = Files.walk(Paths.get(settings.getDir()), settings.getRecursive()))
      {
         walk.filter(path -> fileFilter.accept(null, path.toFile().getName())).forEach(consumer::handle);
      }
   }

}
