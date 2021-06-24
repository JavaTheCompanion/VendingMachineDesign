package vending.machine;

public enum Item {

	COKE("Coke", 25), PEPSI("Pepsi", 35), SODA("Soda", 45);

	private String name;
	private int price;

	private Item(final String name, final int price) {
		this.name = name;
		this.price = price;
	}

	public final String getName() {
		return this.name;
	}

	public final int getPrice() {
		return this.price;
	}

}
