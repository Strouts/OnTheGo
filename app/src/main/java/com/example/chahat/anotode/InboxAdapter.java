package com.example.chahat.anotode;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chahat on 3/9/16.
 */
public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyViewHolder> {

    private List<Inbox> InboxList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Name,Email,DOB;

        public MyViewHolder(View view) {
            super(view);
            Name = (TextView) view.findViewById(R.id.Name);
            Email = (TextView) view.findViewById(R.id.Email);
            DOB = (TextView) view.findViewById(R.id.DOB);
        }
    }


    public InboxAdapter(List<Inbox> InboxList) {
        this.InboxList = InboxList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inbox_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Inbox inbox = InboxList.get(position);
        holder.Name.setText(inbox.getName());
        holder.Email.setText(inbox.getEmail());
        holder.DOB.setText(inbox.getDOB());
    }

    @Override
    public int getItemCount() {
        return InboxList.size();
    }
}
