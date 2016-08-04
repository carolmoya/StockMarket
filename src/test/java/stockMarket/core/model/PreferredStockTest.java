package stockMarket.core.model;

import org.junit.Assert;
import org.junit.Test;

import stockMarket.core.model.PreferredStock;
import stockMarket.core.model.Stock;

public class PreferredStockTest {

	@Test
	public void stockCalculatesItsDividentYieldForGivenPrice() {
		double fixedDividend = 0.02;
		double parValue = 100;
		double[] prices = {10D, 20D, Double.MIN_VALUE};
		Stock stock = new PreferredStock("", 0, fixedDividend, parValue);
		
		for (double price : prices) {
			Assert.assertEquals(fixedDividend * parValue / price, stock.calculateDividendYieldForPrice(price).get(), Double.MIN_VALUE);
		}
	}
	
	@Test
	public void calculateDividendYieldReturnsEmptyOptionalWhenPriceIsZero() {
		double fixedDividend = 0.02;
		double parValue = 100;
		double price = 0;
		PreferredStock stock = new PreferredStock("", 0, fixedDividend, parValue);
		Assert.assertFalse(stock.calculateDividendYieldForPrice(price).isPresent());
	}

}
