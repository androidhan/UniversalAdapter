package com.hanshao.demo.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hanshao.demo.sample.holder.FristViewHolder;
import com.hanshao.demo.sample.provider.FristProvider;
import com.hanshao.universal.OnLoadMoreListener;
import com.hanshao.universal.UniversalAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * AUTHOR: hanshao
 * DATE: 17/2/28.
 * ACTION:单一item种类
 */

public class SingleItemActivity extends AppCompatActivity implements OnLoadMoreListener {


    private RecyclerView mRecyclerView;
    private UniversalAdapter mUniversalAdapter;
    private List<String> infos ;
    private int index = 0;

    public static void startActivity(Context context){
        Intent mIntent =  new Intent(context,SingleItemActivity.class);
        context.startActivity(mIntent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler_view);
        initView();
        initData();
    }

    private void initData() {
        //模拟数据
        mUniversalAdapter = new UniversalAdapter();
        infos = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            infos.add("单一的item类型,当前位置:"+i);
        }
        mUniversalAdapter.registerHolder("1",infos,new FristProvider(this,R.layout.holder_one));
        mUniversalAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mUniversalAdapter);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onLoadMore() {

        new Thread(){

            private List<String> mTempInfos;

            @Override
            public void run() {
                super.run();
                SystemClock.sleep(3000);
                index++;
                mTempInfos = new ArrayList<String>();
                for (int i = infos.size(); i <infos.size()+10 ; i++) {
                    mTempInfos.add("单一的item类型,当前位置:"+i);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(index == 6){
                            mUniversalAdapter.notifyMoreFinish(false);
                        }else{
                            mUniversalAdapter.addDatas("1", mTempInfos);
                            mUniversalAdapter.notifyMoreFinish(true);
                        }
                    }
                });
            }
        }.start();

    }
}
