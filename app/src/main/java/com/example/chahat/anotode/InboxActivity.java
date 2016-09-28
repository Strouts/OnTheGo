package com.example.chahat.anotode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class InboxActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Re {


    RecyclerView recyclerView;
    InboxAdapter mAdapter;
    List<Inbox> InboxList = new ArrayList<>();

    String Name,Email,Id;

    SharedPreferences sharedPreferences;

    FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        String  Name =  sharedPreferences.getString("Name",null);

        Email = sharedPreferences.getString("Email",null);
        Id = sharedPreferences.getString("CollegeId",null);

        Log.v("nameemail",Name+"" +Email);

        firebaseAuth = FirebaseAuth.getInstance();


        View view = navigationView.inflateHeaderView(R.layout.nav_header_inbox);
        TextView  nav_tv = (TextView) view.findViewById(R.id.nav_tv);
        TextView nav_tv1 = (TextView) view.findViewById(R.id.nav_tv1);

        nav_tv.setText(Name);
        nav_tv1.setText(Email);




        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new InboxAdapter(InboxList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Inbox inbox = InboxList.get(position);
                Toast.makeText(getApplicationContext(), inbox.getName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



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
        getMenuInflater().inflate(R.menu.inbox, menu);
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
        else if (id==R.id.action_signout)
        {
            finish();
            firebaseAuth.signOut();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        Log.v("idchoosse",id+"");

        if (id == R.id.nav_request)
        {
            Request request = new Request();

            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout,request).commit();




        }
        else if (id == R.id.nav_response)
        {

            Log.v("hii","jk");

            Response response = new Response();

            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout,response).commit();

        }
        else if (id == R.id.nav_permission)
        {

        }
       else if (id == R.id.nav_share)
        {

        } else if (id == R.id.nav_send)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}