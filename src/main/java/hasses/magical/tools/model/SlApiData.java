package hasses.magical.tools.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import javax.persistence.Id;

@Entity
@Table(name = "slapidata")
public class SlApiData {

	public SlApiData() {
		super();
	}

	public SlApiData(Date lastSLApiCall, BigInteger numberOfCallsThisMonth, BigInteger totalNoOfCalls) {
		super();
		this.lastSLApiCall = lastSLApiCall;
		this.numberOfCallsThisMonth = numberOfCallsThisMonth;
		this.totalNoOfCalls = totalNoOfCalls;
	}
	
	 
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "lastslapi_call")
	private Date lastSLApiCall;
	@Column(name = "number_of_calls_this_month")
	private BigInteger numberOfCallsThisMonth;
	@Column(name = "total_no_of_calls")
	private BigInteger totalNoOfCalls;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lastSLApiCall == null) ? 0 : lastSLApiCall.hashCode());
		result = prime * result + ((numberOfCallsThisMonth == null) ? 0 : numberOfCallsThisMonth.hashCode());
		result = prime * result + ((totalNoOfCalls == null) ? 0 : totalNoOfCalls.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SlApiData other = (SlApiData) obj;
		if (lastSLApiCall == null) {
			if (other.lastSLApiCall != null)
				return false;
		} else if (!lastSLApiCall.equals(other.lastSLApiCall))
			return false;
		if (numberOfCallsThisMonth == null) {
			if (other.numberOfCallsThisMonth != null)
				return false;
		} else if (!numberOfCallsThisMonth.equals(other.numberOfCallsThisMonth))
			return false;
		if (totalNoOfCalls == null) {
			if (other.totalNoOfCalls != null)
				return false;
		} else if (!totalNoOfCalls.equals(other.totalNoOfCalls))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SlApiData [lastSLApiCall=" + lastSLApiCall + ", numberOfCallsThisMonth=" + numberOfCallsThisMonth
				+ ", totalNoOfCalls=" + totalNoOfCalls + "]";
	}

	public Date getLastSLApiCall() {
		return lastSLApiCall;
	}

	public void setLastSLApiCall(Date lastSLApiCall) {
		this.lastSLApiCall = lastSLApiCall;
	}

	public BigInteger getNumberOfCallsThisMonth() {
		return numberOfCallsThisMonth;
	}

	public void setNumberOfCallsThisMonth(BigInteger numberOfCallsThisMonth) {
		this.numberOfCallsThisMonth = numberOfCallsThisMonth;
	}

	public BigInteger getTotalNoOfCalls() {
		return totalNoOfCalls;
	}

	public void setTotalNoOfCalls(BigInteger totalNoOfCalls) {
		this.totalNoOfCalls = totalNoOfCalls;
	}
}
