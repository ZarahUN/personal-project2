package common;

public enum Months {
	JANUARY ("January",0),
	FEBRUARY ("February",1),
	MARCH ("March",2),
	APRIL ("April",3),
	MAY ("May",4),
	JUNE ("June",5),
	JULY ("July",6),
	AUGUST ("August",7),
	SEPTEMBER ("September",8),
	OCTOBER ("October",9),
	NOVEMBER ("November",10),
	DECEMBER ("December",11);
	
	private final String name;
	private final int value;
	
	public String Name () { return this.name; }
	public int Value () { return this.value; }
	public int Value (String month) {
		if (month.equalsIgnoreCase(JANUARY.name)) {
			return JANUARY.value;
		}
		if (month.equalsIgnoreCase(FEBRUARY.name)) {
			return FEBRUARY.value;
		}
		if (month.equalsIgnoreCase(MARCH.name)) {
			return MARCH.value;
		}
		if (month.equalsIgnoreCase(APRIL.name)) {
			return APRIL.value;
		}
		if (month.equalsIgnoreCase(MAY.name)) {
			return MAY.value;
		}
		if (month.equalsIgnoreCase(JUNE.name)) {
			return JUNE.value;
		}
		if (month.equalsIgnoreCase(JULY.name)) {
			return JULY.value;
		}
		if (month.equalsIgnoreCase(AUGUST.name)) {
			return AUGUST.value;
		}
		if (month.equalsIgnoreCase(SEPTEMBER.name)) {
			return SEPTEMBER.value;
		}
		if (month.equalsIgnoreCase(OCTOBER.name)) {
			return OCTOBER.value;
		}
		if (month.equalsIgnoreCase(NOVEMBER.name)) {
			return NOVEMBER.value;
		}
		if (month.equalsIgnoreCase(DECEMBER.name)) {
			return DECEMBER.value;
		}
		return -1;
	}
	
	private Months(String name, int value) {
		this.name = name;
		this.value = value;
	}
}
