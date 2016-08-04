package stockMarket.core.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import stockMarket.core.exception.InvalidTradeException;
import stockMarket.core.exception.StockNotFoundException;
import stockMarket.core.model.Stock;
import stockMarket.core.model.StockMarket;
import stockMarket.core.model.TradeOperation;


public class StockMarketTest {

	@Test
	public void calculateGBCEAllShareIndex() {
		StockMarket stockMarket = new StockMarket();
		
		double[] volumeWeightedStockPriceArray = {5, 3, 8};
		List<Stock> stocks = Arrays.stream(volumeWeightedStockPriceArray)
				.mapToObj(p -> new StockMock(p)).collect(Collectors.toList());
		stockMarket.setStocks(stocks);
		Assert.assertEquals(Math.cbrt(Arrays.stream(volumeWeightedStockPriceArray).sum()),
				stockMarket.calculateGBCEAllShareIndex().get(), Double.MIN_VALUE);
		
		double[] volumeWeightedStockPriceArraySecondCase = {5, 3};
		stocks = Arrays.stream(volumeWeightedStockPriceArraySecondCase)
				.mapToObj(p -> new StockMock(p)).collect(Collectors.toList());
		stockMarket.setStocks(stocks);
		Assert.assertEquals(Math.sqrt(Arrays.stream(volumeWeightedStockPriceArraySecondCase).sum()),
				stockMarket.calculateGBCEAllShareIndex().get(), Double.MIN_VALUE);
	}
	
	@Test
	public void calculateGBCEAllShareIndexReturnsEmptyOptionalWhenTheresNoStocks() {
		StockMarket stockMarketWithEmptyList = new StockMarket();
		stockMarketWithEmptyList.setStocks(new ArrayList<Stock>());
		Assert.assertFalse(stockMarketWithEmptyList.calculateGBCEAllShareIndex().isPresent());
		Assert.assertFalse(new StockMarket().calculateGBCEAllShareIndex().isPresent());
	}
	
	@Test
	public void callRefferedStockToAddTrade() throws InvalidTradeException, StockNotFoundException {
		StockMarket stockMarket = new StockMarket();
		StockMock a = new StockMock("A");
		StockMock b = new StockMock("B");
		stockMarket.setStocks(Arrays.asList(new Stock[]{a,b}));
		stockMarket.addTradeToStock("A", new Date(), 0, TradeOperation.Buy, 0);
		Assert.assertEquals(1, a.getTradesRegistered());
		Assert.assertEquals(0, b.getTradesRegistered());
	}
	
	@Test
	public void throwsExceptionIfCantFindStockToAddTrade() throws InvalidTradeException {
		StockMarket stockMarket = new StockMarket();
		try {
			stockMarket.addTradeToStock("A", new Date(), 0, TradeOperation.Buy, 0);
			Assert.fail("Should throw exception if stock requested doesn't exist.");
		} catch (StockNotFoundException e) {
			Assert.assertEquals("Stock A was not found.", e.getMessage());
		}
	}
	
	private class StockMock extends Stock {

		private Double volumeWeightedStockPriceReturnValue;
		private int tradesRegistered = 0;

		public StockMock(double lastDividend, double parValue) {
			super("", lastDividend, parValue);
			// TODO Auto-generated constructor stub
		}
		
		public StockMock(String name) {
			super(name, 0, 0);
			// TODO Auto-generated constructor stub
		}

		public StockMock(Double volumeWeightedStockPriceReturnValue) {
			super("", 0, 0);
			this.volumeWeightedStockPriceReturnValue = volumeWeightedStockPriceReturnValue;
		}

		@Override
		public Optional<Double> calculateDividendYieldForPrice(double price) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Optional<Double> calculateVolumeWeightedStockPriceOnLastFiveMinutes() {
			return Optional.ofNullable(this.volumeWeightedStockPriceReturnValue);
		}
		
		@Override
		public void registerTrade(Date time, double quantity, TradeOperation tradeOperation, double price) {
			this.tradesRegistered++;
		}
		
		public int getTradesRegistered() {
			return this.tradesRegistered;
		}
	}
}
