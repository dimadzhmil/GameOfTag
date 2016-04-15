package com.example.android.gameoftag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by User on 14.04.2016.
 */
public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TextView textViewTime = (TextView) findViewById(R.id.win_time);
            textViewTime.setText(extras.getString("WIN_TIME"));
            TextView textViewSteps = (TextView) findViewById(R.id.win_steps);
            textViewSteps.setText(getString(R.string.steps) + extras.getInt("WIN_STEPS"));
        }
    }
    public void onClickBack(View view) {
        onBackPressed();
    }
}