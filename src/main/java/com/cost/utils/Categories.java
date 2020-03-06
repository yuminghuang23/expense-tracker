package com.cost.utils;

public enum Categories {
	CAR("CAR"),
	CLOTHING("CLOTHING"),
	EATING_OUT("EATING_OUT"),
	ENTERTAINMENT("ENTERTAINMENT"),
	GROCERIES("GROCERIES"),
	HOBBIES("HOBBIES"),
	HOLIDAY("HOLIDAY"),
	OTHER("OTHER"),
	PARKING("PARKING"),
	PHONE("PHONE"),
	PUBLIC_TRANSPORT("PUBLIC_TRANSPORT"),
	RENT("RENT"),
	SHOES("SHOES"),
	SPORTING("SPORTING"),
	UTILITY("UTILITY"),
	WORK_LUNCH("WORK_LUNCH");

	private String value;

	Categories(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
    public String toString() {
          return value;
    }
}
