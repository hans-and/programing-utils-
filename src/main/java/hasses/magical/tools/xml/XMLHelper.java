package hasses.magical.tools.xml;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

public class XMLHelper
{
   public static Document parse(URL url) throws DocumentException
   {
      SAXReader reader = new SAXReader();
      return reader.read(url);
   }

   public static Document parse(Path path) throws DocumentException
   {
      return parse(path.toFile());
   }

   public static Document parse(File path) throws DocumentException
   {
      SAXReader reader = new SAXReader();
      return reader.read(path);
   }
   
   public static Document parse(String path) throws DocumentException
   {
      return parse(new File(path));
   }
   
   public static String xpathStringValue(Document source,XPath xPath) throws DocumentException
   {
      return xPath.valueOf(source);
   }
   
   public static String xpathStringValue(Document source,String xPathEx, Map<String,String> nameSpaces) throws DocumentException
   {
      XPath xPath = createXpath(xPathEx, nameSpaces);
      
      return xpathStringValue(source,xPath);
   }

   public static XPath createXpath(String xPathEx, Map<String, String> nameSpaces)
   {
      XPath xPath;
      try
      {
         xPath = DocumentHelper.createXPath(xPathEx);   
      }
      catch (Exception e)
      {
         System.out.println(e);
         return null;
      }
      
      
      if(nameSpaces!=null) {
         xPath.setNamespaceURIs(nameSpaces);   
      }
      return xPath;
   }
   
   
   
   public String xpathStringValue(String xPathEx,Document source, String... nameSpaces) throws DocumentException
   {  
      Map<String, String> nameSpaceMap = (nameSpaces!=null&&nameSpaces.length>0) ?  tuplesToMap(nameSpaces): null;
      return xpathStringValue(source,xPathEx, nameSpaceMap);
   }
   
   public static String xpathStringValue(String xPathEx,Path source, String... nameSpaces) throws DocumentException
   {  
      Map<String, String> nameSpaceMap = (nameSpaces!=null&&nameSpaces.length>0) ?  tuplesToMap(nameSpaces): null;
      return xpathStringValue(parse(source),xPathEx, nameSpaceMap);
   }
   
   

   public static Map<String, String> tuplesToMap(String... nameSpaces)
   {
      Map<String,String> nameSpaceMap = new HashMap<>();
      for (String namespaceTuple : nameSpaces)
      {
         String[] aliasNameSpace= namespaceTuple.split("=");
         nameSpaceMap.put(aliasNameSpace[0], aliasNameSpace[1]);
      }
      return nameSpaceMap;
   }

}
