package com.example.runnershi_final;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private ArrayList<Crew> arrayList;
    private Context context;

    private OnItemClickListener mlistener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mlistener  = listener;
    }

    public CustomAdapter(ArrayList<Crew> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_crew,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tv_name.setText(arrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_name = itemView.findViewById(R.id.tv_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition(); // 뷰 객체마다 고유한 번호가 있음
                    if(mlistener != null){
                        mlistener.onItemClick(view, position); //인터페이스 구현부를 호출함
                    }
                }
            });
        }
    }
}

