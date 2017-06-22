package com.hanshao.universal;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AUTHOR: hanshao
 * DATE: 17/2/18.
 * ACTION:万能适配器
 */

public  class UniversalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnTryListener {

    public final static int TYPE_NORMAL = 0;
    public final static int TYPE_HEADER = 1;
    public final static int TYPE_FOOTER = 2;

    public final static int FAILED = -1;
    public final static int LOADING = 0;

    private final static String WATERFAL = "waterfal";

    private int mBaseType =2;
    private int mOtherItem = 0 ;
    private int mLoadMorePosition =0 ;

    private List<String> mKeys ;
    private List<List<?>> mTypeValues;
    private List<Integer>  mTypes ;
    private Map<String,UniversalProvider> mHolderMaps;
    private SparseArray<OnItemClickListener> mOnItemOnClickListeners;
    private OnHeaderOnClickListener mOnHeaderOnClickListener;

    private boolean mIsHeaderEnable= false ;
    private boolean mIsFooterEnable = true ;
    private boolean mIsLoadingMore = false ;

    private RecyclerView mRecyclerView;
    private FooterViewHolder mFooterViewHolder;
    private UniversalViewHolder mHeaderViewHolder;

    private OnLoadMoreListener mOnLoadMoreListener;
    private Object mHeaderData;
    private Activity mActivity;

    public UniversalAdapter(Activity activity) {
        mActivity = activity;
        mTypes = new ArrayList<>();
        mKeys = new ArrayList<>();
        mHolderMaps = new HashMap<>();
        mIsHeaderEnable = false;
        mTypeValues = new ArrayList<>();
        mOnItemOnClickListeners = new SparseArray<>();

    }

    public void registerHolder(@NonNull String key, @NonNull List<?> items, @NonNull UniversalProvider holder, @NonNull UniversalProvider waterfalHolder){

        mKeys.add(key);
        mTypeValues.add(items);
        if(mTypes.size() ==0){
            mTypes.add(TYPE_NORMAL);
        }
        else{
            mTypes.add(++mBaseType);
        }
        mTypes.add(++mBaseType);
        mHolderMaps.put(key,holder);
        mHolderMaps.put(key+WATERFAL,waterfalHolder);
    }

    public <E> void registerHolder(@NonNull String key, @NonNull List<?> items, @NonNull UniversalProvider holder,
                               @NonNull UniversalProvider waterfalHolder,@NonNull OnItemClickListener<E> onItemOnClickListener ){

        mKeys.add(key);
        mOnItemOnClickListeners.put(mTypeValues.size(),onItemOnClickListener);
        mTypeValues.add(items);
        if(mTypes.size() ==0){
            mTypes.add(TYPE_NORMAL);
        }
        else{
            mTypes.add(++mBaseType);
        }
        mTypes.add(++mBaseType);
        mHolderMaps.put(key,holder);
        mHolderMaps.put(key+WATERFAL,waterfalHolder);
    }


    public void registerHolder(@NonNull String key, @NonNull List<?> items, @NonNull UniversalProvider holder) {
        mKeys.add(key);
        mTypeValues.add(items);
        if (mTypes.size() == 0) {
            mTypes.add(TYPE_NORMAL);
        } else {
            if (mHolderMaps.get(key) != null) {
                int k = -1;
                int size = mKeys.size();
                for (int i = 0; i < size; i++) {
                    if (mKeys.get(i) == key) {
                        k = i;
                        break;
                    }
                }
                mTypes.add(mTypes.get(k));
            } else {
                mTypes.add(++mBaseType);
            }
        }
        mHolderMaps.put(key, holder);
    }


    public <E> void registerHolder(@NonNull String key, @NonNull List<?> items, @NonNull UniversalProvider holder,@NonNull OnItemClickListener<E> onItemOnClickListener) {
        mKeys.add(key);
        mOnItemOnClickListeners.put(mTypeValues.size(),onItemOnClickListener);
        mTypeValues.add(items);
        if (mTypes.size() == 0) {
            mTypes.add(TYPE_NORMAL);
        } else {
            if (mHolderMaps.get(key) != null) {
                int k = -1;
                int size = mKeys.size();
                for (int i = 0; i < size; i++) {
                    if (mKeys.get(i) == key) {
                        k = i;
                        break;
                    }
                }
                mTypes.add(mTypes.get(k));
            } else {
                mTypes.add(++mBaseType);
            }
        }
        mHolderMaps.put(key, holder);
    }


    public List<?> getDatas(@NonNull String key) {
        int index = mKeys.indexOf(key);
        return mTypeValues.get(index);
    }


