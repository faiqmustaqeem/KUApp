package com.example.faiq.kuapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faiq on 04/03/2018.
 */

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder> {

    List<AnnouncementModel> list =new ArrayList<>();
    public AnnouncementAdapter(List<AnnouncementModel> list)
    {
        this.list=list;
    }

    @Override
    public AnnouncementAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.announcement_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnnouncementAdapter.MyViewHolder holder, int position) {
        AnnouncementModel model=list.get(position);

        holder.question.setText(model.getQuestion());
        holder.answer.setText(model.getAnswer());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        TextView answer;
        public MyViewHolder(View itemView) {
            super(itemView);
            question=(TextView)itemView.findViewById(R.id.tvQuestion);
            answer=(TextView)itemView.findViewById(R.id.tvAnswer);
        }
    }
}
