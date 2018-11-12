package com.example.best.codeofhubassignmnet;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.best.codeofhubassignmnet.Commmon.Common;
import com.example.best.codeofhubassignmnet.MyPhotesActivity.MyPhotoActivity;
import com.example.best.codeofhubassignmnet.adapter.GalleryPhotesAdapter;
import com.example.best.codeofhubassignmnet.adapter.PublicPhotesAdapter;
import com.example.best.codeofhubassignmnet.map.MapsActivity;
import com.example.best.codeofhubassignmnet.model.FlickerPublicPhotesResponse;
import com.example.best.codeofhubassignmnet.model.Item;
import com.example.best.codeofhubassignmnet.model.galleryResponse.GalleryDetail;
import com.example.best.codeofhubassignmnet.model.modelMyPhotes.Photo;
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

import org.json.JSONArray;
import org.json.JSONException;
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


        if (Common.isConnectedToInternet(getApplicationContext())) {
            // load menu
            sendRequestPublicPhotesFlicker();

        } else {

            Toast.makeText(MainActivity.this, "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
            return;

        }





    }




    private   void  sendRequestPublicPhotesFlicker() {
        dialog.show();

        iFlickerApi.getFlikerPublicPhotes().enqueue(new Callback<FlickerPublicPhotesResponse>() {
            @Override
            public void onResponse(Call<FlickerPublicPhotesResponse> call, Response<FlickerPublicPhotesResponse> response) {

                FlickerPublicPhotesResponse flickerPublicPhotesResponse = response.body();
                List<Item> items = flickerPublicPhotesResponse.getItems();

                dialog.dismiss();

                PublicPhotesAdapter adapter = new PublicPhotesAdapter(MainActivity.this, items);
                recyclerView_publicPhotes.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<FlickerPublicPhotesResponse> call, Throwable t) {

                dialog.dismiss();

                Log.e("error", t.toString());
            }
        });

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

            startActivity(new Intent(MainActivity.this, MyPhotoActivity.class));

        } else if (id == R.id.nav_map) {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));

        } else if (id == R.id.nav_my_profile) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}





