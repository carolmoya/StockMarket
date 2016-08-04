package stockMarket.core.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import stockMarket.core.exception.InvalidTradeException;

public abstract class Stock {
	
	protected double lastDividend;
	protected double parValue;
	private List<Trade> trades = new ArrayList<Trade>();
	private String name;
	
	public abstract Optional<Double> calculateDividendYieldForPrice(double price);

	public Stock(String name, double lastDividend, double parValue) {
		this.lastDividend = lastDividend;
		this.parValue = parValue;
		this.name = name;
	}
	
	public Optional<Double> calculatePERAtioForPrice(double price) {
		Double result = null;
		if (this.lastDividend != 0) {
			result = price/this.lastDividend;
		}
		return Optional.ofNullable(result);
	}
	
	public Optional<Double> calculateVolumeWeightedStockPriceOnLastFiveMinutes() {
		Double result = null;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -5);
		List<Trade> relevantTrades = this.getTradesSince(calendar.getTime());
		if (!relevantTrades.isEmpty()) {
			double dividend = 0;
			double divisor = 0;
			Trade trade;
			for (int i = 0; i < relevantTrades.size(); i++) {
				trade = relevantTrades.get(i);
				dividend += trade.getQuantity() * trade.getPrice();
				divisor += trade.getQuantity();
			}
			result = dividend / divisor; 
		}
		return Optional.ofNullable(result);
	}

	private List<Trade> getTradesSince(Date time) {
		return trades.stream().filter(t -> t.getTime().getTime() >= time.getTime())
				.collect(Collectors.toList());
	}
	
	public void registerTrade(Date time, double quantity, TradeOperation tradeOperation, double price) throws InvalidTradeException {
		if (quantity <= 0 || price <= 0) {
			throw new InvalidTradeException("Trade quantity and price have to be greater than zero.");
		}
		if (time.after(new Date())) {
			throw new InvalidTradeException("A trade can't be registered in the future.");
		}
		this.trades.add(new Trade(time, quantity, tradeOperation, price));
	}

	public String getName() {
		return name;
	}
}

