package com.example.best.codeofhubassignmnet.remote;

import com.example.best.codeofhubassignmnet.model.FlickerPublicPhotesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IFlickerApi {
    @GET("feeds/photos_public.gne?format=json&nojsoncallback=1")
    Call<FlickerPublicPhotesResponse> getFlikerPublicPhotes();

    @GET("feeds/test")
    Call<FlickerPublicPhotesResponse> test(@Query("api-key") String apiKey);
}
