package com.cost.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Table;

@Entity
@Table(name = "monthly_spending_mv")
@IdClass(MonthlySumId.class)
//@NamedStoredProcedureQuery(name = "refresh_mv_now", procedureName = "refresh_mv_now")
public class MonthlySum implements Serializable {
	@Id
	@Column(name = "month_year")
	private String monthYear;
	
	@Id
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "monthly_sum")
	private double monthlySum;
	
	public MonthlySum() {}
	
	public MonthlySum(String monthYear, int userId, double monthlySum) {
		super();
		this.userId = userId;
		this.monthYear = monthYear;
		this.monthlySum = monthlySum;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	public double getMonthlySum() {
		return monthlySum;
	}
	public void setMonthlySum(double monthlySum) {
		this.monthlySum = monthlySum;
	}

	@Override
	public String toString() {
		return "MonthlySum [" + userId + monthYear + monthlySum + "]";
	}
	
}