    public void setData(@NonNull String key, @NonNull List<?> datas){
        int index = mKeys.indexOf(key);
        List items = mTypeValues.get(index);
        items.clear();
        items.addAll(datas);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
             super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (null != mOnLoadMoreListener && mIsFooterEnable && !mIsLoadingMore && dy > 0) {
                    int lastVisiblePosition = getLastVisiblePosition();
                    if (lastVisiblePosition + 1 == getItemCount()) {
                        setLoadMoring(true);
                        mLoadMorePosition = lastVisiblePosition;
                        if(mOnLoadMoreListener != null){
                            mOnLoadMoreListener.onLoadMore();
                        }
                    }
                }
            }
        });
    }

    public void addDatas(@NonNull String key, @NonNull List<?> datas){
        int index = mKeys.indexOf(key);
        List items = mTypeValues.get(index);
        items.addAll(datas);
    }



    private int getLastVisiblePosition() {
        int position;
        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager)mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) mRecyclerView.getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = mRecyclerView.getLayoutManager().getItemCount() - 1;
        }
        return position;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {;

        int type = getItemViewType(position);
        if(type == TYPE_HEADER){

            if(mHeaderViewHolder != null && mHeaderData!= null){
                mHeaderViewHolder.setRawData(mHeaderData);
                    doHeaderOnClick(mHeaderViewHolder.getContentView(),mOnHeaderOnClickListener,mHeaderData);
            }

        } else if (type != TYPE_FOOTER && type != TYPE_HEADER) {

            UniversalViewHolder realHolder = (UniversalViewHolder) holder;
            realHolder.setPosition(position);
            Object data = null;
            int dataPosition = -1;
            int indexListener = -1;
            if(mKeys.size() *2 == mTypes.size()){

                int size = mTypes.size();
                int index = -1;
                for (int i = 0; i < size; i++) {

                    if (mTypes.get(i) == type  ) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {

                    index /= 2;

                    int count = mOtherItem;
                    for (int i = 0; i < index;i++ ) {
                        count += mTypeValues.get(i).size();
                    }

                    int realPosition = position - count;
                    dataPosition = realPosition;
                    indexListener = index;
                    data= mTypeValues.get(index).get(realPosition);
                }

            }else{

                int newCount = 0;
                int realPosition = 0;
                int index = -1;
                for (int i = 0; i <mTypeValues.size() ; i++) {

                     if(position - mOtherItem == 0) {
                         index = 0;
                         realPosition = 0;
                         break;
                     }

                    newCount += mTypeValues.get(i).size();

                    if(i ==0 && position -mOtherItem < newCount){
                        index=i;
                        realPosition = position -mOtherItem;
                        break;
                    }

                    if( i+1 != mTypeValues.size()){
                        if(newCount < position + 1- mOtherItem && position + 1 -mOtherItem < newCount + mTypeValues.get(i+1).size()){
                            realPosition = position - newCount - mOtherItem;
                            index = i + 1;
                            break;
                        }else if(newCount -1 == position - mOtherItem){
                            index = i;
                            realPosition = mTypeValues.get(index).size()-1;
                            break;

                        }else if(newCount -1 +mTypeValues.get(i+1).size() == position - mOtherItem){
                            //临界点
                            index = i+1;
                            realPosition = mTypeValues.get(index).size()-1;
                            break;
                        }
                    }else{
                        index = i;
                        realPosition =  position- mOtherItem -  (newCount - mTypeValues.get(index).size());
                        break;
                    }
                }
                dataPosition  = realPosition;
                indexListener  = index;
                data =mTypeValues.get(index).get(realPosition);
            }
            realHolder.setRawData(data);

            doItemOnClick(mOnItemOnClickListeners,indexListener,realHolder.getContentView(),position,dataPosition,data);
        }

    }

    private void doHeaderOnClick(View v, final OnHeaderOnClickListener onHeaderOnClickListener, final Object headerData) {
        if(v!=null && onHeaderOnClickListener!=null){
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onHeaderOnClickListener.onHeaderClick(headerData);
                }
            });
        }
    }

    public void doItemOnClick(SparseArray<OnItemClickListener> onItemOnClickListeners,int index, View v, final int recyclerPosition, final int dataPoisiton, final Object data){

        if(onItemOnClickListeners.size() != 0 && index != -1){
            final OnItemClickListener onItemOnClickListener = onItemOnClickListeners.get(index);
            if(onItemOnClickListener != null &&v!=null && recyclerPosition!=-1 && dataPoisiton != -1 && data!=null){
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemOnClickListener.onItemClick(recyclerPosition,dataPoisiton,data);
                    }
                });
            }
        }

    }



    public interface OnHeaderOnClickListener<E>{
        void onHeaderClick(E itemData);
    }

    public interface OnItemClickListener<E>{
        void onItemClick(int recyclerPosition,int dataPosition,E itemData);
    }


    public void switchLayoutManager(RecyclerView.LayoutManager layoutManager) {
        int firstVisiblePosition = getFirstVisiblePosition();
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.getLayoutManager().scrollToPosition(firstVisiblePosition);
    }


    private int getFirstVisiblePosition() {
        int position;
        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        } else if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        } else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) mRecyclerView.getLayoutManager();
             //找到显示的位置,因为瀑布流是有不同的布局
            int[] lastPositions = layoutManager.findFirstVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMinPositions(lastPositions);
        } else {
            position = 0;
        }
        return position;
    }


    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }



    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        this.mOnLoadMoreListener = listener;
    }

    private int getMinPositions(int[] positions) {
        int size = positions.length;
        int minPosition = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            minPosition = Math.min(minPosition, positions[i]);
        }
        return minPosition;
    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return mHeaderViewHolder;
        }
        if (viewType == TYPE_FOOTER) {
            mFooterViewHolder = new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_foot,parent,false));
            mFooterViewHolder.setOnTryListener(this);
            return mFooterViewHolder;
        }
        else {

            int size = mTypes.size();
            int index = -1 ;
            for (int i = 0; i < size; i++) {
                if(viewType == mTypes.get(i)){
                    index = i;
                    break;
                }
            }
            if(index != -1){
                String key ;
                if(mKeys.size()*2 == mTypes.size()){

                    key = mKeys.get(index/2);
                    if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                        return mHolderMaps.get(key).newInstance(mActivity,parent);
                    }
                    else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                        return mHolderMaps.get(key+WATERFAL).newInstance(mActivity,parent);
                    }
                }
                else{
                    key = mKeys.get(index);
                    return mHolderMaps.get(key).newInstance(mActivity,parent);
                }
            }
            throw new RuntimeException();
        }
    }



    @Override
    public int getItemViewType(int position) {

        int headerPosition = 0;
        int footerPosition = getItemCount() - 1;

        if (headerPosition == position && mIsHeaderEnable && mHeaderViewHolder != null) {
            return TYPE_HEADER;
        }

        if (footerPosition == position && mIsFooterEnable) {
            return TYPE_FOOTER;
        }

        int size =mKeys.size();
        int tempCount= 0;
        for (int i = 0; i < size ; i++) {

            tempCount += mTypeValues.get(i).size();
            if(position >= mOtherItem && position < tempCount+mOtherItem){

                if(mKeys.size()*2 == mTypes.size()){
                    if(mRecyclerView.getLayoutManager() instanceof  LinearLayoutManager ){
                        return mTypes.get(i*2+1);
                    }else if(mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager){
                        return mTypes.get(i*2);
                    }
                }else{
                    return mTypes.get(i);
                }
            }
            else{
                continue;
            }
        }
        throw new RuntimeException();
    }


    @Override
    public int getItemCount() {

        if(mKeys.size() *2 != mTypes.size() && mKeys.size() !=  mTypes.size() ){
            //异常抛出
            throw new RuntimeException("mKeys.size()* 2 not equal +mTypes.size()  or" +" mKeys.size() not equals mTypes.size()");
        }

        int size = mTypeValues.size();
        int count= 0;
        for (int i = 0; i <size ; i++) {
            count += mTypeValues.get(i).size();
        }

        if (mIsFooterEnable) {
            count++;
        }

        if (mIsHeaderEnable) {
            count++;
            mOtherItem=1;
        }

        return count;
    }

    public int getAllDataSize(){
        int size = mTypeValues.size();
        int count= 0;
        for (int i = 0; i <size ; i++) {
            count += mTypeValues.get(i).size();
        }
        return count;
    }


    public void setHeaderEnable(boolean enable) {
        mIsHeaderEnable = enable;
    }

    public void addHeaderViewHolder(UniversalViewHolder headerViewHolder) {
        this.mHeaderViewHolder = headerViewHolder;
    }

    public <E> void addHeaderViewHolder(UniversalViewHolder headerViewHolder,OnHeaderOnClickListener<E> onHeaderOnClickListener) {
        this.mHeaderViewHolder = headerViewHolder;
        this.mOnHeaderOnClickListener = onHeaderOnClickListener;
    }


    public void setLoadMoring(boolean loadMoring){
        this.mIsLoadingMore  = loadMoring;
    }

    public void setAutoLoadMoreEnable(boolean autoLoadMore) {
        mIsFooterEnable = autoLoadMore;
    }



    public void notifyMoreFinish(boolean hasMore) {
        setAutoLoadMoreEnable(hasMore);
        notifyItemRemoved(mLoadMorePosition);
        mIsLoadingMore = false;
    }

    public void setHeaderData(Object headerData){
        this.mHeaderData = headerData;
        if(mRecyclerView!=null){
            notifyDataSetChanged();
        }
    }

    public Object getHeaderData(){
        return this.mHeaderData;
    }

    public void setLoadMoreFailed(){
        if(mFooterViewHolder != null){
            mFooterViewHolder.setCurrentStatus(FAILED);
        }
    }

    @Override
    public void onTry() {
        mFooterViewHolder.setCurrentStatus(LOADING);
        if(mOnLoadMoreListener!=null){
            mOnLoadMoreListener.onLoadMore();
        }
    }



}




