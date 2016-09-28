package com.example.chahat.anotode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class GrantDenied extends AppCompatActivity {

    String cat , rec,sta,tem,sen;

    String firebaseref = "https://anotode-142204.firebaseio.com/";
    Firebase ref;

    TextView tvcat,tvssen,tvsta,tvtem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grant_denied);


      cat=  getIntent().getExtras().getString("category",null);
        rec=getIntent().getExtras().getString("reciever",null);
       sta= getIntent().getExtras().getString("status",null);
        tem= getIntent().getExtras().getString("template",null);
        sen = getIntent().getExtras().getString("sender",null);


        Log.v("cat",cat);

        fillTextView(R.id.tvcategory,cat);
        fillTextView(R.id.tvsentby,sen);
        fillTextView(R.id.tvstatus,sta);
        fillTextView(R.id.tvtemplate,tem);




        Firebase.setAndroidContext(this);
        ref = new Firebase(firebaseref);



    }

    private void fillTextView (int id, String text) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(text);
    }

    public void btgranthandler(View view)
    {
        Firebase alanRef = ref.child("Request").child(sen);

        Map<String, Object> updates = new HashMap<String,Object>();
        updates.put("Status", "Granted");

        alanRef.updateChildren(updates);
        finish();

        Toast.makeText(this,"Permission given",Toast.LENGTH_SHORT).show();

    }

    public void btdeniedhandler(View view)
    {
        Firebase alanRef = ref.child("Request").child(sen);

        Map<String, Object> updates = new HashMap<String,Object>();
        updates.put("Status", "Denied");

        alanRef.updateChildren(updates);
        finish();

        Toast.makeText(this,"Permission not given",Toast.LENGTH_SHORT).show();
        finish();
    }
}
