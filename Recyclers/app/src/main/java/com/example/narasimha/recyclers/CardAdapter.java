package com.example.narasimha.recyclers;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by narasimha on 26/3/18.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{
    String[] dataset;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linearow, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView vh = holder.mView;
        vh.setText(dataset[position]);
    }

    @Override
    public int getItemCount() {
        return dataset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mView;
        public ViewHolder(View mView){
            super(mView);
            this.mView = (TextView)mView.findViewById(R.id.text);
        }
    }

    public CardAdapter(String[] dataset){
        this.dataset = dataset;
    }
}
