package com.labAdvance.carRental.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.labAdvance.carRental.R;
import com.labAdvance.carRental.database.SqliteHelper;
import com.labAdvance.carRental.fragments.CallFragment;
import com.labAdvance.carRental.fragments.CarMenuFragment;
import com.labAdvance.carRental.fragments.FavoritesFragment;
import com.labAdvance.carRental.fragments.HomeFragment;
import com.labAdvance.carRental.fragments.OffersFragment;
import com.labAdvance.carRental.fragments.ProfileFragment;
import com.labAdvance.carRental.fragments.ReservationsFragment;
import com.labAdvance.carRental.models.RegisterModel;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView profilePic;
    private TextView username, email;
    private SharedPreferences sharedPreferences;
    private SqliteHelper mydb;
    private Fragment fragment;

    private static final String Shared_Name = "Remember";
    private static final String For_Home = "Data" ;
    private static final String emailKey = "userName";
    private static final String passwordKey = "Password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //initialize Components
        View header = navigationView.getHeaderView(0);
        username = (TextView) header.findViewById(R.id.homeUserName);
        profilePic = (ImageView) header.findViewById(R.id.homeProfile);
        email = (TextView) header.findViewById(R.id.homeEmail);
        mydb = new SqliteHelper(this);
        sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userName", "no");

        if (!userEmail.equals("no")) {
            try {
                RegisterModel user = mydb.getUserData(userEmail);
                username.setText(user.getFirstName() + " " + user.getLastName());
                email.setText(userEmail);
                byte[] array = user.getProfile();
                Bitmap bitmap = BitmapFactory.decodeByteArray(array, 0, array.length);
                Drawable image = new BitmapDrawable(getResources(), bitmap);
                profilePic.setBackground(image);
            } catch (Exception e) {
                Toast.makeText(Home.this, "Error", Toast.LENGTH_SHORT).show();
            }

        }
        fragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment).commit();


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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            fragment = new HomeFragment();
            // Handle the camera action
        } else if (id == R.id.car_menu) {
            fragment = new CarMenuFragment();

        } else if (id == R.id.your_reservation) {
            fragment = new ReservationsFragment();

        } else if (id == R.id.your_favorites) {
            fragment = new FavoritesFragment();

        } else if (id == R.id.special_offers) {
            fragment = new OffersFragment();

        } else if (id == R.id.profile) {
            fragment = new ProfileFragment();

        } else if (id == R.id.call_us) {
            fragment = new CallFragment() ;

        } else if (id == R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences(Shared_Name , Context.MODE_PRIVATE);
            SharedPreferences.Editor editor  = sharedPreferences.edit() ;
            editor.putString(emailKey , "no") ;
            editor.putString(passwordKey , "no") ;
            editor.commit() ;
            Intent i =  new Intent(Home.this , Login.class);
            startActivity(i);
            finish();

        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction() ;
        transaction.replace(R.id.fragment_container , fragment).commit() ;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
