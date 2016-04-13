package com.example.android.gameoftag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.android.gameoftag.R;

/**
 * Created by User on 08.04.2016.
 */
public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //    toolbar.setLogo(R.drawable.number15_logo);
        toolbar.setTitle(R.string.action_rules);
        toolbar.setSubtitle(R.string.action_about);
        setSupportActionBar(toolbar);
    }
    public void onClickBack(View view) {
        finish();

    }
}
