package hasses.magical.xml.formatter;

public interface XmlFormatParam extends FilesFilterParam, DirectoryWalkerParam
{

   String getOutdir();

   boolean getVerbose();

   String toString();

}