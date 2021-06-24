package vending.machine;

import java.util.ArrayList;
import java.util.List;

public class DrinksVendingMachine implements VendingMachine {

	private Inventory<Item> itemInventory = new Inventory<>();
	private Inventory<Rupee> cashInventory = new Inventory<>();

	private long totalSales;

	private Item currentItem;
	private long currentBalance;

	public DrinksVendingMachine() {
		this.initializeInventory();
	}

	private void initializeInventory() {
		for (final Item item : Item.values()) {
			itemInventory.add(item, 5);
		}

		for (final Rupee rupee : Rupee.values()) {
			cashInventory.add(rupee, 5);
		}
	}

	@Override
	public long selectItemAndGetPrice(final Item item) {
		if (this.itemInventory.hasItem(item)) {
			this.currentItem = item;
			return item.getPrice();
		}
		throw new SoldOutException("Oops !!!" + item + " Sold Out, Please buy another Drink...");
	}

	@Override
	public void insertRupee(final Rupee rupee) {
		this.currentBalance = this.currentBalance + rupee.getValue();
		this.cashInventory.addCount(rupee);
	}

	@Override
	public List<Rupee> refund() {
		final List<Rupee> changeList = this.getChange(this.currentBalance);
		this.deductCashInventory(changeList);

		this.currentBalance = 0;
		this.currentItem = null;

		return changeList;
	}

	@Override
	public Bucket<Item, List<Rupee>> collectItemAndChange() {
		final Item item = this.collectItem();
		this.totalSales = this.totalSales + this.currentItem.getPrice();

		final List<Rupee> changeList = this.collectChange();

		return new Bucket<>(item, changeList);
	}

	@Override
	public void reset() {
		this.cashInventory.clear();
		this.itemInventory.clear();

		this.totalSales = 0;

		this.currentBalance = 0;
		this.currentItem = null;
	}

	private Item collectItem() throws NotSufficientChangeException, NotFullyPaidException {
		if (this.isFullyPaid()) {
			if (this.hasSufficientChange()) {
				this.itemInventory.deductCount(this.currentItem);
				return this.currentItem;
			} else {
				throw new NotSufficientChangeException("Sorry !!! Not Sufficient Change in the CashInventory...");
			}
		} else {
			final long remainingBalance = this.currentItem.getPrice() - currentBalance;
			throw new NotFullyPaidException("You haven't paid teh full Amount. Remaining : ", remainingBalance);
		}
	}

	public List<Rupee> collectChange() {
		final long changeAmount = this.currentBalance - this.currentItem.getPrice();
		final List<Rupee> changeList = this.getChange(changeAmount);
		this.deductCashInventory(changeList);

		this.currentBalance = 0;
		this.currentItem = null;

		return changeList;
	}

	private boolean isFullyPaid() {
		return this.currentBalance >= this.currentItem.getPrice();
	}

	private boolean hasSufficientChange() {
		return this.hasSufficientChangeForAmount(this.currentBalance - this.currentItem.getPrice());
	}

	private boolean hasSufficientChangeForAmount(final long amount) {
		boolean hasChange = true;

		try {
			this.getChange(amount);
		} catch (final NotSufficientChangeException ex) {
			hasChange = false;
		}

		return hasChange;
	}

	private List<Rupee> getChange(final long amount) throws NotSufficientChangeException {
		final List<Rupee> changeList = new ArrayList<>();
		long balance = amount;

		if (balance > 0) {
			while (balance > 0) {
				if (balance >= Rupee.FIFTY.getValue() && this.cashInventory.hasItem(Rupee.FIFTY)) {
					changeList.add(Rupee.FIFTY);
					balance = balance - Rupee.FIFTY.getValue();
					continue;
				} else if (balance >= Rupee.TWENTY.getValue() && this.cashInventory.hasItem(Rupee.TWENTY)) {
					changeList.add(Rupee.TWENTY);
					balance = balance - Rupee.TWENTY.getValue();
					continue;
				} else if (balance >= Rupee.TEN.getValue() && this.cashInventory.hasItem(Rupee.TEN)) {
					changeList.add(Rupee.TEN);
					balance = balance - Rupee.TEN.getValue();
					continue;
				} else if (balance >= Rupee.FIVE.getValue() && this.cashInventory.hasItem(Rupee.FIVE)) {
					changeList.add(Rupee.FIVE);
					balance = balance - Rupee.FIVE.getValue();
					continue;
				} else {
					throw new NotSufficientChangeException("NotSufficientChange, Please try another Drink...");
				}
			}
		}

		return changeList;
	}

	public void printStats() {
		System.out.println("Total Sales : " + this.totalSales);
		System.out.println("Current Item Inventory : " + this.itemInventory);
		System.out.println("Current Cash Inventory : " + this.cashInventory);
	}

	private void deductCashInventory(final List<Rupee> changeList) {
		for (final Rupee rupee : changeList) {
			this.cashInventory.deductCount(rupee);
		}
	}

	public final long getTotalSales() {
		return this.totalSales;
	}

}
