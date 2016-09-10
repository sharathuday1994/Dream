package com.nihalpradeep.carapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity{

    private Button bLeft[],bRight[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bLeft = new Button[5];
        bRight = new Button[5];

        bLeft[0]= (Button) findViewById(R.id.home_button_left_row1);
        bLeft[1]= (Button) findViewById(R.id.home_button_left_row2);
        bLeft[2]= (Button) findViewById(R.id.home_button_left_row3);
        bLeft[3]= (Button) findViewById(R.id.home_button_left_row4);
        bLeft[4]= (Button) findViewById(R.id.home_button_left_row5);
        bRight[0] = (Button) findViewById(R.id.home_button_right_row1);
        bRight[1] = (Button) findViewById(R.id.home_button_right_row2);
        bRight[2] = (Button) findViewById(R.id.home_button_right_row3);
        bRight[3] = (Button) findViewById(R.id.home_button_right_row4);
        bRight[4] = (Button) findViewById(R.id.home_button_right_row5);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,SearchActivity.class));
            }
        };

        for(int i =0;i<5;i++){
            bLeft[i].setOnClickListener(onClickListener);
            bRight[i].setOnClickListener(onClickListener);
        }
    }


}
