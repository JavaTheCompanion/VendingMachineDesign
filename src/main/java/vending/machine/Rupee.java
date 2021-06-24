package vending.machine;

public enum Rupee {

	FIVE(5), TEN(10), TWENTY(20), FIFTY(50);

	private int value;

	private Rupee(final int value) {
		this.value = value;
	}

	public final int getValue() {
		return this.value;
	}

}
