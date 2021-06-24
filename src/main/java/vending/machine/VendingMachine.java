package vending.machine;

import java.util.List;

public interface VendingMachine {

	long selectItemAndGetPrice(Item item);

	void insertRupee(Rupee rupee);

	List<Rupee> refund();

	Bucket<Item, List<Rupee>> collectItemAndChange();

	void reset();

}
