package com.example.chahat.anotode;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chahat on 9/9/16.
 */
public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {

    private List<Profile> List;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView FirstName, LastName, CollegeId;

        public MyViewHolder(View view) {
            super(view);
            FirstName = (TextView) view.findViewById(R.id.input_firstname);
            LastName = (TextView) view.findViewById(R.id.input_lastname);
            CollegeId = (TextView) view.findViewById(R.id.input_collegeid);
        }
    }


    public ProfileAdapter(List<Profile> InboxList) {
        this.List = InboxList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Profile inbox = List.get(position);
        holder.FirstName.setText(inbox.getFirstName());
        holder.LastName.setText(inbox.getLastName());
        holder.CollegeId.setText(inbox.getCollegeId());
    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}