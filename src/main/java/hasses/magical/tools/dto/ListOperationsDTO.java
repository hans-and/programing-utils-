package hasses.magical.tools.dto;

public class ListOperationsDTO {
	
	public Boolean getIgnoreCase() {
		return ignoreCase;
	}

	public void setIgnoreCase(Boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public Boolean getTrim() {
		return trim;
	}

	public void setTrim(Boolean trim) {
		this.trim = trim;
	}

	public Boolean getSort() {
		return sort;
	}

	public void setSort(Boolean sort) {
		this.sort = sort;
	}

	private Boolean ignoreCase;
	
	private Boolean trim;
	
	private Boolean sort;
	
	private String listA;
	private String listB;
	private String operation;

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
