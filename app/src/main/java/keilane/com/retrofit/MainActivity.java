package keilane.com.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import keilane.com.retrofit.dominio.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class MainActivity extends Activity implements View.OnKeyListener {

    private EditText entrada;
    private TextView saida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preparacaoInicial();

    }

    private void preparacaoInicial() {
        entrada = (EditText) findViewById(R.id.idPost);
        entrada.setOnKeyListener(this);

        saida = (TextView) findViewById(R.id.titlePost);
        saida.setOnKeyListener(this);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent event) {
        if (i == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            Call<Post> call = getPostCall();

            call.enqueue(new Callback<Post>() { //chamada assíncrona

                public void onResponse(Call<Post> call, Response<Post> response) {
                    int statusCode = response.code();
                    Post post = response.body();

                    saida.setText(post.getTitle());
                    Log.i("title : " + post.getTitle(), toString());
                }

                public void onFailure(Call<Post> call, Throwable t) {
                    // Log error here since request failed
                    Log.i("teste", t.toString());
                }
            });
            return true;
        }
        return false;
    }

    private Call<Post> getPostCall() {
        Gson gson = new GsonBuilder()

                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiEndpoint apiService = retrofit.create(ApiEndpoint.class);
        return apiService.obterPost(entrada.getText().toString());
    }



}