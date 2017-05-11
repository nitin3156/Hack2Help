package com.complaints.jd.h2h;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sagar on 8/4/17.
 */

public class CenterBiddingAdapter extends RecyclerView.Adapter<CenterBiddingAdapter.MyViewHolder> {

    private List<CenterBiddingData> data;
    CenterBiddingData currentData;


    public CenterBiddingAdapter(List<CenterBiddingData> data){
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bid_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        currentData = data.get(position);
        holder.quantity.setText(currentData.getQuantity());
        holder.biddingPrice.setText(currentData.getQuantity());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView biddingPrice, quantity;

        MyViewHolder(View view) {
            super(view);
            biddingPrice = (TextView) view.findViewById(R.id.price);
            quantity = (TextView) view.findViewById(R.id.quantity);
        }
    }
}
