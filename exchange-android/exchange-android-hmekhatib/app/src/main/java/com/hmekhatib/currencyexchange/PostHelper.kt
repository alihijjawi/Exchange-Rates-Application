package com.hmekhatib.currencyexchange

import android.util.Log
import com.hmekhatib.currencyexchange.api.Authentication
import com.hmekhatib.currencyexchange.api.ExchangeService
import com.hmekhatib.currencyexchange.api.model.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostHelper {

    companion object {
        var posts: ArrayList<String> = ArrayList();
        var postIDs: ArrayList<Int> = ArrayList();
        fun getPostsC() {
            ExchangeService.exchangeApi().getPosts(
            if (Authentication.getToken() != null)
                "Bearer ${Authentication.getToken()}"
            else
                null
            ).enqueue(object :
                Callback<List<Post>> {
                override fun onResponse(call: Call<List<Post>>, response:
                Response<List<Post>>
                ) {
                    var posts2: ArrayList<String> = ArrayList();
                    var postIDs2: ArrayList<Int> = ArrayList();
                    val responseBody: List<Post>? = response.body();
                    if (responseBody != null) {
                        for (i in responseBody.indices) {

                            var trade = ""
                            if (responseBody[i].usdToLbp == true)
                                trade = "WTS"
                            else
                                trade = "WTB"
                            posts2+=responseBody[i].username.toString()+ " "+trade+" "+responseBody[i].usdAmount.toString()+"$ for "+
                                    responseBody[i].lbpAmount.toString()+ "L.L (rate = "+ (responseBody[i].lbpAmount!! / responseBody[i].usdAmount!!).toString()+")"

                            var postid = responseBody[i].id;
                            postIDs2.add(postid!!);
                        }
                    }
                    posts = posts2;
                    postIDs = postIDs2;

                }
                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    return;
                    TODO("Not yet implemented")
                }
            })
        }
        fun addTransaction(usd: Float, lbp: Float, typeSell: Boolean) {
            var post: Post = Post()
            post.usdAmount = usd;
            post.lbpAmount = lbp;
            post.usdToLbp = typeSell;
            Log.d("TYPEOFTXN", typeSell.toString())
            ExchangeService.exchangeApi().addPost(post,
                if (Authentication.getToken() != null)
                    "Bearer ${Authentication.getToken()}"
                else
                    null
            ).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response:
                Response<Any>) {

                }
                override fun onFailure(call: Call<Any>, t: Throwable) {

                }
            })
        }

        fun acceptPost(postID: Int) {
            var post: Post = Post()
            post.postID = postID
            ExchangeService.exchangeApi().accPost(post,
                if (Authentication.getToken() != null)
                    "Bearer ${Authentication.getToken()}"
                else
                    null
            ).enqueue(object: Callback<Any> {
                override fun onResponse(call: Call<Any>, response:
                Response<Any>) {

                }
                override fun onFailure(call: Call<Any>, t: Throwable) {

                }
            })
        }


        fun retPosts(): ArrayList<String>? {
            return posts;
        }

        fun retPostIDs(): ArrayList<Int>? {
            return postIDs;
        }
    }
}