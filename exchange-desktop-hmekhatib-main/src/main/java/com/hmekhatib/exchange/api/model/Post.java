package com.hmekhatib.exchange.api.model;

import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("added_date")
    public String addedDate;
    @SerializedName("id")
    public Integer id;
    @SerializedName("user_id")
    public Integer userid;
    @SerializedName("lbp_amount")
    public Float lbpAmount;
    @SerializedName("usd_amount")
    public Float usdAmount;
    @SerializedName("typeSell")
    public Boolean typeSell;
    @SerializedName("username")
    public String username;
    @SerializedName("postid")
    public Integer postid;
    @SerializedName("trade")
    public String trade;

    public String getTrade() {
        return trade;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserid() {
        return userid;
    }

    public Float getLbpAmount() {
        return lbpAmount;
    }

    public Float getUsdAmount() {
        return usdAmount;
    }

    public Boolean getTypeSell() {
        return typeSell;
    }


    public String getUsername() {
        return username;
    }

    public Integer getPostid() {
        return postid;
    }

    public Post(Float usdAmount, Float lbpAmount, Boolean typeSell){
        this.lbpAmount = lbpAmount;
        this.usdAmount = usdAmount;
        this.typeSell= typeSell;
    }

    public Post(Integer postid){
        this.postid = postid;
    }
}
