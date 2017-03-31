package com.bosong.sample.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.bosong.sample.model.BrandData;
import com.bosong.sample.R;

/**
 * Created by boson on 2017/3/12.
 */

public class VerticalBrandViewHolder2 extends SViewHolderBase<BrandData> {
    public TextView brandNameTv;

    public VerticalBrandViewHolder2(ViewGroup parent) {
        super(parent, R.layout.item_vertical_brand2);
        brandNameTv = findViewById(R.id.tv_preheat_brand);
    }
}
