package com.nihalpradeep.carapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.nihalpradeep.carapp.serverPitShop.ListDisplay;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button bLeft[],bRight[];
    Menu carlist;
    Toolbar toolbarOther;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbarHeading = (Toolbar) findViewById(R.id.toolbar_heading);
        setSupportActionBar(toolbarHeading);
        //toolbarHeading.inflateMenu(R.menu.menu_home_other);
        toolbarOther = (Toolbar) findViewById(R.id.toolbar_other);
        toolbarOther.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homemenu_new_cars:
                        startActivity(new Intent(HomeActivity.this, ListDisplay.class));
                        return true;
                    case R.id.homemenu_used_cars:
                        startActivity(new Intent(HomeActivity.this, ListDisplay.class));
                        return true;
                    case R.id.homemenu_vintage_cars:
                        startActivity(new Intent(HomeActivity.this, ListDisplay.class));
                        return true;
                    case R.id.homemenu_more:
                        startActivity(new Intent(HomeActivity.this, ListDisplay.class));
                        return true;
                    default:
                        startActivity(new Intent(HomeActivity.this, ListDisplay.class));
                        return true;
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.home_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbarHeading, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       getMenuInflater().inflate(R.menu.menu_home_other, menu);
        toolbarOther.inflateMenu(R.menu.menu_home_main);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.homemenu_search:
                startActivity(new Intent(HomeActivity.this, ListDisplay.class));
                return true;
            case R.id.homemenu_garage:
                startActivity(new Intent(HomeActivity.this, ListDisplay.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.drawer_new_cars) {
            Intent i  = new Intent(HomeActivity.this,SearchActivity.class);
            i.putExtra("message","New Cars");
            startActivity(i);

        } else if (id == R.id.drawer_reviews) {

            Intent i  = new Intent(HomeActivity.this,SearchActivity.class);
            i.putExtra("message","Reviews");
            startActivity(i);
        } else if (id == R.id.drawer_used_cars) {

            Intent i  = new Intent(HomeActivity.this,SearchActivity.class);
            i.putExtra("message","Used Cars");
            startActivity(i);
        } else if (id == R.id.drawer_compare) {

            Intent i  = new Intent(HomeActivity.this,SearchActivity.class);
            i.putExtra("message","Compare");
            startActivity(i);
        } else if (id == R.id.drawer_vintage) {

            Intent i  = new Intent(HomeActivity.this,SearchActivity.class);
            i.putExtra("message","Vintage Collection");
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
