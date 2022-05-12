module com.hmekhatib.exchange {
    requires javafx.controls;
    requires javafx.fxml;
    requires retrofit2;
    requires java.sql;
    requires gson;
    requires retrofit2.converter.gson;
    requires java.prefs;
    opens com.hmekhatib.exchange to javafx.fxml;
    opens com.hmekhatib.exchange.api.model to javafx.base, gson;
    exports com.hmekhatib.exchange;
    exports com.hmekhatib.exchange.rates;
    opens com.hmekhatib.exchange.rates to javafx.fxml;
    opens com.hmekhatib.exchange.login to javafx.fxml;
    opens com.hmekhatib.exchange.register to javafx.fxml;
    opens com.hmekhatib.exchange.insights to javafx.fxml;
    opens com.hmekhatib.exchange.trade_forum to javafx.fxml;
    opens com.hmekhatib.exchange.transactions to javafx.fxml;
}