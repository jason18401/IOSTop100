package com.example.hyu13.iostop100;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static String URL = "https://rss.itunes.apple.com/api/v1/us/ios-apps/top-free/all/50/explicit.json";

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    private RequestQueue mRequestQueue;

    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate start");

        mRequestQueue = Volley.newRequestQueue(this);
        getJSON();
    }

    private void getJSON(){
        Log.d(TAG, "in getJSON");

        JsonObjectRequest root = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject feed = response.getJSONObject("feed");
                    JSONArray results = feed.getJSONArray("results");
                    //Log.d("FEED", String.valueOf(feed));w
                    for(int i = 0; i<results.length(); i++){
                        JSONObject resultsObj = results.getJSONObject(i);

                        Log.d(TAG, "inside" + String.valueOf(i));
                        mImageUrls.add(resultsObj.getString("artworkUrl100"));
                        mTitle.add(resultsObj.getString("name"));

                    }
                    getRecyclerView();
                    //adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(root);

    }

    private void getRecyclerView(){
        Log.d(TAG, "getRecyclerView");
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(mImageUrls, mTitle, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }
}
