package com.bosong.sample.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.bosong.sample.model.BrandData;
import com.bosong.sample.R;

/**
 * Created by boson on 2017/3/10.
 */

public class VerticalBrandViewHolder1 extends SViewHolderBase<BrandData> {

    public volatile TextView brandNameTv;

    public VerticalBrandViewHolder1(ViewGroup itemView) {
        super(itemView, R.layout.item_vertical_brand1);
        brandNameTv = findViewById(R.id.tv_brand);
    }
}
