package com.example.chahat.anotode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by chahat on 10/9/16.
 */
public class Request extends Fragment
{

    Button permission,postquery,requestdocument,complaint;
    ProgressDialog progressDialog;

    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


      View v =    inflater.inflate(R.layout.request,container,false);

        progressDialog = new ProgressDialog(getContext());

        permission = (Button) v.findViewById(R.id.btn_permission);
        postquery = (Button) v.findViewById(R.id.btn_postquery);
        requestdocument = (Button) v.findViewById(R.id.btn_requestpermission);
        complaint = (Button) v.findViewById(R.id.btn_complaint);

        permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                progressDialog.setMessage("Processing...");
                progressDialog.show();
                gettingpermission();
            }
        });

        postquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),PostQueryActivity.class);
                startActivity(intent);
            }
        });

        requestdocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),RequestDocumentActivity.class);
                startActivity(intent);
            }
        });

        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ComplaintActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void gettingpermission()
    {
        progressDialog.dismiss();
        startActivity(new Intent(getContext(),AllPermission.class));
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
