package com.hmekhatib.currencyexchange.api

import com.google.gson.annotations.SerializedName
import com.hmekhatib.currencyexchange.api.model.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

object ExchangeService {
    private const val API_URL: String = "http://10.0.2.2:5000"
    fun exchangeApi(): Exchange {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // Create an instance of our Exchange API interface.
        return retrofit.create(Exchange::class.java);
    }

    interface Exchange {
        @GET("/exchangeRate")
        fun getExchangeRates(): Call<ExchangeRates>

        @POST("/transaction")
        fun addTransaction(@Body transaction: Transaction,
                @Header("Authorization") authorization: String?): Call<Any>

        @GET("/transaction")
        fun getTransactions(@Header("Authorization") authorization: String):
                Call<List<Transaction>>

        @POST("/user")
        fun addUser(@Body user: User): Call<User>

        @POST ("/authentication")
        fun authenticate(@Body user:User): Call<Token>

        @GET ("/getstatsell")
        fun getStatsSell(): Call<Stat>

        @GET ("/getstatbuy")
        fun getStatsBuy(): Call<Stat>

        @GET ("/getgraph")
        fun getGraph(): Call<Graph>

        @GET ("/getPosts")
        fun getPosts(@Header("Authorization") authorization: String?): Call<List<Post>>

        @POST("/post")
        fun addPost(@Body post: Post,
               @Header("Authorization") authorization: String?): Call<Any>

        @POST("/acceptPost")
        fun accPost(@Body post: Post,
                    @Header("Authorization") authorization: String?): Call<Any>


    }

}