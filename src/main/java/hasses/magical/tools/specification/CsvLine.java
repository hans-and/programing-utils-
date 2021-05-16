package hasses.magical.tools.specification;

public class CsvLine {

	private String[] columns;

	private CsvHeader header;

	public CsvLine(String data, CsvHeader header) {
		if (data == null || data.isBlank()) {
			columns = new String[0];
		} else {
			columns = data.split(";");
		}

		this.header = header;
	}

	public Object getAttributeValue(String attribute) {
		int index = header.columnIndex(attribute);
		if (columns.length > index) {
			return header.stringValueToValue(index, columns[index]);
		} else {
			return "N/A";
		}
	}

}
