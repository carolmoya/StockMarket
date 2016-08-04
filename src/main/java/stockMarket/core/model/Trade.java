package stockMarket.core.model;

import java.util.Date;

public class Trade {

	private double quantity;
	private double price;
	private Date time;

	public Trade(Date time, double quantity, TradeOperation type, double price) {
		this.quantity = quantity;
		this.price = price;
		this.time = time;
	}

	public double getQuantity() {
		return this.quantity;
	}

	public double getPrice() {
		return this.price;
	}
	
	public Date getTime() {
		return this.time;
	}

}
