package hasses.magical.tools.dto;

public class BusResultDTO {

	private String	avg1;
	private String	avg2;
	private String	avg3;
	
	public BusResultDTO() {
		super();
	}
	public BusResultDTO(String avg1, String avg2, String avg3) {
		super();
		this.avg1 = avg1;
		this.avg2 = avg2;
		this.avg3 = avg3;
	}
	public String getAvg1() {
		return avg1;
	}
	public void setAvg1(String avg1) {
		this.avg1 = avg1;
	}
	public String getAvg2() {
		return avg2;
	}
	public void setAvg2(String avg2) {
		this.avg2 = avg2;
	}
	public String getAvg3() {
		return avg3;
	}
	public void setAvg3(String avg3) {
		this.avg3 = avg3;
	}

}
