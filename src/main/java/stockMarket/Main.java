package stockMarket;

import java.util.Arrays;
import java.util.Date;

import stockMarket.core.exception.InvalidTradeException;
import stockMarket.core.exception.StockNotFoundException;
import stockMarket.core.model.CommonStock;
import stockMarket.core.model.PreferredStock;
import stockMarket.core.model.Stock;
import stockMarket.core.model.StockMarket;
import stockMarket.core.model.TradeOperation;

public class Main {

	public static void main(String[] args) throws InvalidTradeException, StockNotFoundException {
		StockMarket stockMarket = new StockMarket();
		Stock[] stocks = {
				new CommonStock("TEA", 0, 100), 
				new CommonStock("POP", 8, 100), 
				new CommonStock("ALE", 23, 60),
				new PreferredStock("GIN", 8, 0.2, 100), 
				new CommonStock("JOES", 13, 250)};
		stockMarket.setStocks(Arrays.asList(stocks));
		stockMarket.addTradeToStock("TEA", new Date(), 5, TradeOperation.Buy, 100);
	}

}
