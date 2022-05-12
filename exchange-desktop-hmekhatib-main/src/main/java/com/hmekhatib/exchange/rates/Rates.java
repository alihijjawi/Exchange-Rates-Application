package com.hmekhatib.exchange.rates;


import com.hmekhatib.exchange.Authentication;
import com.hmekhatib.exchange.api.ExchangeService;
import com.hmekhatib.exchange.api.model.ExchangeRates;
import com.hmekhatib.exchange.api.model.Transaction;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Rates {
    public Label buyUsdRateLabel;
    public Label sellUsdRateLabel;
    public TextField lbpTextField;
    public TextField usdTextField;
    public ToggleGroup transactionType;
    public Label calcLabel1;
    public Label calcLabel2;
    public TextField calcTextField1;
    public Label calcLabelValue;
    public ToggleGroup calcType;
    public RadioButton calcBuyUsd;
    public RadioButton calcSellUsd;
    public void initialize() {
        fetchRates();
    }
    private void fetchRates() {
        ExchangeService.exchangeApi().getExchangeRates().enqueue(new
     Callback<ExchangeRates>() {
         @Override
         public void onResponse(Call<ExchangeRates> call, Response<ExchangeRates> response) {
             ExchangeRates exchangeRates = response.body();
             Platform.runLater(() -> {

                 buyUsdRateLabel.setText(exchangeRates.lbpToUsd.toString());
                 sellUsdRateLabel.setText(exchangeRates.usdToLbp.toString());
                 calcSellUsd.setSelected(true);
             });
         }
         @Override
         public void onFailure(Call<ExchangeRates> call, Throwable
                 throwable) {
         }
     });
    }
    public void addTransaction(ActionEvent actionEvent) {
        Transaction transaction = new Transaction(
                Float.parseFloat(usdTextField.getText()),
                Float.parseFloat(lbpTextField.getText()),
                ((RadioButton)
                        transactionType.getSelectedToggle()).getText().equals("Sell USD")
        );
        String userToken = Authentication.getInstance().getToken();
        String authHeader = userToken != null ? "Bearer " + userToken : null;
        ExchangeService.exchangeApi().addTransaction(transaction,authHeader).enqueue(new
          Callback<Object>() {
              @Override
              public void onResponse(Call<Object> call, Response<Object>
                      response) {
                  fetchRates();
                  Platform.runLater(() -> {
                      usdTextField.setText("");
                      lbpTextField.setText("");
                  });
              }
              @Override
              public void onFailure(Call<Object> call, Throwable throwable)
              {
              }
          });
    }

    public void swap(ActionEvent actionEvent) {
        calcTextField1.setText("");
        calcLabelValue.setText("");
        if(calcSellUsd.isSelected()){
            calcLabel1.setText("USD Amount");
            calcLabel2.setText("LBP Amount");
        }else{
            calcLabel2.setText("USD Amount");
            calcLabel1.setText("LBP Amount");
        }
    }
    public void calculate(){
        if (sellUsdRateLabel.getText() == "" && buyUsdRateLabel.getText() == "") {
            return;
        }
        float amount_usd = Float.parseFloat(calcTextField1.getText());
        if(calcSellUsd.isSelected()){
            calcLabelValue.setText(Float.toString(amount_usd*Float.parseFloat(sellUsdRateLabel.getText())));
        } else if(calcBuyUsd.isSelected()){
            calcLabelValue.setText(Float.toString(amount_usd/Float.parseFloat(buyUsdRateLabel.getText())));
        }
    }



}