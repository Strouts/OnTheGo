package com.example.chahat.anotode;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class PostQueryActivity extends AppCompatActivity {

    EditText etpost;
    String post,myid;

    String firebaseref = "https://anotode-142204.firebaseio.com/";
    Firebase ref;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_query);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        myid = sharedPreferences.getString("CollegeId",null);

        Firebase.setAndroidContext(this);
        ref = new Firebase(firebaseref);

        etpost = (EditText) findViewById(R.id.etpost);
    }


    public void posthandler(View view)
    {
        post = etpost.getText().toString();

        allquery();
    }

    public void allquery()
    {
        Firebase objRef = ref.child("Query");
        Query pendingTasks = objRef.orderByKey();

        Log.v("fireeee",pendingTasks+"");

        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {





                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }
}
