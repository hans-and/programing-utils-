package hasses.magical.tools.file.pom;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.XPath;

import com.opencsv.CSVWriter;

import hasses.magical.common.file.FileFilter;
import hasses.magical.helpers.XMLHelper;

public class PomReporter
{
   private static class XPaths
   {
      XPath groupIdPath;

      XPath artifactIdPath;

      XPath versionPath;

      XPath namePath;

      XPaths()
      {

         Map<String, String> nameSpaces = XMLHelper.tuplesToMap("p=http://maven.apache.org/POM/4.0.0");
         groupIdPath = XMLHelper.createXpath("p:project/p:groupId/text()", nameSpaces);
         artifactIdPath = XMLHelper.createXpath("p:project/p:artifactId/text()", nameSpaces);
         versionPath = XMLHelper.createXpath("p:project/p:version/text()", nameSpaces);
         namePath = XMLHelper.createXpath("p:project/p:name/text()", nameSpaces);
      }
   }

   private PomReporter()
   {

   }

   public static void report(String path, String destination) throws IOException
   {
      XPaths paths = new XPaths();

      try (Stream<Path> pathStream = Files.walk(Paths.get(path));
               Writer writer = Files.newBufferedWriter(Paths.get(destination));
               CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);

      )
      {
         initHeader(csvWriter);

         pathStream.filter(FileFilter.equals("pom.xml")).forEach(pompath -> handleFile(pompath, paths, csvWriter));
      }

   }

   private static void initHeader(CSVWriter csvWriter)
   {
      String[] headerRecord = { "ArtifactId", "Version", "Project Name", "GroupId", "Directory (repository)", "pom.xml"};
      csvWriter.writeNext(headerRecord);
   }

   private static void handleFile(Path path, XPaths paths, CSVWriter csvWriter)
   {
      try
      {

         Document pom = XMLHelper.parse(path);
         csvWriter.writeNext(new String[] { XMLHelper.xpathStringValue(pom, paths.artifactIdPath), XMLHelper.xpathStringValue(pom, paths.versionPath),
                  XMLHelper.xpathStringValue(pom, paths.namePath), XMLHelper.xpathStringValue(pom, paths.groupIdPath), getNearestChildFolder(path, "git"),
                  path.toFile().getAbsoluteFile().toString()});

      }
      catch (

      DocumentException e)
      {

         e.printStackTrace();
      }

   }

   private static String getNearestChildFolder(Path path, String parent)
   {
      boolean foundParent = false;
      for (Path supPath : path)
      {
         if (foundParent) return "" + supPath;
         foundParent = (parent.equals("" + supPath));
      }
      return "";
   }
}
