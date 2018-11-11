package com.example.best.codeofhubassignmnet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.best.codeofhubassignmnet.Commmon.Common;
import com.example.best.codeofhubassignmnet.adapter.PublicPhotesAdapter;
import com.example.best.codeofhubassignmnet.map.MapsActivity;
import com.example.best.codeofhubassignmnet.model.FlickerPublicPhotesResponse;
import com.example.best.codeofhubassignmnet.model.Item;
import com.example.best.codeofhubassignmnet.remote.IFlickerApi;
import com.example.best.codeofhubassignmnet.volly.MySingleton;
import com.flickr4java.flickr.Flickr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.activity.ActivityInterface;
import com.flickr4java.flickr.activity.Event;

import com.flickr4java.flickr.activity.ItemList;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.util.IOUtilities;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private IFlickerApi iFlickerApi;
    private RecyclerView recyclerView_publicPhotes;
    RecyclerView.LayoutManager layoutManager;
    SpotsDialog dialog;
    public SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        dialog = new SpotsDialog(MainActivity.this);
        dialog.setCancelable(false);

        recyclerView_publicPhotes = findViewById(R.id.recyclerview_publicPhotes);
        recyclerView_publicPhotes.setLayoutManager(new LinearLayoutManager(this));
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView_publicPhotes.setLayoutManager(layoutManager);

        // public photes api call initalization

        iFlickerApi = Common.getPublicPhotes();


        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark
        );


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                if (Common.isConnectedToInternet(getApplicationContext())) {
                    // load menu
                    sendRequestPublicPhotesFlicker();
                    swipeRefreshLayout.setRefreshing(false);

                } else {

                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
                    return;

                }


            }
        });


//        if (Common.isConnectedToInternet(getApplicationContext())) {
//            // load menu
//            sendRequestPublicPhotesFlicker();
//           // getToken();
//
//        } else {
//
//            Toast.makeText(MainActivity.this, "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
//            return;
//
//        }


        test();


    }


    public void test() {





        Flickr flickr = new Flickr("79a5f137544068d61e0b889065f13bba", "e514bd64c354a159", new REST());
        Flickr.debugStream = false;
        AuthInterface authInterface = flickr.getAuthInterface();
        Token token = authInterface.getRequestToken();



        String url = authInterface.getAuthorizationUrl(token, Permission.WRITE);
        Log.e("url",url);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

         String newToken="878-249-843";

//
//        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
//                new com.android.volley.Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        dialog.dismiss();
//                        // JsonObject jsonObject=new JsonObject()
//                        Log.e("res",response);
//
//
//                    }
//                }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                dialog.dismiss();
//
//                Log.e("error",error.getMessage()+"");
//                Toast.makeText(MainActivity.this,
//                        "something went wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        MySingleton.getInstance().addToReqQueue(postRequest);
//





//        String tokenKey = scanner.nextLine();
//        scanner.close();
//
//        Token requestToken = authInterface.getAccessToken(token, new Verifier(tokenKey));
//        System.out.println("Authentication success");
//
//        Auth auth = authInterface.checkToken(requestToken);
//
//        // This token can be used until the user revokes it.
//        System.out.println("Token: " + requestToken.getToken());
//        System.out.println("Secret: " + requestToken.getSecret());
//        System.out.println("nsid: " + auth.getUser().getId());
//        System.out.println("Realname: " + auth.getUser().getRealName());
//        System.out.println("Username: " + auth.getUser().getUsername());
//        System.out.println("Permission: " + auth.getPermission().getType());
////

//        WebView webView = (WebView) findViewById(R.id.webView1);
//        WebSettings settings = webview.getSettings();
//        settings.setJavaScriptEnabled(true);
//        webView.loadUrl(url);

       // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

        //dialog.show();
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
//        String url ="http://www.flickr.com/services/oauth/request_token"+"?oauth_nonce=89601180"+
//                "&oauth_consumer_key=79a5f137544068d61e0b889065f13bba"+
//                "&oauth_version=1.0";


        //"http://api.flickr.com/services/rest/?method=flickr.auth.getFrob&api_key=79a5f137544068d61e0b889065f13bba&api_sig=e514bd64c354a159"




    }

    private void sendRequestPublicPhotesFlicker() {
//        dialog.show();
//
//        iFlickerApi.getFlikerPublicPhotes().enqueue(new Callback<FlickerPublicPhotesResponse>() {
//            @Override
//            public void onResponse(Call<FlickerPublicPhotesResponse> call, Response<FlickerPublicPhotesResponse> response) {
//
//                FlickerPublicPhotesResponse flickerPublicPhotesResponse = response.body();
//                List<Item> items = flickerPublicPhotesResponse.getItems();
//
//                dialog.dismiss();
//
//                PublicPhotesAdapter adapter = new PublicPhotesAdapter(MainActivity.this, items);
//                recyclerView_publicPhotes.setAdapter(adapter);
//
//
//            }
//
//            @Override
//            public void onFailure(Call<FlickerPublicPhotesResponse> call, Throwable t) {
//
//                dialog.dismiss();
//
//                Log.e("error", t.toString());
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_public_photes) {

            if (Common.isConnectedToInternet(getApplicationContext())) {
                sendRequestPublicPhotesFlicker();
            } else {
                Toast.makeText(MainActivity.this, "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
            }


        } else if (id == R.id.nav_my_photes) {

        } else if (id == R.id.nav_map) {
            startActivity(new Intent(MainActivity.this,MapsActivity.class));

        } else if (id == R.id.nav_my_profile) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
