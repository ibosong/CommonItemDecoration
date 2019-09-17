package com.bosong.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bosong.commonitemdecoration.SCommonItemDecoration;
import com.bosong.sample.R;
import com.bosong.sample.Utils;
import com.bosong.sample.adapter.VerticalAdapter;
import com.bosong.sample.model.BrandData;

import java.util.ArrayList;
import java.util.List;

public class VerticalGridRecyclerActivity extends AppCompatActivity {
    private RecyclerView mBrandRecyclerView;
    VerticalAdapter mMyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_recycler);

        mBrandRecyclerView = findViewById(R.id.rv_brand_list);

        initNormalAdapter();
    }

    private void initNormalAdapter(){
        List<BrandData> data = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            BrandData brand = new BrandData("brand " + i);
            data.add(brand);
        }

        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == 1 || position == 2){
                    return 2;
                }
                if(position == 10){
                    return 2;
                }
                return 1;
            }
        });
        mBrandRecyclerView.setLayoutManager(manager);

        mMyAdapter = new VerticalAdapter(data);
        mBrandRecyclerView.setAdapter(mMyAdapter);

        mBrandRecyclerView.addItemDecoration(
                SCommonItemDecoration.builder()
                        .type(VerticalAdapter.TYPE_1)
                        .prop(Utils.dip2px(this, 15), Utils.dip2px(this, 25), true, true)
                        .buildType()
                        .type(VerticalAdapter.TYPE_2)
                        .prop(Utils.dip2px(this, 5), Utils.dip2px(this, 5), true, true)
                        .buildType()
                        .build()
        );
    }
}
