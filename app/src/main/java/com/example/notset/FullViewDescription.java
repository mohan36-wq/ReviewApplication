package com.example.notset;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FullViewDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view_description);

        TextView tTitle = (TextView)findViewById(R.id.textView_title);
        TextView tDescription = (TextView)findViewById(R.id.textView_description);

        tTitle.setText(""+getIntent().getStringExtra("TITLE"));
        tDescription.setText(""+getIntent().getStringExtra("DESCRIPTION"));


    }
}
