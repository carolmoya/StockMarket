package stockMarket.core.model;

import org.junit.Assert;
import org.junit.Test;

import stockMarket.core.model.CommonStock;

public class CommonStockTest {
	
	@Test
	public void stockCalculatesItsDividentYieldForGivenPrice() {
		double lastDividend = 8;
		double[] prices = {10D, 20D, Double.MIN_VALUE};
		CommonStock stock = new CommonStock("", lastDividend, 0);
		
		for (double price : prices) {
			Assert.assertEquals(lastDividend / price, stock.calculateDividendYieldForPrice(price).get(), Double.MIN_VALUE);
		}
	}
	
	@Test
	public void calculateDividendYieldReturnsEmptyOptionalWhenPriceIsZero() {
		double lastDividend = 8;
		double price = 0;
		CommonStock stock = new CommonStock("", lastDividend, 0);
		Assert.assertFalse(stock.calculateDividendYieldForPrice(price).isPresent());
	}

}
