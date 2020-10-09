package hasses.magical.xml.formatter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import static hasses.magical.xml.formatter.UIOut.LOG;

public class XMLFormatter
{

   private SAXReader reader;

   public XMLFormatter()
   {

      reader = new SAXReader();

   }

   public void transform(Path sourcePath, File file) throws IOException
   {
      LOG.logVerbose("Will write to file: {}", file);
      final Document document = parseXmlFile(sourcePath);

      if (document != null) transformDoc(document, file);

   }

   private void transformDoc(final Document document, File file) throws IOException
   {

      FileWriter writer = new FileWriter(file);
      OutputFormat opf = OutputFormat.createPrettyPrint();
      XMLWriter xmlWriter = new XMLWriter(writer, opf);
      xmlWriter.write(document);
      xmlWriter.close();

   }

   private Document parseXmlFile(Path in)
   {
      try
      {
         return reader.read(in.toFile());
      }
      catch (DocumentException e)
      {

         LOG.error("Unable to properly read  file:{}", in.toFile());
         if (LOG.isVerbose())
         {
            if (!in.toFile().canRead())
            {
               LOG.error("Unable to read");
            }
            LOG.error("Exeption during parse", e);
         }

         return null;
      }

   }

}
