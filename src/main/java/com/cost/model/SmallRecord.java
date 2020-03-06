package com.cost.model;

import java.time.LocalDate;

import com.cost.utils.Categories;

public class SmallRecord {
	private Categories category;
	private double cost;
	
	public SmallRecord (Categories category, double cost) {
		this.category = category;
		this.cost = cost;
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

	@Override
	public String toString() {
		return "[" + category + ", " + cost + "]";
	}

}
