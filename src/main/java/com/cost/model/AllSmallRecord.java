package com.cost.model;

import com.cost.utils.Categories;

public class AllSmallRecord {
	private Categories category;
	private double cost;
	private int userId;
	
	public AllSmallRecord(Categories category, double cost, int userId) {
		super();
		this.category = category;
		this.cost = cost;
		this.userId = userId;
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "[" + category + ", " + cost + ", " + userId + "]";
	}
}
