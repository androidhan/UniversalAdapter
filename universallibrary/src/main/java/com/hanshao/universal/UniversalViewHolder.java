package com.hanshao.universal;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * AUTHOR: hanshao
 * DATE: 17/2/18.
 */

public abstract class UniversalViewHolder<T> extends RecyclerView.ViewHolder {

    protected T mData;
    private View mRootView;
    protected int mPosition;


    public UniversalViewHolder(View v) {
        super(v);
        mRootView = v;
        initView(v);
    }

    protected abstract void initView(View v);


    public View getContentView() {
        return mRootView;
    }


    public void setRawData(Object o) {
        setData((T) o);
    }


    public void setData(T data) {
        mData = data;
        refreshUi(data);
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }


    public abstract void refreshUi(T data);

}
