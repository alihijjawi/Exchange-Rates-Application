# Exchange-Rates-Application

EECE 430L project on the three following platforms: React, Desktop and Android  
Developed by:
- Bernard Challita
- Hasan Khatib
- Issam Misto
- Ali Hijjawi




## Functional Documentation
We list below the features our platforms support

### User Accounts
The person using our app is able to create an account for himself by providing a username and a password. This allows him later on to see all the transactions he has made over time.

### Transactions
A person (logged in/not logged in) can add a new transaction he has made. He needs to provide the usd amount, lbp amount as well as the type of transaction (BUY/SELL usd). This data is fed into the database and is used later on to calculate the exchange rates.

### Exchange rates
The user has the option to view today's exchange rate for both selling and buying USD.

### Statistics
The user has the option to view a graph showing how the exchange rate for both type of transactions vary over time. Additionaly, he can view the percentage in changes for the last 1hour, 12hours, 24hours and 7days. He can also view the maximum value the rates for both types of transactions have reached.

### Posts
A logged in user has the option to post a specific transactions. He choses the type of transaction, the amount to sell and what he excpects to gain in return. Once another user accepts this trade, this trade is fed into our databases and is used to calculate a new exchange rate.
### Exchange rate calculator
A person has the option to calculate how much a specific amount of currency is worth in another currency, based on the current exchange rate. (We only support usd to lbp and lbp to usd rates)




## Architecture
Below is a simple diagram showing the main architecture we adopted for this project.

