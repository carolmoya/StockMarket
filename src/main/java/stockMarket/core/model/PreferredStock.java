package stockMarket.core.model;

import java.util.Optional;

public class PreferredStock extends Stock {
	
	private Double fixedDividend;

	public PreferredStock(String name, double lastDividend, Double fixedDividend, double parValue) {
		super(name, lastDividend, parValue);
		this.fixedDividend = fixedDividend;
	}

	public Optional<Double> calculateDividendYieldForPrice(double price) {
		Double result = null;
		if (price != 0) {
			result  = this.fixedDividend * this.parValue / price;
		}
		return Optional.ofNullable(result);
		
	}

}
