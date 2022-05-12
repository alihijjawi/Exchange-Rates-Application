package com.hmekhatib.exchange.api.model;

import com.google.gson.annotations.SerializedName;

public class Stat {
    @SerializedName("1h")
    public Float OneHChange;
    @SerializedName("12h")
    public Float TwelveHChange;
    @SerializedName("24h")
    public Float TwentyFourHChange;
    @SerializedName("7d")
    public Float SevenDChange;
    @SerializedName("max")
    public Float maxRate;
    @SerializedName("min")
    public Float minRate;

    public Float getOneHChange() {
        return OneHChange;
    }

    public Float getTwelveHChange() {
        return TwelveHChange;
    }

    public Float getTwentyFourHChange() {
        return TwentyFourHChange;
    }

    public Float getSevenDChange() {
        return SevenDChange;
    }

    public Float getMaxRate() {
        return maxRate;
    }

    public Float getMinRate() {
        return minRate;
    }
}
