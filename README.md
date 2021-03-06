This application represents the core model of a simple stock market application.
As a first step I imagined a Stock class, which would contain the Stock data, and also calculate things regarding its own data.

As there is one formula that is different depending on the type of stock it is, I thought of two main options, the first one was having two strategy classes, each implementing a formula, but that wasn't good enough, as there is a field that is only used for one of these formulas and could also be isolated (fixed dividend). So I opted for making Stock an abstract class, and having two subclasses of it, CommonStock and PreferredStock, the second one with the fixed dividend field.

As a second step I implemented the calculation for Volume Weighted Stock Price based on trades in past 5 minutes. To represent the data needed for this calculation I created the class Trade. This class is instantiated and kept inside the Stock, so the details of how trades are handled are isolated there.

At last I added the StockMarket class, that is responsible for keeping the existent Stocks, and running calculations that would include all of them, and also delegating any external requests to the Stocks.

This application uses Java 8 and JUnit(jars included in /src/externalLibraries).