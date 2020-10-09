package hasses.magical.xml.formatter;

import static hasses.magical.xml.formatter.UIOut.LOG;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class FormattingController implements PathHandler
{
   private MagicalFileWalker fileWalker = null;

   private XMLFormatter formatter = null;

   private DestinationHandler fileout = null;

   private boolean initialized;
   public FormattingController(XmlFormatParam settings)
   {
      super();
      LOG.setVerbose(settings.getVerbose());
      if(LOG.isVerbose())LOG.log("I'm feeling talkative");
      this.settings = settings;
      initialized = (initFileSave(settings) && initFileWalking(settings)&&initXmlFormating());
   }

   private boolean initXmlFormating()
   {
      boolean result = true;
      try
      {
         formatter = new XMLFormatter();
      }
      catch (Exception e)
      {
         LOG.error("Unable to create Transformer will Exit");
         if (LOG.isVerbose()) LOG.error("TransformerConfigurationException:", e);
         result = false;
      }
      
      return result;
   }

   private boolean initFileWalking(XmlFormatParam settings)
   {
      try
      {
         fileWalker = new MagicalFileWalker(settings, new MagicFileNameFilter(settings), this);
         return true;
      }
      catch (Exception e)
      {
         LOG.error("Unable read files check your file patterns and or directory rights");
         if (LOG.isVerbose()) LOG.error("Cause:", e);
         return false;
      }
   }

   private boolean initFileSave(XmlFormatParam settings)
   {
      try
      {
         fileout = new DestinationHandler(settings.getOutdir());
         return true;
      }
      catch (Exception e)
      {
         LOG.error("Could not initiate save");
         if (LOG.isVerbose()) LOG.error("Cause:", e);
         return false;

      }
   }

   private XmlFormatParam settings;

   public Integer excecute()
   {
      if (!initialized)
      {
         return -99;
      }

      LOG.logAccordingly("Formatting start", "Will begin formatting with the following {}", settings);

      try
      {
         fileWalker.excecute();
         return 0;
      }
      catch (IOException e)
      {
         LOG.error("IOException during traverse dir: {} please check this a valid directory", settings.getDir());

         return -99;
      }

   }

   

   @Override
   public void handle(Path path)
   {
      File destination;
      try
      {
         destination = fileout.getDestination(path);
      }
      catch (IOException e1)
      {
         LOG.error("File error Unable to write result");
         if (LOG.isVerbose())
         {
            LOG.error("Exeption during formating", e1);
         }
         return;
      }
      LOG.logVerbose("Will attempt to format: {}", path);
      
      try 
      {
         formatter.transform(path,destination);         
      }      
      catch (IOException e)
      {
         LOG.error("File error"+path);
         if (LOG.isVerbose())
         {
            LOG.error("Exeption during formating", e);
         }
      }

   }

}
