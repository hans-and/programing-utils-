package hasses.magical.tools.string;

public class StringLib {
	
   private StringLib() {}
   
  
   
   private static String SPLIT_ROW_REGEX = "\\r?\\n";

	public static boolean isNullOrEmpty(String str) {
		if (str != null && !str.isEmpty())
			return false;
		return true;
	}

	/**
	 * @param source
	 * @return splits both Windows and Unix style line break, NOTE that null source is empty array!
	 */
	public static String[] splitInRows(String source) {
		String[] result;
		if (source == null) {
			result = new String[0];
		} else if (source.isEmpty()) {
			result = new String[1];
			result[0] = "";
		} else {
			result = source.split(SPLIT_ROW_REGEX);
		}
		return result;
	}
}
