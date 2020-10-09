package hasses.magical.xml.formatter;

import java.io.File;
import java.io.FilenameFilter;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 * Will either work as filter that accepts one or more wild-card file patterns with one or more exceptions or using regular expressions. This can either be case or case
 * insensitive. Since there are a regex option one could argue that there is no need for exceptions or add-ons or cases. However I'm sure some people will find this useful and
 * simpler to use.
 * 
 * @author hans.andersson
 */
public class MagicFileNameFilter implements FilenameFilter
{
   private FilenameFilter includes = null;

   private FilenameFilter includesPlus = null;

   private FilenameFilter excludes = getAcceptNone();

   public MagicFileNameFilter(FilesFilterParam param)
   {
      if (param.getRegex())
      {
         initRegex(param.getPatterns(), param.getPatternsPlus(), param.getExcludePatterns(), param.getCaseSensivity());
      }
      else initWildCard(param.getPatterns(), param.getPatternsPlus(), param.getExcludePatterns(), param.getCaseSensivity());

   }

   private void initWildCard(String patterns, String patternsPlusPattern, String excludePatterns, IOCase ioCase)
   {
      includes = new WildcardFileFilter(patterns.split("\\|"), ioCase);
      includesPlus = (patternsPlusPattern.isEmpty()) ? getAcceptNone() : new WildcardFileFilter(patternsPlusPattern.split("\\|"), ioCase);
      excludes = (excludePatterns.isEmpty()) ? getAcceptNone() : new WildcardFileFilter(excludePatterns.split("\\|"), ioCase);
   }

   private void initRegex(String patterns, String patternsPlusPattern, String excludePatterns, IOCase ioCase)
   {
      includes = new RegexFileFilter(patterns, ioCase);
      includesPlus = (patternsPlusPattern.isEmpty()) ? getAcceptNone() : new RegexFileFilter(patternsPlusPattern, ioCase);
      excludes = (excludePatterns.isEmpty()) ? getAcceptNone() : new RegexFileFilter(excludePatterns, ioCase);
   }

   @Override
   public boolean accept(File dir, String name)
   {
      return notExcluded(name) && accepted(name);
   }

   private boolean accepted(String name)
   {
      return includesPlus.accept(null, name) || includes.accept(null, name);
   }

   private boolean notExcluded(String name)
   {
      return !excludes.accept(null, name);
   }

   /**
    * @return returns a lambda implementation that always return false
    */
   private FilenameFilter getAcceptNone()
   {
      return (dir, name) -> false;
   }
}
