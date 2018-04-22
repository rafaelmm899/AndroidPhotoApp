package com.softmedialtda.softmediaphotoapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.softmedialtda.softmediaphotoapp.models.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import layout.ListNotificationFrag;
import layout.LogOutFrag;
import layout.ProfileFrag;
import layout.SearchFrag;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    public NavigationView nvDrawer;
    public ImageView img;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user = (User) getIntent().getSerializableExtra("user");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        nvDrawer = (NavigationView) findViewById(R.id.nav_view);
        View hView = nvDrawer.getHeaderView(0);
        TextView nameUserNav = (TextView) hView.findViewById(R.id.nameUserNav);
        TextView userNameNav = (TextView) hView.findViewById(R.id.userNameNav);
        img = (ImageView) hView.findViewById(R.id.imageProfileView);
        if (user.getImageProfile().equals("")) {
            img.setImageResource(R.mipmap.photodefault);
        }else{
            byte[] decodedString = Base64.decode(user.getImageProfile(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            img.setImageBitmap(decodedByte);
        }

        userNameNav.setText(user.getUsername());
        nameUserNav.setText(user.getFirstName()+ " "+user.getSurname());

        setupDrawerContent(nvDrawer);

        hideItemsByTypeUser(user);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.flContent, new ListNotificationFrag());
        tx.commit();
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Bundle bundle = new Bundle();
        if (menuItem.isChecked() == false) {
            switch (menuItem.getItemId()) {
                case R.id.showProfileComplete:
                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new ProfileFrag()).commit();
                    break;
                case R.id.notiList:
                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new ListNotificationFrag()).commit();
                    break;
                case R.id.searchStudent:
                    bundle.putString("typeSearch", "Student");
                    SearchFrag searchFrag =  new SearchFrag();
                    searchFrag.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent, searchFrag).commit();
                    break;
                case R.id.searchFuncionary:
                    bundle.putString("typeSearch", "Functionary");
                    SearchFrag searchFragement =  new SearchFrag();
                    searchFragement.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent, searchFragement).commit();
                    break;
                case R.id.logout:
                    Intent myIntent = new Intent(this, LoginActivity.class);
                    startActivity(myIntent);
                    break;
                default:
                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new ListNotificationFrag()).commit();
            }
            menuItem.setChecked(true);
            setTitle(menuItem.getTitle());
        }
        mDrawer.closeDrawers();
    }


    private ActionBarDrawerToggle setupDrawerToggle(){
        return new ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void hideItemsByTypeUser(User user){
        Menu navMenu = nvDrawer.getMenu();
        switch (user.getTypeUser()){
            case "Student" :
                navMenu.findItem(R.id.searchStudent).setVisible(false);
                navMenu.findItem(R.id.searchFuncionary).setVisible(false);
                break;
            case "Attendant" :
                navMenu.findItem(R.id.searchStudent).setVisible(false);
                navMenu.findItem(R.id.searchFuncionary).setVisible(false);
                break;

        }
    }
}
