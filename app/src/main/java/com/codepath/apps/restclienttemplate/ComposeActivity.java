package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ComposeActivity extends AppCompatActivity {

    public int MAX_TWEET_LENGTH = 200;
    EditText Compose;
    Button tweet_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        Compose = findViewById(R.id.Compose);
        tweet_button = findViewById(R.id.tweet_button);

        //make button functional, display/update activity
        tweet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = Compose.getText().toString();
                if (tweetContent.isEmpty()){
                    Toast.makeText(ComposeActivity.this,"Tweet cannot be null", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tweetContent.length()>MAX_TWEET_LENGTH){
                    Toast.makeText(ComposeActivity.this,"Too many characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ComposeActivity.this,tweetContent,Toast.LENGTH_SHORT).show();
            }
        });

    }
}