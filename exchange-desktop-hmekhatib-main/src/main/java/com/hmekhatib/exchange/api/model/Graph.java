package com.hmekhatib.exchange.api.model;

import com.google.gson.annotations.SerializedName;
import javafx.scene.chart.NumberAxis;

import java.util.List;

public class Graph {

        @SerializedName("x")
        public List<String> xaxis;
        @SerializedName("buy")
        public List<Float> yAxisB;
        @SerializedName("sell")
        public List<Float> yAxisS;


}
