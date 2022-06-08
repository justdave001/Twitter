package com.codepath.apps.restclienttemplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {

    TwitterClient client;
    RecyclerView rvTweets;
    List<Tweet> tweets;
    TweetsAdapter adapter;


    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApp.getRestClient(this);
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });
        //find recyclerview
        rvTweets = findViewById(R.id.rvTweets);

        //initialize list of tweets
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(this,tweets);

        //recycler setup: layout manager
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setAdapter(adapter);
        populateHomeTimeline();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //adds value to menu bar if menu is present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if (item.getItemId()==R.id.compose_tweet){
           //tweet icon is clicked and we'll navigate to compose screen/actvty
           Toast.makeText(this,"Tweet", Toast.LENGTH_SHORT).show();
           //navigate to tweet screen
           return true;
       }
        return super.onOptionsItemSelected(item);
    }

    private void openLogin() {
        TwitterApp.getRestClient(this).clearAccessToken();

        // navigate backwards to Login screen
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // this makes sure the Back button won't work
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // same as above
        startActivity(i);
    }


    private void populateHomeTimeline()  {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i("tlactivity", "successful"+json.toString());
                JSONArray jsonArray = json.jsonArray;
                try {
                    tweets.addAll(Tweet.fromJsonArray(jsonArray));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e("tlactivity","Json exception",e);
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("onFailure", "Failed!"+response, throwable);
            }

        });
    }
}