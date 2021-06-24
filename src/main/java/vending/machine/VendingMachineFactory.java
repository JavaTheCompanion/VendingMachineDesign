package vending.machine;

public final class VendingMachineFactory {

	private VendingMachineFactory() {

	}

	public static VendingMachine createVendingMachine(final VendingMachineType type) {
		switch (type) {
		case DRINKS:
			return new DrinksVendingMachine();
		default:
			return null;
		}
	}

}
