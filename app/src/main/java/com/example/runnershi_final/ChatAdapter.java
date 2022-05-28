package com.example.runnershi_final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Chat> cList = null;

    public ChatAdapter(ArrayList<Chat> cList) {
        this.cList = cList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == 0) {
            view = inflater.inflate(R.layout.list_left_chat, parent, false);
            return new LeftViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.list_right_chat, parent, false);
            return new RightViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof LeftViewHolder) {
            ((LeftViewHolder) viewHolder).textv_nicname.setText(cList.get(position).getId());
            ((LeftViewHolder) viewHolder).textv_msg.setText(cList.get(position).getContent());
        } else {
            ((RightViewHolder) viewHolder).textv_msg.setText(cList.get(position).getContent());
        }

    }

    @Override
    public int getItemCount() {
        return cList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return cList.get(position).getType();
    }


    public class LeftViewHolder extends RecyclerView.ViewHolder {
        TextView textv_nicname;
        TextView textv_msg;

        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            textv_nicname = (TextView) itemView.findViewById(R.id.textv_nicname);
            textv_msg = (TextView) itemView.findViewById(R.id.textv_msg);

        }
    }

    public class RightViewHolder extends RecyclerView.ViewHolder {
        TextView textv_msg;

        public RightViewHolder(@NonNull View itemView) {
            super(itemView);
            textv_msg = (TextView) itemView.findViewById(R.id.textv_msg);
        }
    }

}