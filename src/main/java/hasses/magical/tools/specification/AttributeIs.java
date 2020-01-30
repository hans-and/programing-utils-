package hasses.magical.tools.specification;

import java.text.ParseException;

public interface AttributeIs
{
   public boolean is(Object attributeCandidate, String pattern) throws ParseException;
}
