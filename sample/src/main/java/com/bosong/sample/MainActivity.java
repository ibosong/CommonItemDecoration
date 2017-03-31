package com.bosong.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bosong.sample.activity.LinearActivity;
import com.bosong.sample.activity.StaggeredRecyclerActivity;
import com.bosong.sample.activity.VerticalGridRecyclerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_vertical_recycler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVerticalRecyclerClick();
            }
        });

        findViewById(R.id.btn_staggered_recycler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStaggeredRecyclerClick();
            }
        });

        findViewById(R.id.btn_linear_recycler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLinearRecyclerClick();
            }
        });
    }

    private void onVerticalRecyclerClick(){
        Intent it = new Intent();
        it.setClass(this, VerticalGridRecyclerActivity.class);
        startActivity(it);
    }

    private void onStaggeredRecyclerClick(){
        Intent it = new Intent();
        it.setClass(this, StaggeredRecyclerActivity.class);
        startActivity(it);
    }

    private void onLinearRecyclerClick() {
        Intent it = new Intent();
        it.setClass(this, LinearActivity.class);
        startActivity(it);
    }
}
