package hasses.magical.tools.specification;

public interface CsvHeader {
	
	int columnIndex(String name);
	
	Object stringValueToValue(int index,String stringValue); 

}
