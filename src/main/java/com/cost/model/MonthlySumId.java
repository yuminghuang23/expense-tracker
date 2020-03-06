package com.cost.model;

import java.io.Serializable;

public class MonthlySumId implements Serializable{
	private int userId;
	private String monthYear;
	
	public MonthlySumId() {}

	public MonthlySumId(int userId, String month_year) {
		super();
		this.userId = userId;
		this.monthYear = month_year;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getMonth_year() {
		return monthYear;
	}
	public void setMonth_year(String month_year) {
		this.monthYear = month_year;
	}
	
	@Override
	public String toString() {
		return "MonthlySumId [userId=" + userId + ", month_year=" + monthYear + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((monthYear == null) ? 0 : monthYear.hashCode());
		result = prime * result + userId;
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
		MonthlySumId other = (MonthlySumId) obj;
		if (monthYear == null) {
			if (other.monthYear != null)
				return false;
		} else if (!monthYear.equals(other.monthYear))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
}
