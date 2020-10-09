package hasses.magical.xml.formatter;

import java.util.concurrent.Callable;

import org.apache.commons.io.IOCase;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Main class of a small program that: format xml files
 */
@Command(name = "xml formatter", mixinStandardHelpOptions = true, version = "checksum 4.0", description = "format")
public class MainXmlFormatter implements Callable<Integer>, XmlFormatParam
{

   

   public static final String NOT_AVAILIBLE = "N/A";

   @Override
   public String getDir()
   {
      return dir;
   }

   @Override
   public String getOutdir()
   {
      return outdir;
   }

   @Override
   public boolean getVerbose()
   {
      return verbose != null;
   }

   @Override
   public String toString()
   {
      return "formatting settings [Dir:" + getDir() + ", Outdir:" + getOutdir() + ", Verbose:" + getVerbose() + ", Recursive:" + getRecursive() + ", Patterns:" + getPatterns()
               + ", Patterns Plus:" + getPatternsPlus() + ", Exclude Patterns:" + getExcludePatterns() + ", is Regex mode:" + getRegex() + "]";
   }

   @Override
   public int getRecursive()
   {
      return (recursive == null)?1:Integer.MAX_VALUE;
   }

   @Override
   public String getPatterns()
   {
      return patterns;
   }

   @Override
   public String getPatternsPlus()
   {
      return patternsPlus;
   }

   @Override
   public String getExcludePatterns()
   {
      return excludePatterns;
   }

   @Override
   public boolean getRegex()
   {
      return regex != null;
   }

   @Option(names = { "-d", "--dir"}, description = "source/s directory ex: c:\\temp\\ default uses current dir")
   private String dir = NOT_AVAILIBLE;

   @Option(names = { "-o", "--out"}, description = "out dir ex: c:\\temp\\out\\ if not specified files are ower written")
   private String outdir = DestinationHandler.OVER_WRITE;

   @Option(names = { "-v", "--verbose"}, description = "Verbose mode. Helpful for troubleshooting. Will report")
   private boolean[] verbose;

   @Option(names = { "-r", "--recursive"}, description = "If files in sub directorys should be formatted as well")
   private boolean[] recursive;

   @Option(names = { "-p", "--pattern"}, description = "default is *.xml|*.xsd|*.wsdl|*.xslt")
   private String patterns = "*.xml|*.xsd|*.wsdl|*.xslt";

   @Option(names = { "-pp", "--pattern plus"}, description = "add pattern (to default)")
   private String patternsPlus = "";

   @Option(names = { "-ep", "--exclude pattern"}, description = "excludes file regardless of matchin any other pattern")
   private String excludePatterns = "";

   @Option(names = { "-regex",
            "--regular expression"}, description = "all patterns are treated as one regular expression (Normally * is just a wild card and pipe treated as separator)")
   private boolean[] regex;

   @Option(names = { "-cs",
            "--case sensitive"}, description = "if you want the patterns to be sensitive to upper and lower Casing ")
   private boolean[] caseSensitive;

   @Override
   public IOCase getCaseSensivity()
   {      
      return (caseSensitive == null)?IOCase.INSENSITIVE:IOCase.SENSITIVE;
   }

   public static void main(String[] args)
   {
      int exitCode = new CommandLine(new MainXmlFormatter()).execute(args);
      System.exit(exitCode);
   }

   @Override
   public Integer call() throws Exception
   {
      if (NOT_AVAILIBLE.equals(dir))
      {
         dir = System.getProperty("user.dir");
      }
      
      return new FormattingController(this).excecute();
   }

}
