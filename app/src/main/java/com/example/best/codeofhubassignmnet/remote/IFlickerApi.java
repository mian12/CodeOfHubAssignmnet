package com.example.best.codeofhubassignmnet.remote;

import com.example.best.codeofhubassignmnet.model.FlickerPublicPhotesResponse;
import com.example.best.codeofhubassignmnet.model.modelMyPhotes.FlickerMyPhotesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IFlickerApi {
    //public photes api end points
    @GET("feeds/photos_public.gne?format=json&nojsoncallback=1")
    Call<FlickerPublicPhotesResponse> getFlikerPublicPhotes();
}
