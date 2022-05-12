package com.hmekhatib.exchange.insights;

import com.hmekhatib.exchange.api.ExchangeService;
import com.hmekhatib.exchange.api.model.Graph;
import com.hmekhatib.exchange.api.model.Stat;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class Insights {

    @FXML
    CategoryAxis xAxis;
    @FXML
    NumberAxis yAxis;
    @FXML
    LineChart<String, Float> lc;

    @FXML
    Label oneHChange;
    @FXML
    Label tHChange;
    @FXML
    Label thHChange;
    @FXML
    Label sDChange;
    @FXML
    Label minRate;
    @FXML
    Label maxRate;
    @FXML
    Label oneHChangeB;
    @FXML
    Label tHChangeB;
    @FXML
    Label thHChangeB;
    @FXML
    Label sDChangeB;
    @FXML
    Label minRateB;
    @FXML
    Label maxRateB;
    public XYChart.Series seriesB = new XYChart.Series();
    public XYChart.Series seriesS = new XYChart.Series();
    public void initialize() {
        getStatsS();
        getStatsB();
        SetupGraph();
    }

    public void SetupGraph() {

        ExchangeService.exchangeApi().getGraph().enqueue(new
        Callback<Graph>() {
            @Override
            public void onResponse(Call<Graph> call, Response<Graph> response) {
                Graph graph = response.body();
                Platform.runLater(() -> {
                    List<String> dates = graph.xaxis;
                    List<Float> ratesB = graph.yAxisB;
                    List<Float> ratesS = graph.yAxisS;


                    for (int i = 0; i < dates.size(); i ++) {
                        seriesB.getData().add(new XYChart.Data(dates.get(i), ratesB.get(i)));
                        seriesS.getData().add(new XYChart.Data(dates.get(i), ratesS.get(i)));
                    }
                    xAxis.setLabel("Dates");
                    yAxis.setLabel("Rates * 10^-3");
                    seriesB.setName("Buy");
                    seriesS.setName("Sell");
                    lc.getData().add(seriesB);
                    lc.getData().add(seriesS);

                });
            }

            @Override
            public void onFailure(Call<Graph> call, Throwable
                    throwable) {
            }
        });

    }

    private void getStatsS() {
        ExchangeService.exchangeApi().getStatBuy().enqueue(new
        Callback<Stat>() {
            @Override
            public void onResponse(Call<Stat> call, Response<Stat> response) {
                Stat stats = response.body();
                Platform.runLater(() -> {

                    oneHChangeB.setText(stats.OneHChange.toString());
                    tHChangeB.setText(stats.TwelveHChange.toString());
                    thHChangeB.setText(stats.TwentyFourHChange.toString());
                    sDChangeB.setText(stats.TwentyFourHChange.toString());
                    maxRateB.setText(stats.maxRate.toString());
                    minRateB.setText(stats.minRate.toString());

                });
            }

            @Override
            public void onFailure(Call<Stat> call, Throwable
                    throwable) {
            }
        });
    }

    private void getStatsB() {
        ExchangeService.exchangeApi().getStatSell().enqueue(new
        Callback<Stat>() {
            @Override
            public void onResponse(Call<Stat> call, Response<Stat> response) {
                Stat stats = response.body();
                Platform.runLater(() -> {
                    oneHChange.setText(stats.OneHChange.toString());
                    tHChange.setText(stats.TwelveHChange.toString());
                    thHChange.setText(stats.TwentyFourHChange.toString());
                    sDChange.setText(stats.TwentyFourHChange.toString());
                    maxRate.setText(stats.maxRate.toString());
                    minRate.setText(stats.minRate.toString());

                });
            }

            @Override
            public void onFailure(Call<Stat> call, Throwable
                    throwable) {
            }
        });
    }


}




