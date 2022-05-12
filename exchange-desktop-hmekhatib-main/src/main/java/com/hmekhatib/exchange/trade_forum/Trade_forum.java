package com.hmekhatib.exchange.trade_forum;
import com.hmekhatib.exchange.Authentication;
import com.hmekhatib.exchange.OnPageCompleteListener;
import com.hmekhatib.exchange.PageCompleter;
import com.hmekhatib.exchange.api.ExchangeService;
import com.hmekhatib.exchange.api.model.Post;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class Trade_forum implements PageCompleter {
    @FXML
    TextField usdTextField;
    @FXML
    TextField lbpTextField;
    @FXML
    ToggleGroup transactionType;
    @FXML
    TableView<Post> tableView;
    @FXML
    TableColumn username;
    @FXML
    TableColumn lbpAmount;
    @FXML
    TableColumn usdAmount;
    @FXML
    TableColumn typeSell;
    @FXML
    TableColumn id;
    @FXML
    TextField selectedTrade;



    public void initialize(){get();}

    public void accept(ActionEvent actionEvent){
        Post post = new Post(Integer.parseInt(selectedTrade.getText()));
        String userToken = Authentication.getInstance().getToken();
        String authHeader = userToken != null ? "Bearer " + userToken : null;
        ExchangeService.exchangeApi().acceptPost(post,authHeader).enqueue(new
       Callback<Object>() {
           @Override
           public void onResponse(Call<Object> call, Response<Object>
                   response) {
               get();

           }
           @Override
           public void onFailure(Call<Object> call, Throwable throwable)
           {
           }
       });

    }

    public void add(ActionEvent actionEvent){
            Post post = new Post(
                 Float.parseFloat(usdTextField.getText()),
                 Float.parseFloat(lbpTextField.getText()),
                 ((RadioButton)
                         transactionType.getSelectedToggle()).getText().equals("Sell USD")
         );
         String userToken = Authentication.getInstance().getToken();
         String authHeader = userToken != null ? "Bearer " + userToken : null;
         ExchangeService.exchangeApi().addPost(post,authHeader).enqueue(new
         Callback<Object>() {
             @Override
             public void onResponse(Call<Object> call, Response<Object>
                     response) {
                 get();
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



    public void get(){
        id.setCellValueFactory(new
                PropertyValueFactory<Post, Integer>("id"));
        username.setCellValueFactory(new
                PropertyValueFactory<Post, String>("username"));
        lbpAmount.setCellValueFactory(new
                PropertyValueFactory<Post, Long>("lbpAmount"));
        usdAmount.setCellValueFactory(new
                PropertyValueFactory<Post, Long>("usdAmount"));
        typeSell.setCellValueFactory(new
                PropertyValueFactory<Post, String>("trade"));
        ExchangeService.exchangeApi().getPost("Bearer " +
                Authentication.getInstance().getToken()).enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call,
                                           Response<List<Post>> response) {
                        for (Post p: response.body()) {
                            if (p.typeSell) p.trade = "WTS";
                            else p.trade = "WTB";
                        }
                        tableView.getItems().setAll(response.body());
                    }
                    @Override
                    public void onFailure(Call<List<Post>> call,
                                          Throwable throwable) {
                    }
                });

    }

    private OnPageCompleteListener
            onPageCompleteListener;


    public void setOnPageCompleteListener(OnPageCompleteListener
                                                  onPageCompleteListener) {
        this.onPageCompleteListener = onPageCompleteListener;
    }
}

