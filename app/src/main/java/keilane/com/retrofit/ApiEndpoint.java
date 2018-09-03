package keilane.com.retrofit;

import keilane.com.retrofit.dominio.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface ApiEndpoint {

    @GET("posts/{id}")
    Call<Post> obterPost(@Path("id") String userID);

}