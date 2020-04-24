package hasses.magical.tools.dto;

public class ListOperationsDTO {

	private boolean ignoreCase;

	private boolean trim;

	private boolean sort;

	private String listA;
	private String listB;
	private ListOperation operation;

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public boolean getTrim() {
		return trim;
	}

	public void setTrim(boolean trim) {
		this.trim = trim;
	}

	public boolean getSort() {
		return sort;
	}

	public void setSort(boolean sort) {
		this.sort = sort;
	}

	public String getListA() {
		return listA;
	}

	public void setListA(String listA) {
		this.listA = listA;
	}

	public void setListB(String listB) {
		this.listB = listB;
	}

	public void setOperation(ListOperation operation) {
		this.operation = operation;
	}

	public String getListB() {
		return listB;
	}

	public ListOperation getOperation() {
		return operation;
	}

}
