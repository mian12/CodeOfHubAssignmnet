package com.example.best.codeofhubassignmnet.Commmon;


import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.best.codeofhubassignmnet.remote.IFlickerApi;
import com.example.best.codeofhubassignmnet.remote.RetrofitClient;

public class Common {


    //https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1

    public  static  final String BASE_URL="https://api.flickr.com/services/";

//
//
    public  static IFlickerApi getPublicPhotes(){

        return RetrofitClient.getRetrofit(BASE_URL).create(IFlickerApi.class);
    }


    public  static  Boolean isConnectedToInternet(Context context)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager!=null) {
            NetworkInfo[]  info=connectivityManager.getAllNetworkInfo();
            if (info!=null)
            {
                for (int i=0; i<info.length; i++)
                {
                    if (info[i].getState()==NetworkInfo.State.CONNECTED){
                        return  true;
                    }
                }
            }
        }


        return  false;
    }






}
