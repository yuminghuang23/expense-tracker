package com.cost.model;

import java.time.LocalDate;

import com.cost.utils.Categories;


public class MediumRecord {
	private Categories category;
	private double cost;
	private LocalDate recordDate;
	
	public MediumRecord (Categories category, double cost, LocalDate recordDate) {
		this.category = category;
		this.cost = cost;
		this.recordDate = recordDate;
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public LocalDate getRecordDate() {
		return recordDate;
	}

	@Override
	public String toString() {
		return "[" + category + ", " + cost + ", " + recordDate + "]";
	}

	public void setRecordDate(LocalDate recordDate) {
		this.recordDate = recordDate;
	}
	
}
