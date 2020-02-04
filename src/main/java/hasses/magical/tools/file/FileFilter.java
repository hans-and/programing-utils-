package hasses.magical.tools.file;

import java.nio.file.Path;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.google.common.base.Predicate;

public class FileFilter
{
   private FileFilter()
   {

   }

   public static Predicate<Path> matches(String regex)
   {
      return new Predicate<Path>()
      {
         @Override
         public boolean apply(@Nullable Path input)
         {
            return input != null && input.getFileName().toString().matches(regex);
         }
      };
   }
   
   public static Predicate<Path> equals(String pattern)
   {
      return new Predicate<Path>()
      {
         @Override
         public boolean apply(@Nullable Path input)
         {
            return input != null && input.getFileName().toString().equals(pattern);
         }
      };
   }

   public static Predicate<Path> notMatching(String regex)
   {
      return new Predicate<Path>()
      {
         @Override
         public boolean apply(@Nullable Path input)
         {
            return input != null && !input.getFileName().toString().matches(regex);
         }
      };
   }
   
   

}
