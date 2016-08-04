package stockMarket.core.model;

import java.util.Optional;

public class CommonStock extends Stock {

	public CommonStock(String name, double lastDividend, double parValue) {
		super(name, lastDividend, parValue);
	}

	public Optional<Double> calculateDividendYieldForPrice(double price) {
		Double result = null;
		if (price != 0) {
			result = this.lastDividend /price;
		}
		return Optional.ofNullable(result);
		
	}

}
