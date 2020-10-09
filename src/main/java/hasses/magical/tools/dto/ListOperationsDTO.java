package hasses.magical.tools.dto;

public class ListOperationsDTO {

	private String operation;
	private boolean ignoreCase;
	private boolean sort;
	private boolean trim;
	private String listA;
	private String listB;

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

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getListB() {
		return listB;
	}

	public String getOperation() {
		return operation;
	}

}
