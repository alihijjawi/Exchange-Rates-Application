package com.hmekhatib.currencyexchange;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.hmekhatib.currencyexchange.api.Authentication;
import com.hmekhatib.currencyexchange.api.ExchangeService;
import com.hmekhatib.currencyexchange.api.model.Post;
import com.hmekhatib.currencyexchange.api.model.Transaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.lang.reflect.Type;
import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class PostsFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView lv;
    ArrayList<String> posts;
    ArrayList<Integer> postIDs;
    Button addButton;
    TextInputLayout inputLbp;
    TextInputLayout inputUsd;
    TextView add;
    TextView titleLV;
    RadioButton sellUSD;
    RadioButton buyUSD;
    boolean TypeSell = true;
    String token;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_forum, container, false);

        lv = rootView.findViewById(R.id.lv);
        inputLbp = rootView.findViewById(R.id.txtInptLbpAmount);
        inputUsd = rootView.findViewById(R.id.txtInptUsdAmount);
        sellUSD = rootView.findViewById(R.id.TypeSellBtn);
        buyUSD = rootView.findViewById(R.id.TypeBuyBtn);
        addButton = rootView.findViewById(R.id.btnAddPost);
        titleLV = rootView.findViewById(R.id.textView);

        if (Authentication.INSTANCE.getToken() == null) {
            add = rootView.findViewById(R.id.textViewAdd);
            add.setText("Please Login");
            add.setGravity(Gravity.CENTER);
            lv.setVisibility(View.INVISIBLE);
            inputLbp.setVisibility(View.INVISIBLE);
            inputUsd.setVisibility(View.INVISIBLE);
            sellUSD.setVisibility(View.INVISIBLE);
            buyUSD.setVisibility(View.INVISIBLE);
            addButton.setVisibility(View.INVISIBLE);
            titleLV.setVisibility(View.INVISIBLE);
            return rootView;
        }



        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TypeSell = sellUSD.isChecked();
               float lbp = Integer.parseInt(inputLbp.getEditText().getText().toString());
               float usd = Integer.parseInt(inputUsd.getEditText().getText().toString());
               PostHelper.Companion.addTransaction(usd,lbp,TypeSell);
            }
        });

        //posts= new String[PostHelper.Companion.retPosts().size()];
        posts = PostHelper.Companion.retPosts();

        //postIDs= new Integer[PostHelper.Companion.retPostIDs().size()];
        postIDs = PostHelper.Companion.retPostIDs();
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(rootView.getContext(), R.layout.list_item, posts);
        lv.setAdapter(monthAdapter);
        lv.setOnItemClickListener(this);

        return rootView;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    Toast.makeText(view.getContext(),"Trade Accepted! "+i+"  "+postIDs.get(i),Toast.LENGTH_SHORT).show();
    PostHelper.Companion.acceptPost(postIDs.get(i));
    posts.remove(i);
    postIDs.remove(i);
    renderLV(view);
    }

    void renderLV(View rv) {
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(rv.getContext(), R.layout.list_item, posts);
        lv.setAdapter(monthAdapter);
    }

}