package vending.machine;

import java.util.HashMap;
import java.util.Map;

public class Inventory<T> {

	private Map<T, Integer> inventory = new HashMap<>();

	public final void addCount(final T item) {
		this.inventory.putIfAbsent(item, 0);
		this.inventory.put(item, inventory.get(item) + 1);
	}

	public final int getCount(final T item) {
		return this.inventory.getOrDefault(item, 0);
	}

	public final boolean hasItem(final T item) {
		return this.getCount(item) > 0;
	}

	public final void deductCount(final T item) {
		if (this.hasItem(item)) {
			this.inventory.put(item, inventory.get(item) - 1);
		}
	}

	public final void reset() {
		this.inventory.clear();
	}

	public void add(final T item, final Integer count) {
		this.inventory.put(item, count);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Inventory [inventory=");
		builder.append(this.inventory);
		builder.append("]");
		return builder.toString();
	}

}
