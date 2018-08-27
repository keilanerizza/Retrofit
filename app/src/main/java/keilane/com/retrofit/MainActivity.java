package keilane.com.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import keilane.com.retrofit.dominio.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new GsonBuilder()

                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiEndpoint apiService = retrofit.create(ApiEndpoint.class);
        Call<Post> call = apiService.obterPost(1);
        
        call.enqueue(new Callback<Post>() { //chamada assíncrona

            public void onResponse(Call<Post> call, Response<Post> response) {
                int statusCode = response.code();
                Post post = response.body();

            }

            public void onFailure(Call<Post> call, Throwable t) {
            // Log error here since request failed
                Log.i("teste", t.toString());
            }
        });
    }
}