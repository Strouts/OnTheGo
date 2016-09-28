package com.example.chahat.anotode;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chahat on 10/9/16.
 */
public class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.MyViewHolder> {

    private java.util.List<Permission> List;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView category,cid,template,status;

        public MyViewHolder(View view) {
            super(view);
            category = (TextView) view.findViewById(R.id.tvcatogary);
            cid = (TextView) view.findViewById(R.id.tvid);

            template = (TextView) view.findViewById(R.id.tvtemplate);
            status = (TextView) view.findViewById(R.id.tvstatus);
        }
    }


    public PermissionAdapter(List<Permission> InboxList) {
        this.List = InboxList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.permission_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Permission inbox = List.get(position);
        holder.category.setText(inbox.getCatogary());
        holder.cid.setText(inbox.getReciever());

        holder.template.setText(inbox.getTemplate());
        holder.status.setText(inbox.getStatus());
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public java.util.List<Permission> getList() {
        return List;
    }
}
