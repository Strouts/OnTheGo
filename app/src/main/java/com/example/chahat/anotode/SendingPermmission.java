package com.example.chahat.anotode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendingPermmission extends AppCompatActivity {

    String firebaseref = "https://anotode-142204.firebaseio.com/";
    Firebase ref;

    int i;


    List<Profile> ProfileList = new ArrayList<>();
    ProfileAdapter mAdapter;
    RecyclerView recyclerView;

    ProgressDialog progressDialog;

    String permissiondetail,permissiontext,receiverid,senderid ;

    SharedPreferences sharedPreferences;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_permmission);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        senderid = sharedPreferences.getString("CollegeId",null);



        permissiondetail = getIntent().getExtras().getString("PermissionDetail");
        permissiontext = getIntent().getExtras().getString("PermissionText");



        Firebase.setAndroidContext(this);
        ref = new Firebase(firebaseref);

        progressDialog = new ProgressDialog(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        mAdapter = new ProfileAdapter(ProfileList);



        getalluser();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                progressDialog.setMessage("Sending...");
progressDialog.show();
                Profile inbox = ProfileList.get(position);

                receiverid = inbox.getCollegeId();

                sendrequesttodata();

                progressDialog.dismiss();

                Toast.makeText(getBaseContext(), "Request sent to "+inbox.getFirstName() , Toast.LENGTH_SHORT).show();

                finish();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    public void getalluser()
    {
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        Firebase objRef = ref.child("Users");
        Query pendingTasks = objRef.orderByKey();

        Log.v("fireeee",pendingTasks+"");

        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                    String  valuename = snapshot.child("FirstName").getValue(String.class);
                    String valuelname = snapshot.child("LastName").getValue(String.class);
                    String valuecid = snapshot.child("CollegeId").getValue(String.class);
                    Log.v("vlaue",valuename+"   "+valuelname+"  "+valuecid);

                    Profile profile = new Profile(valuename,valuelname,valuecid);

                    ProfileList.add(profile);

                    mAdapter.notifyDataSetChanged();



                }

                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void sendrequesttodata()
    {
        i = sharedPreferences.getInt("i",0);

        i++;

       Firebase alanRef = ref.child("Request").child(i+"");

        alanRef.child("Category").setValue(permissiondetail);
        alanRef.child("Template").setValue(permissiontext);
        alanRef.child("Sender").setValue(senderid);
        alanRef.child("Reciever").setValue(receiverid);
        alanRef.child("Status").setValue("Pending");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("i",i);
        editor.apply();






    }
}
