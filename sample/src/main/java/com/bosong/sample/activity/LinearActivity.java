package com.bosong.sample.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import com.bosong.commonitemdecoration.SCommonItemDecoration;
import com.bosong.sample.R;
import com.bosong.sample.Utils;
import com.bosong.sample.adapter.VerticalAdapter;
import com.bosong.sample.model.BrandData;

import java.util.ArrayList;
import java.util.List;

public class LinearActivity extends AppCompatActivity {
    private RecyclerView mBrandRecyclerView;
    VerticalAdapter mMyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);
        mBrandRecyclerView = (RecyclerView) findViewById(R.id.rv_brand_list);

        initNormalAdapter();
    }

    private void initNormalAdapter(){
        List<BrandData> data = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            BrandData brand = new BrandData("brand " + i);
            data.add(brand);
        }

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mBrandRecyclerView.setLayoutManager(manager);

        mMyAdapter = new VerticalAdapter(data);
        mBrandRecyclerView.setAdapter(mMyAdapter);

        int verticalSpace = Utils.dip2px(this, 15);
        int horizontalSpace = Utils.dip2px(this, 25);
        SparseArray<SCommonItemDecoration.ItemDecorationProps> propMap = new SparseArray<>();
        SCommonItemDecoration.ItemDecorationProps prop1 =
                new SCommonItemDecoration.ItemDecorationProps(horizontalSpace, verticalSpace, false, true);
        propMap.put(VerticalAdapter.TYPE_1, prop1);

        verticalSpace = Utils.dip2px(this, 5);
        horizontalSpace = Utils.dip2px(this, 5);
        SCommonItemDecoration.ItemDecorationProps prop2 =
                new SCommonItemDecoration.ItemDecorationProps(horizontalSpace, verticalSpace, true, true);
        propMap.put(VerticalAdapter.TYPE_2, prop2);
        mBrandRecyclerView.addItemDecoration(new SCommonItemDecoration(propMap));
    }
}
