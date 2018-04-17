package com.softmedialtda.softmediaphotoapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.softmedialtda.softmediaphotoapp.models.Notification;
import com.softmedialtda.softmediaphotoapp.models.User;
import com.softmedialtda.softmediaphotoapp.util.NotificationListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import static com.softmedialtda.softmediaphotoapp.util.Common.convertJsonObjectToUserObject;
import static com.softmedialtda.softmediaphotoapp.util.Common.getListNotification;
import static com.softmedialtda.softmediaphotoapp.util.Connection.sendPost;
import static com.softmedialtda.softmediaphotoapp.util.Constants.DOMAIN;

public class NotificationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private NavigationView navigationView;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        User user = (User) getIntent().getSerializableExtra("user");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.maincontent);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        TextView nameUserNav = (TextView) hView.findViewById(R.id.nameUserNav);
        TextView userNameNav = (TextView) hView.findViewById(R.id.userNameNav);
        ImageView img = (ImageView) hView.findViewById(R.id.imageProfileView);
        if (user.getImageProfile().equals("")) {
            img.setImageResource(R.mipmap.photodefault);
        }else{
            try {
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(user.getImageProfile()).getContent());
                img.setImageBitmap(bitmap);
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        userNameNav.setText(user.getUsername());
        nameUserNav.setText(user.getFirstName()+ " "+user.getSurname());
        navigationView.setNavigationItemSelectedListener(this);


    }

    public static class NotificationFragment extends Fragment{
        String urlNotifications = DOMAIN+"notifications";
        User user = new User();
        ListView notificationListView;

        int selectedPosition;

        public NotificationFragment() {
        }

        public static NotificationFragment newInstance(int section){
            NotificationFragment fragment = new NotificationFragment();
            Bundle args = new Bundle();
            args.putInt("num", section);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            selectedPosition = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_notification, container, false);

            notificationListView = (ListView) rootView.findViewById(R.id.notificationListViewFragment);
            notificationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object o = notificationListView.getItemAtPosition(position);
                    Notification notSelected = (Notification) o;

                    final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle(notSelected.getSubject());
                    alert.setMessage(notSelected.getContent()).setPositiveButton(R.string.closeDialogNotification, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }
            });
            user = (User) getActivity().getIntent().getSerializableExtra("user");
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            new HttpAsyncTask().execute(urlNotifications);
        }

        private class HttpAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                JSONObject paramaters = new JSONObject();
                try {
                    paramaters.accumulate("ID_PER", user.getIdTypeUser());
                    paramaters.accumulate("ID_INSTITUCION", user.getIdInstitution());
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                return sendPost(urlNotifications,paramaters);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                if (!s.equals("")){
                    try{
                        JSONArray response = new JSONArray(s);
                        ArrayList<Notification> list = getListNotification(response,selectedPosition);

                        NotificationListAdapter nla = new NotificationListAdapter(getActivity(),list);
                        notificationListView.setAdapter(nla);

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_profile, menu);
        return true;
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

        if (id == R.id.showProfileComplete) {
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainContainer);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.activity_profile,null);
            mainLayout.removeAllViews();
            mainLayout.addView(layout);

        }
        else if (id == R.id.notiList) {

        }/* else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
