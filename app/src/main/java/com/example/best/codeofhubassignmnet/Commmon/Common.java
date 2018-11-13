package com.example.best.codeofhubassignmnet.Commmon;


import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.best.codeofhubassignmnet.remote.IFlickerApi;
import com.example.best.codeofhubassignmnet.remote.RetrofitClient;

public class Common {



    public  static  String realName="";
    public  static  String userName="";
    public  static  String userId="";

    // main flicker url fo geting data
    public static final String BASE_URL = "https://api.flickr.com/services/";

    ///calling retrofit class and make a call according to base url
    public static IFlickerApi getPublicPhotes() {

        return RetrofitClient.getRetrofit(BASE_URL).create(IFlickerApi.class);
    }


    public static Boolean isConnectedToInternet(Context context) {
        // checking inernet connection available or not
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }


        return false;
    }


}
