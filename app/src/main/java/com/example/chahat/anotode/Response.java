package com.example.chahat.anotode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

/**
 * Created by chahat on 11/9/16.
 */
public class Response extends Fragment {

    ProgressDialog progressDialog;

    String firebaseref = "https://anotode-142204.firebaseio.com/";
    Firebase ref;

    ResponseAdapter mAdapter;
    List<Permission> responseist = new ArrayList<Permission>();

    List<Permission> listt = new ArrayList<>();


    Permission permission=new Permission();

    SharedPreferences sharedPreferences;

    String  valuecat,valuerec,valuesen,valuesta,valuetem;

    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.response, container, false);

        progressDialog = new ProgressDialog(getContext());
progressDialog.setMessage("Processing....");
        progressDialog.show();

        sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        Firebase.setAndroidContext(getContext());
        ref = new Firebase(firebaseref);


        mAdapter = new ResponseAdapter(responseist);

        getresponselist();

        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);



        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(final View view, final int position) {

               listt= mAdapter.getList();

                Log.v("listtttt",listt.get(position).getCatogary());

                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Permission");
                alert.setMessage("Are you sure you want to grant a Permission");

                alert.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        Firebase objRef = ref.child("Request");
                        Query pendingTasks = objRef.orderByChild("Category").equalTo(listt.get(position).getCatogary());

                        Log.v("fireeee",pendingTasks+"");

                        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot tasksSnapshot) {
                                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                                    snapshot.getRef().child("Status").setValue("Granted");

                                }
                            }
                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                System.out.println("The read failed: " + firebaseError.getMessage());
                            }
                        });
                    }
                });

                alert.setNegativeButton("Denied", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        Firebase objRef = ref.child("Request");
                        Query pendingTasks = objRef.orderByChild("Category").equalTo(listt.get(position).getCatogary());

                        Log.v("fireeee",pendingTasks+"");

                        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot tasksSnapshot) {
                                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                                    snapshot.getRef().child("Status").setValue("Denied");

                                }
                            }
                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                System.out.println("The read failed: " + firebaseError.getMessage());
                            }
                        });
                    }




                });

                alert.show();





            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void getresponselist()
    {
        String myid = sharedPreferences.getString("CollegeId",null);

        Firebase objRef = ref.child("Request");
        Query pendingTasks = objRef.orderByChild("Reciever").equalTo(myid);

        Log.v("fireeee",pendingTasks+"");

        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {

                      valuecat = snapshot.child("Category").getValue(String.class);
                     valuerec = snapshot.child("Reciever").getValue(String.class);
                     valuesen = snapshot.child("Sender").getValue(String.class);
                     valuesta= snapshot.child("Status").getValue(String.class);
                     valuetem = snapshot.child("Template").getValue(String.class);

                    Permission profile = new Permission(valuecat,valuerec,valuesta,valuetem,valuesen);

                    responseist.add(profile);

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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}