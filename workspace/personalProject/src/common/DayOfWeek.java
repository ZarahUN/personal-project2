package common;

public enum DayOfWeek {
	SUNDAY ("Sunday",1),
	MONDAY ("Monday",2),
	TUESDAY ("Tuesday",3),
	WEDNESDAY ("Wednesday",4),
	THURSDAY ("Thursday",5),
	FRIDAY ("Friday",6),
	SATURDAY ("Saturday",7);
	
	private final String name;
	private final int value;
	
	public String Name () { return this.name; }
	public int Value () { return this.value; }
	public int Value (String day) {
		if (day.equalsIgnoreCase(SUNDAY.name)) {
			return SUNDAY.value;
		}
		if (day.equalsIgnoreCase(MONDAY.name)) {
			return MONDAY.value;
		}
		if (day.equalsIgnoreCase(TUESDAY.name)) {
			return TUESDAY.value;
		}
		if (day.equalsIgnoreCase(WEDNESDAY.name)) {
			return WEDNESDAY.value;
		}
		if (day.equalsIgnoreCase(THURSDAY.name)) {
			return THURSDAY.value;
		}
		if (day.equalsIgnoreCase(FRIDAY.name)) {
			return FRIDAY.value;
		}
		if (day.equalsIgnoreCase(SATURDAY.name)) {
			return SATURDAY.value;
		}
		return -1;
	}
	
	private DayOfWeek(String name, int value) {
		this.name = name;
		this.value = value;
	}
}
