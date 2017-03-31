package com.bosong.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.bosong.sample.model.BrandData;
import com.bosong.sample.viewholder.VerticalBrandViewHolder1;
import com.bosong.sample.viewholder.VerticalBrandViewHolder2;

import java.util.List;
import java.util.Random;

/**
 * Created by boson on 2017/3/22.
 */

public class StaggeredAdapter extends RecyclerView.Adapter {
    public static final int TYPE_1 = 0;
    public static final int TYPE_2 = 1;
    private List<BrandData> mData;
    private Random mRandom = new Random();
    public StaggeredAdapter(List<BrandData> data){
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0){
            VerticalBrandViewHolder1 holder1 = new VerticalBrandViewHolder1(parent);
            holder1.brandNameTv.getLayoutParams().height = getRandomIntInRange(200, 400);
            return holder1;
        }else{
            VerticalBrandViewHolder2 holder2 = new VerticalBrandViewHolder2(parent);
            holder2.brandNameTv.getLayoutParams().height = getRandomIntInRange(200, 400);
            return holder2;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mData == null || mData.size() <= position){
            return;
        }

        if(holder instanceof VerticalBrandViewHolder1){
            VerticalBrandViewHolder1 normalHolder = (VerticalBrandViewHolder1) holder;

            normalHolder.brandNameTv.setText(mData.get(position).brandName);
        }else{
            VerticalBrandViewHolder2 preheatHolder = (VerticalBrandViewHolder2) holder;
            preheatHolder.brandNameTv.setText(mData.get(position).brandName);
        }

        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        if(position == 0){
            layoutParams.setFullSpan(true);
        }else{
            layoutParams.setFullSpan(false);
        }
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(position < 10){
            return TYPE_1;
        }else{
            return TYPE_2;
        }
    }

    // Custom method to get a random number between a range
    protected int getRandomIntInRange(int max, int min){
        return mRandom.nextInt((max-min)+min)+min;
    }
}
