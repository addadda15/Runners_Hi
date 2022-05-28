package com.example.runnershi_final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private ArrayList<String> arrayList;
    private Context context;

    public CourseAdapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }


    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_course, parent, false);
        CourseViewHolder holder = new CourseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        String str = "코스" + (position + 1);
        holder.tv_course.setText(str);
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }


    public class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView tv_course;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_course = itemView.findViewById(R.id.courseTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getBindingAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }
}
