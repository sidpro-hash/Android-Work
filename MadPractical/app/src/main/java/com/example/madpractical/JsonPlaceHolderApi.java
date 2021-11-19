package com.example.madpractical;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    // posts is relative url of https://jsonplaceholder.typicode.com/posts
    // to guide retrofit we have to annotate
    @GET("posts")
    Call<List<Post>> getPosts();
    // retrofit will autogenerate all code
    // to get result
}
