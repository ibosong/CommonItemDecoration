package com.bosong.sample.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.bosong.commonitemdecoration.SCommonItemDecoration;
import com.bosong.sample.R;
import com.bosong.sample.Utils;
import com.bosong.sample.adapter.StaggeredAdapter;
import com.bosong.sample.adapter.VerticalAdapter;
import com.bosong.sample.model.BrandData;

import java.util.ArrayList;
import java.util.List;

public class StaggeredRecyclerActivity extends AppCompatActivity {
    private RecyclerView mBrandRecyclerView;
    private StaggeredAdapter mMyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggered_recycler);
        mBrandRecyclerView = findViewById(R.id.rv_brand_list);

        initNormalAdapter();
    }

    private void initNormalAdapter(){
        List<BrandData> data = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            BrandData brand = new BrandData("brand " + i);
            data.add(brand);
        }

        mMyAdapter = new StaggeredAdapter(data);
        mBrandRecyclerView.setAdapter(mMyAdapter);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mBrandRecyclerView.setLayoutManager(manager);

        mBrandRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    // 在item重排后，重新计算边框
                    mBrandRecyclerView.invalidateItemDecorations();
                }
            }
        });

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
