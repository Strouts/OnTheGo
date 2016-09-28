package com.example.chahat.anotode;

/**
 * Created by chahat on 30/8/16.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chahat.anotode.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by hp1 on 21-01-2015.
 */
public class Tab2 extends Fragment  {



    String Name,FEmail,DOB,id;

    public static final String MyPREFERENCES = "MyPrefs" ;


    SharedPreferences sharedpreferences;



    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    EditText firstname,lastname,email,password, collegeid,phone;
    Button register;

    String firebaseref = "https://anotode-142204.firebaseio.com/";

    String emailPattern = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab2, container, false);

        firstname = (EditText) v.findViewById(R.id.input_firstname);
        lastname = (EditText) v.findViewById(R.id.input_lastname);
        email = (EditText) v.findViewById(R.id.input_email);
        password = (EditText) v.findViewById(R.id.input_password);
        collegeid = (EditText) v.findViewById(R.id.input_collegeid);
        phone=(EditText) v.findViewById(R.id.input_number);

        firebaseAuth = FirebaseAuth.getInstance();





        progressDialog = new ProgressDialog(getContext());


        register = (Button) v.findViewById(R.id.btn_signup);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              final  int otp=gen();



                String number =phone.getText().toString();
                SmsManager.getDefault().sendTextMessage(number,null,otp+"",null,null);

                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Authentication");
                alert.setMessage("Enter OTP");

                final EditText input = new EditText(getContext());

                alert.setView(input);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String Yourvalue = input.getText().toString();

                        if (Yourvalue.equals(otp+""))
                        {
                            progressDialog.setMessage("registering user...");
                            progressDialog.show();

                            registeruser();
                        }
                        else {
                            Toast.makeText(getContext(),"Wrong OTP",Toast.LENGTH_SHORT).show();

                        }

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();







            }


        });

        return  v;

    }

    public void registeruser()
    {
        final String finame = firstname.getText().toString();
        final String laname = lastname.getText().toString();
        final String eemail = email.getText().toString();
        final String ppassword = password.getText().toString();
        final String id = collegeid.getText().toString();
        final String number = phone.getText().toString();

        if (eemail.matches(emailPattern)) {
            firebaseAuth.createUserWithEmailAndPassword(eemail, ppassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();

                        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("Name",finame+" "+laname);
                        editor.putString("Email",eemail);
                        editor.putString("CollegeId",id);
                        editor.putString("Phone",number);
                        editor.putInt("i",0);
                        editor.apply();


                        Intent intent = new Intent(getActivity(), InboxActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                        Toast.makeText(getContext(),"You have Successfully Registered",Toast.LENGTH_SHORT).show();

                        Firebase.setAndroidContext(getContext());
                        Firebase ref = new Firebase(firebaseref);




                        Firebase alanRef = ref.child("Users").child(id);

                        alanRef.child("FirstName").setValue(finame);
                        alanRef.child("LastName").setValue(laname);
                        alanRef.child("Email").setValue(eemail);
                        alanRef.child("Password").setValue(ppassword);
                        alanRef.child("CollegeId").setValue(id);
                        alanRef.child("Phone").setValue(number);
                        Log.v("firebase", alanRef + "");
                    } else {
                        if (task.getException().getMessage().equals("The email address is already in use by another account.")) {
                            Log.d("emailregisteredalready", "This email ID already used by someone else");
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "This Email is already Registerd", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


            });

        }
        else {
            progressDialog.dismiss();
            Toast.makeText(getContext(),"Invalid Email",Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public  int gen()
    {
        Random r = new Random(System.currentTimeMillis());
        return 10000+r.nextInt(20000);
    }



}

