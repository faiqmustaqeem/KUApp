package com.example.faiq.kuapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by faiq on 05/03/2018.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {

    List<PostsModel> list;
    public PostsAdapter(List<PostsModel> list)
    {
        this.list=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.posts_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PostsModel model=list.get(position);

        holder.tvPost.setText(model.getPost());

        String tagStr="";
        for (  int i=0 ; i < model.getTags().size() ; i++ )
        {
            tagStr+= "#"+model.getTags().get(i)+ " ";
        }
        holder.tags.setText(tagStr);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPost;
        TextView tags;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvPost=itemView.findViewById(R.id.tvPost);
            tags=itemView.findViewById(R.id.tags);
        }
    }
}
