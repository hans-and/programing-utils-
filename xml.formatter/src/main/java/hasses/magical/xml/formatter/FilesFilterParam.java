package hasses.magical.xml.formatter;

import org.apache.commons.io.IOCase;

public interface FilesFilterParam
{

   String getPatterns();

   String getPatternsPlus();

   String getExcludePatterns();
   
   IOCase getCaseSensivity();

   boolean getRegex();

}