package com.bosong.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bosong.sample.model.BrandData;
import com.bosong.sample.viewholder.VerticalBrandViewHolder1;
import com.bosong.sample.viewholder.VerticalBrandViewHolder2;

import java.util.List;

/**
 * Created by boson on 2017/3/21.
 */

public class VerticalAdapter extends RecyclerView.Adapter {
    public static final int TYPE_1 = 0;
    public static final int TYPE_2 = 1;
    private List<BrandData> mData;
    public VerticalAdapter(List<BrandData> data){
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0){

            return new VerticalBrandViewHolder1(parent);
        }else{
            return new VerticalBrandViewHolder2(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mData == null || mData.size() <= position){
            return;
        }

        if(holder instanceof VerticalBrandViewHolder1){
            final VerticalBrandViewHolder1 holder1 = (VerticalBrandViewHolder1) holder;
            holder1.brandNameTv.setText(mData.get(position).brandName);
        }else{
            VerticalBrandViewHolder2 holder2 = (VerticalBrandViewHolder2) holder;
            holder2.brandNameTv.setText(mData.get(position).brandName);
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
}
