package com.hanshao.demo.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hanshao.demo.sample.bean.ItemInfo;
import com.hanshao.demo.sample.holder.HeaderHolder;
import com.hanshao.demo.sample.provider.FristNormalProvider;
import com.hanshao.demo.sample.provider.FristProvider;
import com.hanshao.universal.OnLoadMoreListener;
import com.hanshao.universal.UniversalAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * AUTHOR: hanshao
 * DATE: 17/2/28.
 * ACTION:
 */

public class MultipleActivity extends AppCompatActivity implements OnLoadMoreListener {

    private RecyclerView mRecyclerView;
    private int index = 0;
    private List<ItemInfo> otherInfos;
    private int[] ids = new int[]{R.mipmap.a,R.mipmap.b};
    private List<String> infos ;
    private UniversalAdapter mUniversalAdapter;
    private int mFlag;
    private final static String HEADERDATA= "我是头部的数据信息";

    public static void startActivity(Context context,int flag){
        Intent mIntent = new Intent(context,MultipleActivity.class);
        mIntent.putExtra("flag",flag);
        context.startActivity(mIntent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler_view);
        mFlag = getIntent().getIntExtra("flag",0);
        initView();
        initData();
    }



    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    private void initData() {
        otherInfos = new ArrayList<>();
        infos = new ArrayList<>();

        for (int i = 0; i <10 ; i++) {
            infos.add("第二种类型Item,当前位置:"+i);
            ItemInfo itemInfo =  new ItemInfo();
            itemInfo.content= "第二种类型Item,当前位置:"+i;
            itemInfo.imageId = ids[new Random().nextInt(2)];
            otherInfos.add(itemInfo);

        }
        mUniversalAdapter = new UniversalAdapter();
        if(mFlag == 1){
            mUniversalAdapter.setHeaderEnable(true);
            mUniversalAdapter.addHeaderViewHolder(new HeaderHolder(View.inflate(this,R.layout.holder_header,null)));
            mUniversalAdapter.setHeaderData(HEADERDATA);
        }

        mUniversalAdapter.registerHolder("1",infos,new FristProvider(this,R.layout.holder_one));
        mUniversalAdapter.registerHolder("2",otherInfos,new FristNormalProvider(this,R.layout.holder_normal_one));
        mUniversalAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mUniversalAdapter);
    }

    @Override
    public void onLoadMore() {
        new Thread(){

            private List<ItemInfo> mTempInfos;

            @Override
            public void run() {
                super.run();
                SystemClock.sleep(3000);
                index++;
                mTempInfos = new ArrayList<ItemInfo>();
                for (int i = otherInfos.size(); i <otherInfos.size()+10 ; i++) {
                    ItemInfo itemInfo =  new ItemInfo();
                    itemInfo.content= "第二种类型Item,当前位置:"+i;
                    itemInfo.imageId = ids[new Random().nextInt(2)];
                    mTempInfos.add(itemInfo);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(index == 6){
                            mUniversalAdapter.notifyMoreFinish(false);
                        }else{
                            mUniversalAdapter.addDatas("2",mTempInfos);
                            mUniversalAdapter.notifyMoreFinish(true);
                        }
                    }
                });

            }
        }.start();
    }
}
