package com.example.best.codeofhubassignmnet;


import android.content.Intent;
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
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.example.best.codeofhubassignmnet.Commmon.Common;
import com.example.best.codeofhubassignmnet.MyPhotesActivity.MyPhotoActivity;
import com.example.best.codeofhubassignmnet.MyProfile.MyProfileActivity;
import com.example.best.codeofhubassignmnet.adapter.PublicPhotesAdapter;
import com.example.best.codeofhubassignmnet.map.MapsActivity;
import com.example.best.codeofhubassignmnet.model.FlickerPublicPhotesResponse;
import com.example.best.codeofhubassignmnet.model.Item;
import com.example.best.codeofhubassignmnet.remote.IFlickerApi;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // initilize dialog indicator
        dialog = new SpotsDialog(MainActivity.this);
        dialog.setCancelable(false);

        // initialize recylerview
        recyclerView_publicPhotes = findViewById(R.id.recyclerview_publicPhotes);

        //setting the  gridlayout for recyclerview
        layoutManager = new GridLayoutManager(this, 2);

        recyclerView_publicPhotes.setLayoutManager(layoutManager);

        // set animation on recyclerview

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(recyclerView_publicPhotes.getContext(),R.anim.layout_fall_down);

        recyclerView_publicPhotes.setLayoutAnimation(controller);

        // public photes api call initalization
        iFlickerApi = Common.getPublicPhotes();


        // pull to refresh initialize view
        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                // check if internet is connected or not
                if (Common.isConnectedToInternet(getApplicationContext())) {
                    // call for public api
                    sendRequestPublicPhotesFlicker();
                    swipeRefreshLayout.setRefreshing(false);

                } else {

                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
                    return;

                }


            }
        });

        // check if internet is connected or not
        if (Common.isConnectedToInternet(getApplicationContext())) {
            // call for public api
            sendRequestPublicPhotesFlicker();

        } else {

            Toast.makeText(MainActivity.this, "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
            return;

        }


    }


    private void sendRequestPublicPhotesFlicker() {
        dialog.show();

        // retrofit call request for public photos
        iFlickerApi.getFlikerPublicPhotes().enqueue(new Callback<FlickerPublicPhotesResponse>() {
            @Override
            public void onResponse(Call<FlickerPublicPhotesResponse> call, Response<FlickerPublicPhotesResponse> response) {

                // response of public photoes api
                FlickerPublicPhotesResponse flickerPublicPhotesResponse = response.body();
                //assign  public photes to list
                List<Item> items = flickerPublicPhotesResponse.getItems();

                dialog.dismiss();

                //set adapter for public photos
                PublicPhotesAdapter adapter = new PublicPhotesAdapter(MainActivity.this, items);
                recyclerView_publicPhotes.setAdapter(adapter);
                recyclerView_publicPhotes.scheduleLayoutAnimation();


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
            startActivity(new Intent(MainActivity.this, MyProfileActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}





