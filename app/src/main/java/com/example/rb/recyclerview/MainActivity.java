package com.example.rb.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yalantis.phoenix.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private RequestQueue mRequestQueue;
    LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExampleList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

        //For pagination...
        //Api page 0,1 it will fetch data according to priority and all the objects from two pages of api are fetched according to priority
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            parseJSON1();



                        }
                    }
                }
            }
        });

    }
    private void parseJSON()
    {
    String url = "http://pascolan.com/users.php?page=0";

        StringRequest request = new StringRequest(Request.Method.GET, url,new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj=new JSONObject(response);
                        Log.d("hello", obj.toString());
                        JSONArray jsonArray = obj.getJSONArray("SampleUsers");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject hit = jsonArray.getJSONObject(i);
                            Log.d("hello", hit.toString());
                            String username = hit.getString("name");
                            Log.d("hello", username);
                            String imageUrl = hit.getString("image");
                            Log.d("hello", imageUrl);
                            int verify = hit.getInt("verified");
                            int priority=hit.getInt("priority");

                            mExampleList.add(new ExampleItem(imageUrl, username, verify,priority));
                            Collections.sort(mExampleList);

                        }
                        Log.d("rishabh", String.valueOf(mExampleList.size()));
                        //Toast.makeText(MainActivity.this,""+mExampleList.size(),Toast.LENGTH_SHORT).show();
                        mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
                        mRecyclerView.setAdapter(mExampleAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        }
    });

        mRequestQueue.add(request);
    }


    private void parseJSON1()
    {
        String url = "http://pascolan.com/users.php?page=1";

        StringRequest request = new StringRequest(Request.Method.GET, url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj=new JSONObject(response);
                    Log.d("hello", obj.toString());
                    JSONArray jsonArray = obj.getJSONArray("SampleUsers");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        Log.d("hello", hit.toString());
                        String username = hit.getString("name");
                        Log.d("hello", username);
                        String imageUrl = hit.getString("image");
                        Log.d("hello", imageUrl);
                        int verify = hit.getInt("verified");
                        int priority=hit.getInt("priority");

                        mExampleList.add(new ExampleItem(imageUrl, username, verify,priority));
                        Collections.sort(mExampleList);

                    }
                    Log.d("rishabh", String.valueOf(mExampleList.size()));
                    //Toast.makeText(MainActivity.this,""+mExampleList.size(),Toast.LENGTH_SHORT).show();
                    mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
                    mRecyclerView.setAdapter(mExampleAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }



}
