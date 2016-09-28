package com.example.chahat.anotode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cete.dynamicpdf.Document;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.Page;
import com.cete.dynamicpdf.PageOrientation;
import com.cete.dynamicpdf.PageSize;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.pageelements.Label;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllPermission extends AppCompatActivity {

    RecyclerView recyclerView;

    String ccccccname;

    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;

    String firebaseref = "https://anotode-142204.firebaseio.com/";
    Firebase ref;

    String cooid;

    PermissionAdapter permissionAdapter;

    List<Permission> list = new ArrayList<>();

    List<Permission> listt = new ArrayList<>();

    int pin;
    private static String FILE= Environment.getExternalStorageDirectory()+"/permission.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_permission);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),NextPermission.class));
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        progressDialog = new ProgressDialog(this);
        Firebase.setAndroidContext(this);
        ref = new Firebase(firebaseref);

        permissionAdapter = new PermissionAdapter(list);
        getallpermission();







        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(permissionAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {



                if (permissionAdapter.getList().get(position).getStatus().equals("Granted")) {
                    createPDF(position);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Permission has not yet been Granted",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    public void getallpermission()
    {
        progressDialog.setMessage("Processing...");
        progressDialog.show();

        cooid = sharedPreferences.getString("CollegeId",null);

        Firebase objRef = ref.child("Request");
        Query pendingTasks = objRef.orderByChild("Sender").equalTo(cooid);

        Log.v("fireeee",pendingTasks+"");

        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                    String  valuecat = snapshot.child("Category").getValue(String.class);
                    String valuelrec = snapshot.child("Reciever").getValue(String.class);
                    String valuecstatus = snapshot.child("Status").getValue(String.class);
                    String valuectemp = snapshot.child("Template").getValue(String.class);


                    Permission profile = new Permission(valuecat,valuelrec,valuecstatus,valuectemp);

                    list.add(profile);

                    permissionAdapter.notifyDataSetChanged();



                }

                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        progressDialog.dismiss();
    }

    public void createPDF(int pos){



        Document objDocument=new Document();
        objDocument.setCreator("IIIT-V");
        objDocument.setAuthor(permissionAdapter.getList().get(pos).getReciever());
        objDocument.setTitle("Permission for "+permissionAdapter.getList().get(pos).getCatogary());

        Page objPage=new Page(PageSize.LETTER, PageOrientation.PORTRAIT, 54.0f);
       String strText = "Dear Sir\n I, "+permissionAdapter.getList().get(pos).getSender()+
                " of 3rd Year, CS branch would like to seek permission to\n"
                +permissionAdapter.getList().get(pos).getCatogary()+"  Granted By:"+permissionAdapter.getList().get(pos).getReciever();


        Label objLabel = new Label(strText, 0, 0, 504, 100, Font.getHelvetica(), 18, TextAlign.CENTER);
        objPage.getElements().add(objLabel);
        objDocument.getPages().add(objPage);
        try{
            // Outputs the document to file
            objDocument.draw(FILE);
            Toast.makeText(this, "File has been written to :" + FILE,
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this,
                    "Error, unable to write to file\n" + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


}
