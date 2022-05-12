package com.hmekhatib.exchange.api;

import com.hmekhatib.exchange.api.model.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.util.List;

public interface Exchange {
    @POST("/user")
    Call<User> addUser(@Body User user);
    @POST("/authentication")
    Call<Token> authenticate(@Body User user);
    @GET("/exchangeRate")
    Call<ExchangeRates> getExchangeRates();
    @POST("/transaction")
    Call<Object> addTransaction(@Body Transaction transaction,
                                @Header("Authorization") String authorization);
    @GET("/transaction")
    Call<List<Transaction>> getTransactions(@Header("Authorization")
                                                    String authorization);
    @GET("/getstatsell")
    Call<Stat> getStatSell();

    @GET("/getstatbuy")
    Call<Stat> getStatBuy();

    @GET("/getgraph")
    Call<Graph> getGraph();

    @GET("/getPosts")
    Call<List<Post>> getPost(@Header("Authorization") String authorization);

    @POST("/post")
    Call<Object> addPost(@Body Post post,
                         @Header("Authorization")
                                 String authorization);
    @POST("/acceptPost")
    Call<Object> acceptPost(@Body Post post,
                            @Header("Authorization")
                            String authorization);
}
