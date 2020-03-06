package com.cost.utils;

public enum Roles {
	ADMIN(1), USER(2);
	private int value;

	Roles(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
