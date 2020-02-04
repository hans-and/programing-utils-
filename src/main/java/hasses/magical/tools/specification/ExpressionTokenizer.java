package hasses.magical.tools.specification;

public class ExpressionTokenizer
{
   public class tokens
   {
      private boolean isNot;

      private String attributePath;

      private String value;

      private String operator;
   }

   private static String MATCH_IS_GREATER_THAN = "^[\\d,A-Z,a-z,.,_]+\\s{0,5}[>]{1}\\s{0,5}[^><~=]{2}.+";

   private static String MATCH_IS_LESS_THAN = "^[\\d,A-Z,a-z,.,_]+\\s{0,5}[<]{1}\\s{0,5}[^><~=]{2}.+";

   private static String MATCH_EQUAL = "^[\\d,A-Z,a-z,.,_]+\\s{0,5}[=]{1}\\s{0,5}[^><~=]{2}.+";

   private static String MATCH_REGEX = "^[\\d,A-Z,a-z,.,_]+\\s{0,5}[~]{1}\\s{0,5}[^><~=]{2}.+";

   private static String MATCH_LIKE = "^[\\d,A-Z,a-z,.,_]+\\s{0,5}(\\sLIKE\\s){1}\\s{0,5}[^><~=]{2}.+";

}
