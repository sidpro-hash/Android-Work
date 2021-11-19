package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Practical5b extends AppCompatActivity {

    private TextView textResult;
    // https://www.youtube.com/watch?v=4JGvDUlfk7Y&ab_channel=CodinginFlow
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical5b);

        textResult = findViewById(R.id.text_retrofit);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);//getposts() method body created
        ProgressDialog dialog = new ProgressDialog(Practical5b.this);
        dialog.setMessage("Reading Data...");
        dialog.show();
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        // execute will run on main thread causing app to stop for second(s)
        // enqueue will run on separate thread
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textResult.setText("Code: "+response.code());
                    return;
                }
                List<Post> posts = response.body();
                for(Post post: posts){
                    String content="";
                    content += "ID: "+ post.getId() + "\n";
                    content += "User ID: "+ post.getUserId() + "\n";
                    content += "Title: "+ post.getTitle() + "\n";
                    content += "Text: "+ post.getText() + "\n\n";

                    textResult.append(content);
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }
}