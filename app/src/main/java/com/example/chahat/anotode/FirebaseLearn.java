package com.example.chahat.anotode;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseLearn extends AppCompatActivity {

    EditText etfirstname,etlastname,etid;
    TextView tvquery;
    ListView listView;


    String firebaseref = "https://anotode-142204.firebaseio.com/";
    Firebase ref;

    RecyclerView recyclerView;
    ProfileAdapter mAdapter;
    List<Profile> ProfileList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_learn);

        etfirstname = (EditText) findViewById(R.id.etfirstname);
        etlastname = (EditText) findViewById(R.id.etlastname);
        etid = (EditText) findViewById(R.id.etid);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);






        Firebase.setAndroidContext(this);
        ref = new Firebase(firebaseref);

        mAdapter = new ProfileAdapter(ProfileList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Profile inbox = ProfileList.get(position);
                Toast.makeText(getApplicationContext(), inbox.getFirstName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

       // getjain();
        //getkey();
      //  getall();
       // addnode();
       // addnodetoall();
        //getallkey();
      //  updatechild();
      //  updatechildcollegejain();
      //  removenode();





    }

  /*  public void adduserhandler(View view)
    {
        String firstname = etfirstname.getText().toString();
        String lastname = etlastname.getText().toString();
        String id = etid.getText().toString();




        Firebase alanRef = ref.child("AddUsers").push();

        alanRef.child("FirstName").setValue(firstname);
        alanRef.child("LastName").setValue(lastname);
        alanRef.child("CollegeId").setValue(id);

        Toast.makeText(this,"User Added..You can add multiple users...",Toast.LENGTH_SHORT).show();

        Log.v("firebase",alanRef+"");

    }*/

    public void adduserhandler(View view)
    {
        String firstname = etfirstname.getText().toString();
        String lastname = etlastname.getText().toString();
        String id = etid.getText().toString();




        Firebase alanRef = ref.child("AddUsers").child(id);


        alanRef.child("FirstName").setValue(firstname);
        alanRef.child("LastName").setValue(lastname);
        alanRef.child("CollegeId").setValue(id);

        Toast.makeText(this,"User Added..You can add multiple users...",Toast.LENGTH_SHORT).show();

        Log.v("firebase",alanRef+"");


    }

   /* public void addnodetoall()
    {

        Firebase objRef = ref.child("AddUsers");
        Query pendingTasks = objRef.orderByKey();
        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                    snapshot.getRef().child("College").setValue("IIITV");
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }*/

   /* public void addnode()
    {
        Firebase addnode = ref.child("AddUsers").child("201451042");

        addnode.child("RealName").setValue("Mayank");
    }*/

  /*  public void getjain()
    {
        Firebase objRef = ref.child("AddUsers");
        Query pendingTasks = objRef.orderByChild("LastName").equalTo("jain");

        Log.v("fireeee",pendingTasks+"");

        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                    Object value = snapshot.child("FirstName").getValue();
                    Log.v("vlaue",value+"");

                    querylist.add(value);

                    Log.v("querylist",querylist+"");

                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    */

   /* public void getkey()
    {
        Firebase objRef = ref.child("AddUsers");
        Query pendingTasks = objRef.orderByChild("FirstName").equalTo("mukesh");

        Log.v("fireeee",pendingTasks+"");

        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                    Object value = snapshot.getKey();
                    Log.v("vlaue",value+"");

                }


            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }*/

 /*   public void getall()
    {
        Firebase objRef = ref.child("AddUsers");
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
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }*/

   /* public void getallkey()
    {
        Firebase objRef = ref.child("AddUsers");
        Query pendingTasks = objRef.orderByKey();

        Log.v("fireeee",pendingTasks+"");

        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {

                    String  key = snapshot.getKey();
                    Log.v("allkey",key);

                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }*/

   /* public void updatechild()
    {

        Firebase alanRef = ref.child("AddUsers").child("201451042");

        Map<String, Object> updates = new HashMap<String,Object>();
        updates.put("FirstName", "Mayank");
        updates.put("LastName", "Pathshala");
        alanRef.updateChildren(updates);
    }*/

  /*  public void updatechildcollegejain()
    {
        Firebase objRef = ref.child("AddUsers");
        Query pendingTasks = objRef.orderByChild("LastName").equalTo("jain");

        Log.v("fireeee",pendingTasks+"");

        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                    snapshot.getRef().child("College").setValue("GEC");

                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }*/

  /*  public void removenode()
    {
        Firebase objRef = ref.child("AddUsers");
        Query pendingTasks = objRef.orderByChild("LastName").equalTo("jain");

        Log.v("fireeee",pendingTasks+"");

        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                    snapshot.getRef().child("College").removeValue();

                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }*/

}
