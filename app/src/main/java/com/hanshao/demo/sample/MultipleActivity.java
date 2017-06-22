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
import android.widget.Toast;

import com.hanshao.demo.sample.bean.ItemInfo;
import com.hanshao.demo.sample.holder.HeaderHolder;
import com.hanshao.demo.sample.provider.FristNormalProvider;
import com.hanshao.demo.sample.provider.TextProvider;
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
            infos.add("第一种类型Item,当前位置:"+i);
            ItemInfo itemInfo =  new ItemInfo();
            itemInfo.content= "第二种类型Item,当前位置:"+i;
            itemInfo.imageId = ids[new Random().nextInt(2)];
            otherInfos.add(itemInfo);

        }
        mUniversalAdapter = new UniversalAdapter(this);
        if(mFlag == 1){
            mUniversalAdapter.setHeaderEnable(true);
            mUniversalAdapter.addHeaderViewHolder(new HeaderHolder(View.inflate(this, R.layout.holder_header, null)), new UniversalAdapter.OnHeaderOnClickListener() {
                @Override
                public void onHeaderClick(Object data) {
                    Toast.makeText(MultipleActivity.this,"点击了头部",Toast.LENGTH_SHORT).show();
                }
            });
            mUniversalAdapter.setHeaderData(HEADERDATA);
        }

        mUniversalAdapter.registerHolder("1", infos, new TextProvider(R.layout.holder_text), new UniversalAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(int recyclerPosition, int dataPosition, String itemData) {
                Toast.makeText(MultipleActivity.this,"点击类型为:一,当前在RecyclerView的位置:"+recyclerPosition+",当前数据位置:"+dataPosition
                        +"内容:"+itemData,Toast.LENGTH_SHORT).show();
            }
        });
        mUniversalAdapter.registerHolder("2", otherInfos, new FristNormalProvider(R.layout.holder_normal_one), new UniversalAdapter.OnItemClickListener<ItemInfo>() {
            @Override
            public void onItemClick(int recyclerPosition, int dataPosition, ItemInfo itemData) {
                Toast.makeText(MultipleActivity.this,
                        "点击类型为:二,当前在RecyclerView的位置:"+recyclerPosition+",当前数据位置:"+dataPosition+",当前内容:"+itemData.content,Toast.LENGTH_SHORT).show();
            }
        });
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
                SystemClock.sleep(1000);
                index++;
                final List<ItemInfo> t = new ArrayList<ItemInfo>();
                final List<String> s= new ArrayList<String>();
                for (int i = mUniversalAdapter.getAllDataSize(); i <mUniversalAdapter.getAllDataSize()+10 ; i++) {
                    ItemInfo itemInfo =  new ItemInfo();
                    itemInfo.content= "第二种类型Item,当前位置:"+i;
                    itemInfo.imageId = ids[new Random().nextInt(2)];
                    s.add( itemInfo.content);
                    t.add(itemInfo);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(index == Integer.MAX_VALUE){
                            mUniversalAdapter.notifyMoreFinish(false);
                        }else{
//                            int k = 2+index;
//                            if(index % 2 == 1){
//                                mUniversalAdapter.registerHolder("1",s,new TextProvider(MultipleActivity.this,R.layout.holder_text));
//                                mUniversalAdapter.notifyMoreFinish(true);
//                            }else{
//                                mUniversalAdapter.registerHolder("2",t,new FristNormalProvider(MultipleActivity.this,R.layout.holder_normal_one));
//                                mUniversalAdapter.notifyMoreFinish(true);
//                            }
                            mUniversalAdapter.addDatas("2",t);
                            mUniversalAdapter.notifyMoreFinish(true);
                        }
                    }
                });

            }
        }.start();
    }
}
