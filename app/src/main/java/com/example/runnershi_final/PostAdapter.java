package com.example.runnershi_final;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<Post> arrayList;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private OnItemClickListener mListener1 = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener1 = listener;
    }

    public PostAdapter(ArrayList<Post> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_post, parent, false);
        PostViewHolder holder = new PostViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.tv_content.setText(arrayList.get(position).getContent());
        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.tv_Writer.setText(arrayList.get(position).getId());
        String str =String.valueOf(arrayList.get(position).getTag());
        switch (str) {
            case "0":
                str = "코스추천";
                break;
            case "1":
                str = "홍보";
                break;
            case "2":
                str = "자유";
                break;
            case "3":
                str = "같이뛰기";
                break;
            case "4":
                str = "거래";
                break;
        }
        holder.tv_tag.setText(str);
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_content;
        TextView tv_Writer;
        TextView tv_tag;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_content = itemView.findViewById(R.id.postContent);
            this.tv_title = itemView.findViewById(R.id.postTitle);
            this.tv_Writer = itemView.findViewById(R.id.postWriter);
            this.tv_tag = itemView.findViewById(R.id.postTag);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener1 != null) {
                            mListener1.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }
}
