package com.example.runnershi_final;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private ArrayList<String> arrayList;

    public MemberAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_member,parent,false);
        MemberViewHolder holder = new MemberViewHolder(view);

        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        //holder.iv_member.setImageResource(arrayList.get(position).getIv_member());
        holder.tv_member.setText(arrayList.get(position));


    }


    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder {

        //protected ImageView iv_member;
        protected TextView tv_member;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.iv_member = itemView.findViewById(R.id.iv_member);
            this.tv_member = itemView.findViewById(R.id.tv_member);
        }
    }
}