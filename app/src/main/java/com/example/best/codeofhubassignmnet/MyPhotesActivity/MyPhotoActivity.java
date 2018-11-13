package com.example.best.codeofhubassignmnet.MyPhotesActivity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.best.codeofhubassignmnet.Commmon.Common;
import com.example.best.codeofhubassignmnet.MainActivity;

import com.example.best.codeofhubassignmnet.R;
import com.example.best.codeofhubassignmnet.adapter.GalleryPhotesAdapter;
import com.example.best.codeofhubassignmnet.model.galleryResponse.GalleryDetail;
import com.example.best.codeofhubassignmnet.model.modelMyPhotes.Photo;
import com.example.best.codeofhubassignmnet.volly.MySingleton;
import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.auth.Permission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.model.Token;
import org.scribe.model.Verifier;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MyPhotoActivity extends AppCompatActivity {


    private RecyclerView recyclerView_myPhotes;
    RecyclerView.LayoutManager layoutManager;
    SpotsDialog dialog;
    public SwipeRefreshLayout swipeRefreshLayout;


    Flickr flickr = null;
    AuthInterface authInterface = null;
    Token token = null;
    String result = null;

    ArrayList<GalleryDetail> galleyDetailArrayList = new ArrayList<>();
    List<String> urlList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_photo);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        getSupportActionBar().setTitle("My Photo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        dialog = new SpotsDialog(MyPhotoActivity.this);
        dialog.setCancelable(false);


        recyclerView_myPhotes = findViewById(R.id.recyclerview_MyPhotes);

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView_myPhotes.setLayoutManager(layoutManager);
        // set animation on recyclerview
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(recyclerView_myPhotes.getContext(),R.anim.layout_fall_down);

        recyclerView_myPhotes.setLayoutAnimation(controller);



        // pull to refresh declare
        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark
        );
        //listener of pull to refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // checking net connected or not
                if (Common.isConnectedToInternet(getApplicationContext())) {
                    //called AsyncTask backgroud thread  to request token
                    new GetTokenFlicker().execute();
                    swipeRefreshLayout.setRefreshing(false);

                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MyPhotoActivity.this, "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
                    return;

                }


            }
        });


        // checking net connected or not
        if (Common.isConnectedToInternet(getApplicationContext())) {
            //called AsyncTask backgroud thread  to request token
            new GetTokenFlicker().execute();

        } else {
            Toast.makeText(MyPhotoActivity.this, "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
            return;

        }


    }


    public class GetTokenFlicker extends AsyncTask<String, String, String> {

        public final String TAG = GetTokenFlicker.class.getSimpleName();
        Dialog auth_dialog = null;

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "START");
        }

        @Override
        protected String doInBackground(String... params) {

            // getting flicker app key and secret key by registering the user on flicker
            String flickrKey = "79a5f137544068d61e0b889065f13bba";
            String flickrSecret = "e514bd64c354a159";

            String result = "";
            try {

                Flickr.debugRequest = false;
                Flickr.debugStream = false;
                // define object of flicker with paramters  for rest call
                flickr = new Flickr(flickrKey, flickrSecret, new REST());
                authInterface = flickr.getAuthInterface();
                // this is callback method w for flicker  for login yahoo account
                token = authInterface.getRequestToken("your calback url");
                result = authInterface.getAuthorizationUrl(token, Permission.WRITE);

                return result;

            } catch (IllegalStateException e) {
                e.printStackTrace();
                return result;
            } catch (VerifyError e) {
                e.printStackTrace();
                return result;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && result.length() > 0) {
                Log.e("f1Post", "Follow this URL to authorise yourself on Flickr");
                //now delcare dalert dialog for yahoo page
                auth_dialog = new Dialog(MyPhotoActivity.this);
                auth_dialog.setContentView(R.layout.auth_dialog);
                //intialize webview to load url  and getting new generated token
                final WebView web = (WebView) auth_dialog.findViewById(R.id.webv);
                web.getSettings().setJavaScriptEnabled(true);

                web.loadUrl(result);

                web.setWebViewClient(
                        new WebViewClient() {

                            @Override
                            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                super.onPageStarted(view, url, favicon);
                                Log.e("urlStart", "url to start " + url);
                                if (url.contains("&oauth_verifier")) {
                                    auth_dialog.dismiss();
                                    Uri uri = Uri.parse(url);
                                    String oauth_verifier = uri.getQueryParameter("oauth_verifier");
                                    String oauth_token = uri.getQueryParameter("oauth_token");

                                    // to verifiy the new generated token on flicker for create  async task backend thread
                                    new VerifiyTokenFlicker().execute(oauth_token, oauth_verifier);
                                }
                            }


                            @Override
                            public void onPageFinished(WebView view, String url) {
                                super.onPageFinished(view, url);
                                Log.e("urlGet", "url to get " + url);
                                // auth_dialog.dismiss();


                            }
                        });

                auth_dialog.show();
                auth_dialog.setTitle("Authorize");
                auth_dialog.setCancelable(true);


            }

        }
    }


    public class VerifiyTokenFlicker extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... oauth_verifier) {
            Log.e("code", "CODE " + oauth_verifier[0] + " " + oauth_verifier[1]);
            try {
                Verifier verifier = new Verifier(oauth_verifier[1]);
                Token accessToken = authInterface.getAccessToken(token, verifier);
                Log.e("sucsess", "Authentication success");
                 authInterface = flickr.getAuthInterface();
                Token requestToken = authInterface.getRequestToken();
                Log.e("auth tocen", "auth tocen and secret: " + requestToken.getToken() + " , " + requestToken.getSecret());

                Auth auth = new Auth();

                auth.setToken(requestToken.getToken());
                auth.setTokenSecret(requestToken.getSecret()); // thats the token I got from the registration, before I set the token of the requestToken
                auth.setPermission(Permission.WRITE);


                RequestContext requestContext = RequestContext.getRequestContext();
                requestContext.setAuth(auth);
                flickr.setAuth(auth);
                Log.e("", "checking for token" + accessToken);
                auth = authInterface.checkToken(accessToken);

                //save the current user login details
                Common.userId=auth.getUser().getId();
                Common.realName=auth.getUser().getRealName();
                Common.userName=auth.getUser().getUsername();


                // This token can be used until the user revokes it.
                Log.e("0", "Token: " + accessToken.getToken());
                Log.e("1", "Secret: " + accessToken.getSecret());
                Log.e("2", "nsid: " + auth.getUser().getId());
                Log.e("3", "Realname: " + auth.getUser().getRealName());
                Log.e("4", "Username: " + auth.getUser().getUsername());
                Log.e("5", "Permission: " + auth.getPermission().getType());

                //  url for getting gallery ids
                String galleryApiUrl = "https://api.flickr.com/services/rest/?method=flickr.galleries.getList&" +
                        "api_key=073238bec9052c2f344dcbe2908fa103&user_id=66857167%40N04&format=json&nojsoncallback=1" +
                        "&auth_token=72157700283359252-6dbcc9569868b461&api_sig=6f4358a0768f693efe0951532863a90f";


                //backend call for getting gallery id
                gettingGallery(galleryApiUrl);


            } catch (FlickrException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {


        }

    }


    public void gettingGallery(String getPhotesUrl) {

        StringRequest postRequest = new StringRequest(Request.Method.GET, getPhotesUrl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("res", response);

                        try {

                            // parse the json response
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject galleyJson = jsonObject.getJSONObject("galleries");
                            String userId = galleyJson.getString("user_id");


                            JSONArray jsonArray = galleyJson.getJSONArray("gallery");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject innerJson = jsonArray.getJSONObject(i);
                                GalleryDetail object = new GalleryDetail();

                                object.setUserId(userId);
                                object.setGalleryId(innerJson.getString("gallery_id"));
                                object.setGalleryUrl(innerJson.getString("url"));
                                object.setUserName(innerJson.getString("username"));

                                galleyDetailArrayList.add(object);

                            }

                            //id of gallery
                            String galleryId = galleyDetailArrayList.get(0).getGalleryId();

                            //url for getting photoes from specific gallery
                            String galleryPhotesApi = "https://api.flickr.com/services/rest/?method=flickr.galleries.getPhotos&api_key=79a5f137544068d61e0b889065f13bba&gallery_id=" + galleryId + "&format=json&nojsoncallback=1";



                            //now call the backend volley liberay to get photes
                            getGallleryPhotes(galleryPhotesApi);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage() + "");
                Toast.makeText(MyPhotoActivity.this,
                        "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


        MySingleton.getInstance().addToReqQueue(postRequest);


    }


    private void getGallleryPhotes(String url)

    {


        dialog.show();

        //initalize the arraylist for storing urls of gallery photos
        urlList = new ArrayList<>();

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        dialog.dismiss();

                        Log.e("res", response);
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject photosJson = jsonObject.getJSONObject("photos");


                            JSONArray jsonArray = photosJson.getJSONArray("photo");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject innerJson = jsonArray.getJSONObject(i);

                                int farmId = Integer.parseInt(innerJson.getString("farm"));
                                String id = innerJson.getString("id");
                                String server = innerJson.getString("server");
                                String secret = innerJson.getString("secret");

                                // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg

                                //now make the url for get image
                                String customUrl = "https://farm" + farmId + ".staticflickr.com/" + server + "/" + id + "_" + secret + ".jpg";
                                Log.e("urlinal", customUrl);

                                // add  all photos url into arraylist for adapter purpose
                                urlList.add(customUrl);


                            }


                            //declare object of  adapter and assign newly created arra
                            GalleryPhotesAdapter adapter = new GalleryPhotesAdapter(MyPhotoActivity.this, urlList);
                            recyclerView_myPhotes.setAdapter(adapter);
//

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();

                Log.e("error", error.getMessage() + "");
                Toast.makeText(MyPhotoActivity.this,
                        "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


        MySingleton.getInstance().addToReqQueue(postRequest);

    }
}
