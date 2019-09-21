package com.example.webservice;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView rv;

    private Api api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)

                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);


        api.getResponse().enqueue(new Callback<WebserviceResponse>() {
            @Override
            public void onResponse(@NonNull Call<WebserviceResponse> call, @NonNull Response<WebserviceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus().equals("ok")) {

                        Log.i(TAG, "onResponse::::: " + response.body().getTotalResults());
                        setupNewsList(response.body().getArticles());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WebserviceResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });

    }

    private void setupNewsList(List<Article> articles) {

        if (articles.size() > 0) {
            rv.setAdapter(new NewsAdapter(articles, this));
        }

    }
}
