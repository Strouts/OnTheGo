package com.example.chahat.anotode;

/**
 * Created by chahat on 30/8/16.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by hp1 on 21-01-2015.
 */
public class Tab1 extends Fragment {

    EditText etemail,etpassword;
    ProgressDialog progressDialog;
    Button login;

    FirebaseAuth firebaseAuth;
    String firebaseref = "https://anotode-142204.firebaseio.com/";
    Firebase ref;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab1,container,false);

        etemail = (EditText) v.findViewById(R.id.input_email);
        etpassword = (EditText) v.findViewById(R.id.input_password);
        login = (Button) v.findViewById(R.id.btn_signup);

        progressDialog =new ProgressDialog(getContext());

        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                progressDialog.setMessage("Logging In...");
                progressDialog.show();
                userLogIn();
            }
        });

        return v;
    }

    public void userLogIn()
    {
       final String email = etemail.getText().toString();
        String password  = etpassword.getText().toString();

        Firebase.setAndroidContext(getContext());
        ref = new Firebase(firebaseref);

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if (task.isSuccessful()) {
                            //start the profile activity
                            Intent intent = new Intent(getActivity(), InboxActivity.class);
                            intent.putExtra("profileInfoName", "");
                            intent.putExtra("profileInfoEmail", email);
                            startActivity(intent);
                            getActivity().finish();
                            Toast.makeText(getContext(),"Logged In",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(),"Invalid Email,Password Or Not Registerd yet",Toast.LENGTH_SHORT).show();
                        }

                    }





                });


    }
}