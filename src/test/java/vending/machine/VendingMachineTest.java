package vending.machine;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class VendingMachineTest {

	private VendingMachine vendingMachine;

	@BeforeEach
	public void setUp() {
		vendingMachine = VendingMachineFactory.createVendingMachine(VendingMachineType.DRINKS);
	}

	@AfterEach
	public void tearDown() {
		vendingMachine = null;
	}

	@Test
	public void testBuyItemWithExactPrice() {
		final long price = vendingMachine.selectItemAndGetPrice(Item.COKE);

		Assertions.assertEquals(Item.COKE.getPrice(), price);

		vendingMachine.insertRupee(Rupee.TWENTY);
		vendingMachine.insertRupee(Rupee.FIVE);

		final Bucket<Item, List<Rupee>> bucket = vendingMachine.collectItemAndChange();
		final Item item = bucket.getFirst();
		final List<Rupee> change = bucket.getSecond();

		Assertions.assertEquals(Item.COKE, item);

		Assertions.assertTrue(change.isEmpty());
	}

	@Test
	public void testBuyItemWithMorePrice() {
		final long price = vendingMachine.selectItemAndGetPrice(Item.SODA);
		Assertions.assertEquals(Item.SODA.getPrice(), price);

		vendingMachine.insertRupee(Rupee.FIFTY);

		final Bucket<Item, List<Rupee>> bucket = vendingMachine.collectItemAndChange();
		final Item item = bucket.getFirst();
		final List<Rupee> change = bucket.getSecond();

		Assertions.assertEquals(Item.SODA, item);

		Assertions.assertTrue(!change.isEmpty());

		Assertions.assertEquals(50 - Item.SODA.getPrice(), change.stream().mapToInt(Rupee::getValue).sum());
	}

	@Test
	public void testRefund() {
		final long price = vendingMachine.selectItemAndGetPrice(Item.PEPSI);
		Assertions.assertEquals(Item.PEPSI.getPrice(), price);

		vendingMachine.insertRupee(Rupee.TWENTY);
		vendingMachine.insertRupee(Rupee.TWENTY);

		final List<Rupee> changeList = vendingMachine.refund();

		Assertions.assertEquals(40, changeList.stream().mapToInt(Rupee::getValue).sum());
	}

	@Test
	public void testSoldOut() {

		Assertions.assertThrows(SoldOutException.class, () -> {
			for (int i = 0; i < 6; i++) {
				vendingMachine.selectItemAndGetPrice(Item.COKE);
				vendingMachine.insertRupee(Rupee.TWENTY);
				vendingMachine.insertRupee(Rupee.FIVE);
				vendingMachine.collectItemAndChange();
			}
		});

	}

	@Test
	public void testNotSufficientChangeException() {
		Assertions.assertThrows(NotSufficientChangeException.class, () -> {
			for (int i = 0; i < 5; i++) {
				vendingMachine.selectItemAndGetPrice(Item.SODA);
				vendingMachine.insertRupee(Rupee.FIFTY);
				vendingMachine.collectItemAndChange();

				vendingMachine.selectItemAndGetPrice(Item.PEPSI);
				vendingMachine.insertRupee(Rupee.FIFTY);
				vendingMachine.collectItemAndChange();
			}
		});
	}

	@Test
	public void testReset() {
		Assertions.assertThrows(SoldOutException.class, () -> {
			vendingMachine.reset();
			vendingMachine.selectItemAndGetPrice(Item.COKE);
		});
	}

	@Disabled
	@Test
	public void testVendingMachineImpl() {
		final VendingMachine vm = new DrinksVendingMachine();
	}

}
