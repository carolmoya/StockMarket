package stockMarket.core.model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import stockMarket.core.exception.InvalidTradeException;
import stockMarket.core.exception.StockNotFoundException;

public class StockMarket {
	
	private List<Stock> stocks;

	public Optional<Double> calculateGBCEAllShareIndex() {
		Double result = null;
		if(stocks != null && !stocks.isEmpty()) {
			double sum = stocks.stream().map(s -> s.calculateVolumeWeightedStockPriceOnLastFiveMinutes())
					.filter(Optional::isPresent).mapToDouble(Optional::get).sum();
			int root = stocks.size();
			result = Math.pow(sum, 1D / root);
		}
		return Optional.ofNullable(result);
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public void addTradeToStock(String name, Date time, double quantity, TradeOperation type, double price) throws InvalidTradeException, StockNotFoundException {
		Optional<Stock> stock = Optional.empty();
		if (this.stocks != null) {
			stock = stocks.stream().filter(s -> s.getName().equals(name)).findFirst();
		}
		if (stock.isPresent()) {
			stock.get().registerTrade(time, quantity, type, price);
		}
		else {
			throw new StockNotFoundException("Stock " + name + " was not found.");
		}
	}
	

}
