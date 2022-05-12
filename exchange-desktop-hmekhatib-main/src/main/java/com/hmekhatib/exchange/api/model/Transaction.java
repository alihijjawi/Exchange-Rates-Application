package com.hmekhatib.exchange.api.model;

import com.google.gson.annotations.SerializedName;

public class Transaction {
    @SerializedName("id")
    Integer id;
    @SerializedName("usd_amount")
    Float usdAmount;
    @SerializedName("lbp_amount")
    Float lbpAmount;
    @SerializedName("usd_to_lbp")
    Boolean usdToLbp;
    @SerializedName("added_date")
    String addedDate;

    public Integer getId() {
        return id;
    }

    public Float getUsdAmount() {
        return usdAmount;
    }

    public Float getLbpAmount() {
        return lbpAmount;
    }

    public Boolean getUsdToLbp() {
        return usdToLbp;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public Transaction(Float usdAmount, Float lbpAmount, Boolean usdToLbp)
    {
        this.usdAmount = usdAmount;
        this.lbpAmount = lbpAmount;
        this.usdToLbp = usdToLbp;
    }
}