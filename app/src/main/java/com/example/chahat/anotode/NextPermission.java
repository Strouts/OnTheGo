package com.example.chahat.anotode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NextPermission extends AppCompatActivity {

    Spinner spinner;
    String permissiondetail,permissiontext ;
    EditText whatpermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_permission);
        spinner = (Spinner) findViewById(R.id.spinner);
        whatpermission = (EditText) findViewById(R.id.editText);



        List<String> categories = new ArrayList<String>();
        categories.add("Stay Out Overnight");
        categories.add("Organise an Event");
        categories.add("Leave");
        categories.add("Others");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                permissiondetail = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }





    public void listviewpopuphandler(View view)
    {
        permissiontext = whatpermission.getText().toString();

        Intent intent = new Intent(this,SendingPermmission.class);
        intent.putExtra("PermissionDetail",permissiondetail);
        intent.putExtra("PermissionText",permissiontext);
        startActivity(intent);
    }
}
