package hasses.magical.tools.file.pom;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.dom4j.DocumentHelper;
import org.dom4j.XPath;
import org.junit.jupiter.api.Test;


class PomReporterTest
{

   @Test
   void test()
   {

      try
      {
         XPath groupIdPath = DocumentHelper.createXPath("project/groupId");
         PomReporter.report("c:\\Users\\hans.andersson\\git","c:\\Users\\hans.andersson\\projectReport.csv");
         
      }
      catch (IOException e)
      {
        fail("Should not fail");
      }
   }

}
