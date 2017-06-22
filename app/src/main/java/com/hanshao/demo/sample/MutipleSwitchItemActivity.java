package com.hanshao.demo.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;

import com.hanshao.demo.sample.bean.ItemInfo;
import com.hanshao.demo.sample.provider.FristNormalProvider;
import com.hanshao.demo.sample.provider.TextProvider;
import com.hanshao.demo.sample.provider.FristWaterfalProvider;
import com.hanshao.demo.sample.provider.TextWaterfalProvider;
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

public class MutipleSwitchItemActivity extends AppCompatActivity implements OnLoadMoreListener {

    private Button mButtonSwitch;
    private RecyclerView mRecyclerView;
    private boolean isWatefal= false;
    private List<ItemInfo> infos;
    private int[] ids = new int[]{R.mipmap.a,R.mipmap.b};
    private UniversalAdapter mUniversalAdapter;
    private int index =0 ;


    private List<String> otherInfos = new ArrayList<>();

    public static void startActivity(Context context){
        Intent mIntent = new Intent(context,MutipleSwitchItemActivity.class);
        context.startActivity(mIntent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        initView();
        initData();
    }

    private void initData() {

        infos = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            ItemInfo itemInfo =  new ItemInfo();
            itemInfo.content= "多种类型的切换显示,当前位置:"+i;
            itemInfo.imageId = ids[new Random().nextInt(2)];
            infos.add(itemInfo);
            otherInfos.add("多类型的切换显示,当前位置:"+i);
        }
        mUniversalAdapter = new UniversalAdapter(this);
        mUniversalAdapter.registerHolder("2",otherInfos,new TextProvider(R.layout.holder_text),new TextWaterfalProvider(R.layout.holder_text_waterfal));
        mUniversalAdapter.registerHolder("1",infos,new FristNormalProvider(R.layout.holder_normal_one),new FristWaterfalProvider(R.layout.holder_waterfal_one));
        mUniversalAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mUniversalAdapter);
    }


    private void initView() {
        mButtonSwitch = (Button) findViewById(R.id.button_switch);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mButtonSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                if(isWatefal){
                    mButtonSwitch.setText("切换为瀑布流");
                    mUniversalAdapter.switchLayoutManager(new LinearLayoutManager(MutipleSwitchItemActivity.this));
                }else{
                    mButtonSwitch.setText("切换为普通");
                    mUniversalAdapter.switchLayoutManager(new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL));

                }
                isWatefal = !isWatefal;
            }
        });
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
                for (int i = infos.size(); i <infos.size()+10 ; i++) {
                    ItemInfo itemInfo =  new ItemInfo();
                    itemInfo.content= "单种类型的切换显示,当前位置:"+i;
                    itemInfo.imageId = ids[new Random().nextInt(2)];
                    mTempInfos.add(itemInfo);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(index == 6){
                            mUniversalAdapter.notifyMoreFinish(false);
                        }else{
                            mUniversalAdapter.addDatas("1",mTempInfos);
                            mUniversalAdapter.notifyMoreFinish(true);
                        }
                    }
                });

            }
        }.start();
    }
}
