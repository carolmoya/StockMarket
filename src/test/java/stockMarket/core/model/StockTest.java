package stockMarket.core.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import stockMarket.core.exception.InvalidTradeException;
import stockMarket.core.model.Stock;
import stockMarket.core.model.TradeOperation;

public class StockTest {
	
	@Test
	public void calculatePERatioForGivenPrice() {
		double[] lastDividends = {8, 90, Double.MIN_VALUE};
		double price = 7;
		for (double lastDividend : lastDividends) {
			Stock stock = new StockMock(lastDividend, 0);
			Assert.assertEquals(price / lastDividend , stock.calculatePERAtioForPrice(price).get(), Double.MIN_VALUE);
		}
	}

	@Test
	public void calculatePERatioWithZeroAsLastDividendReturnsEmptyOptional() {
		double lastDividend = 0;
		Stock stock = new StockMock(lastDividend, 0);
		double price = 7;
		Assert.assertFalse(stock.calculatePERAtioForPrice(price).isPresent());
	}
	
	@Test
	public void calculateVolumeWeightedStockPriceBasedOnLastFiveMinutes() throws InvalidTradeException {
		Stock stock = new StockMock(0, 0);
		double firstTradeQuantity = 20;
		double firstTradePrice = 100;
		stock.registerTrade(new Date(), firstTradeQuantity, TradeOperation.Buy, firstTradePrice);
		double secondTradeQuantity = 10;
		double secondTradePrice = 80;
		stock.registerTrade(new Date(), secondTradeQuantity, TradeOperation.Sell, secondTradePrice);
		double thirdTradeQuantity = 1;
		double thirdTradePrice = 80000;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -5);
		calendar.add(Calendar.MILLISECOND, -1);
		stock.registerTrade(calendar.getTime(), thirdTradeQuantity, TradeOperation.Buy, thirdTradePrice);
		double expected = (firstTradeQuantity * firstTradePrice + secondTradeQuantity * secondTradePrice) /
				(firstTradeQuantity + secondTradeQuantity);
		Assert.assertEquals(expected, stock.calculateVolumeWeightedStockPriceOnLastFiveMinutes().get(), Double.MIN_VALUE);
	}
	
	@Test
	public void calculateVolumeWeightedStockPriceReturnsEmptyOptionalWhenTheresNoTrade() {
		Stock stock = new StockMock(0, 0);
		Assert.assertFalse(stock.calculateVolumeWeightedStockPriceOnLastFiveMinutes().isPresent());
	}
	
	@Test
	public void registerTradeThrowsExceptionForZeroOrNegativeAsQuantityOrPrice() {
		Stock stock = new StockMock(0, 0);
		double[][] quantityAndPrice = {{0,0},{0,1},{1,0},{-1,1},{1,-1}};
		for (double[] line : quantityAndPrice) {
			try {
				stock.registerTrade(new Date(), line[0], TradeOperation.Buy, line[1]);
				Assert.fail("Shouldn't accept trade with invalid quantity or price");
			}
			catch (InvalidTradeException e) {
				Assert.assertEquals("Trade quantity and price have to be greater than zero.", e.getMessage());
			}
		}
	}
	
	@Test
	public void registerTradeThrowsExceptionForFutureDates() {
		Stock stock = new StockMock(0, 0);
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, 5);
			stock.registerTrade(calendar.getTime(), 5, TradeOperation.Buy, 6);
			Assert.fail("Shouldn't accept trade with invalid date.");
		}
		catch (InvalidTradeException e) {
			Assert.assertEquals("A trade can't be registered in the future.", e.getMessage());
		}
	}
	
	private class StockMock extends Stock {

		public StockMock(double lastDividend, double parValue) {
			super("", lastDividend, parValue);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Optional<Double> calculateDividendYieldForPrice(double price) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
