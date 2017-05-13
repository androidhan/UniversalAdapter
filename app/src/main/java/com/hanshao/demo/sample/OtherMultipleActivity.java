package com.hanshao.demo.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.hanshao.demo.sample.bean.ItemInfo;
import com.hanshao.demo.sample.holder.HeaderHolder;
import com.hanshao.demo.sample.provider.FourProvider;
import com.hanshao.demo.sample.provider.FristNormalProvider;
import com.hanshao.demo.sample.provider.OneProvider;
import com.hanshao.demo.sample.provider.TextProvider;
import com.hanshao.demo.sample.provider.ThreeProvider;
import com.hanshao.demo.sample.provider.TowProvider;
import com.hanshao.universal.OnLoadMoreListener;
import com.hanshao.universal.UniversalAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * AUTHOR: hanshao
 * DATE: 17/4/10.
 * ACTION:
 */

public class OtherMultipleActivity extends AppCompatActivity implements OnLoadMoreListener {
    private RecyclerView mRecyclerView;
    private int index = 0;
    private List<String> infos ;
    private UniversalAdapter mUniversalAdapter;
    private List<String> mOtherInfos;

    public static void startActivity(Context context){
        Intent mIntent = new Intent(context,OtherMultipleActivity.class);
        context.startActivity(mIntent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler_view);
        initView();
        initData();
    }



    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    private void initData() {


        mUniversalAdapter = new UniversalAdapter();

        for (int i = 0; i <6 ; i++) {
            register();
        }

        mUniversalAdapter.setOnLoadMoreListener(this);

        mRecyclerView.setAdapter(mUniversalAdapter);
    }

    private void register() {
        infos = new ArrayList<>();
        mOtherInfos = new ArrayList<>();
        for (int i = 0; i <1 ; i++) {
            infos.add("date:"+System.currentTimeMillis());
            mOtherInfos.add("date:"+System.currentTimeMillis());
        }

        mUniversalAdapter.registerHolder("1",infos,new OneProvider(this,R.layout.holder_one));
        mUniversalAdapter.registerHolder("2",mOtherInfos,new TowProvider(this,R.layout.holder_tow));
    }

    @Override
    public void onLoadMore() {
        new Thread(){


            @Override
            public void run() {
                super.run();
                SystemClock.sleep(800);
                index++;
                final List<String> s= new ArrayList<String>();
                    s.add("date:"+System.currentTimeMillis());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(index == Integer.MAX_VALUE){
                            mUniversalAdapter.notifyMoreFinish(false);
                        }else{
                            int r = new Random().nextInt(4);
                            if(r % 4 == 0){
                                mUniversalAdapter.registerHolder("1",s,new OneProvider(OtherMultipleActivity.this,R.layout.holder_one));
                                mUniversalAdapter.registerHolder("2",s,new TowProvider(OtherMultipleActivity.this,R.layout.holder_tow));
                                mUniversalAdapter.registerHolder("3",s,new ThreeProvider(OtherMultipleActivity.this,R.layout.holder_three));
                                mUniversalAdapter.registerHolder("4",s,new FourProvider(OtherMultipleActivity.this,R.layout.holder_four));
                                mUniversalAdapter.notifyMoreFinish(true);
                            }else if(r % 4 == 1){
                                mUniversalAdapter.registerHolder("4",s,new FourProvider(OtherMultipleActivity.this,R.layout.holder_four));
                                mUniversalAdapter.registerHolder("3",s,new ThreeProvider(OtherMultipleActivity.this,R.layout.holder_three));
                                mUniversalAdapter.registerHolder("1",s,new OneProvider(OtherMultipleActivity.this,R.layout.holder_one));
                                mUniversalAdapter.registerHolder("2",s,new TowProvider(OtherMultipleActivity.this,R.layout.holder_tow));
                                mUniversalAdapter.notifyMoreFinish(true);
                            }else if(r % 4 == 2){
                                mUniversalAdapter.registerHolder("3",s,new ThreeProvider(OtherMultipleActivity.this,R.layout.holder_three));
                                mUniversalAdapter.registerHolder("2",s,new TowProvider(OtherMultipleActivity.this,R.layout.holder_tow));
                                mUniversalAdapter.registerHolder("4",s,new FourProvider(OtherMultipleActivity.this,R.layout.holder_four));
                                mUniversalAdapter.registerHolder("1",s,new OneProvider(OtherMultipleActivity.this,R.layout.holder_one));
                                mUniversalAdapter.notifyMoreFinish(true);
                            }else{
                                mUniversalAdapter.registerHolder("3",s,new ThreeProvider(OtherMultipleActivity.this,R.layout.holder_three));
                                mUniversalAdapter.registerHolder("1",s,new OneProvider(OtherMultipleActivity.this,R.layout.holder_one));
                                mUniversalAdapter.registerHolder("2",s,new TowProvider(OtherMultipleActivity.this,R.layout.holder_tow));
                                mUniversalAdapter.registerHolder("4",s,new FourProvider(OtherMultipleActivity.this,R.layout.holder_four));
                                mUniversalAdapter.notifyMoreFinish(true);
                            }
                        }
                    }
                });

            }
        }.start();
    }
}

