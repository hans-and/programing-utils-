package hasses.magical.xml.formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum UIOut {
   LOG;

   private boolean verbose;

   public boolean isVerbose()
   {
      return verbose;
   }

   void setVerbose(boolean verbose)
   {
      this.verbose = verbose;
   }

   private static final Logger log = LoggerFactory.getLogger(UIOut.class);

   public void logVerbose(String format, Object... arguments)
   {
      if (verbose) log.info(format, arguments);
   }

   public void logAccordingly(String shortMess, String format, Object... arguments)
   {
      if (verbose)
      {
         log.info(format, arguments);
      }
      else
      {
         log.info(shortMess);
      }
   }

   public void log(String format, Object... arguments)
   {
      log.info(format, arguments);
   }

   public void error(String format, Object... arguments)
   {
      log.error(format, arguments);
   }
   
   public void error(String msg, Throwable e)
   { 
      log.error(msg, e);
   }

}
