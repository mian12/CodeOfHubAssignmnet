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

    @GET("rest/?method=flickr.galleries.getPhotos&format=json&nojsoncallback=1")
    Call<FlickerMyPhotesResponse> getFlikerGallery(@Query("api-key") String apiKey,@Query("gallery_id") String galleryId,@Query("auth_token") String authToken,@Query("api_sig") String apiSig);

//    @GET("feeds/test")
//    Call<FlickerPublicPhotesResponse> test(@Query("api-key") String apiKey);
}
