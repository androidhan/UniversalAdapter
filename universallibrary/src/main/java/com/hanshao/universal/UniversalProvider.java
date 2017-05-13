package com.hanshao.universal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * AUTHOR: hanshao
 * DATE: 17/2/27.
 */

public abstract class UniversalProvider<T> {

    private UniversalViewHolder<T> mUniversalViewHolder;
    private Context mContext;
    private int mResId;

    public UniversalProvider(Context context, int resId){
        mContext = context;
        mResId = resId;
    }

    public UniversalViewHolder newInstance(ViewGroup parent){
        View v = LayoutInflater.from(mContext).inflate(mResId,parent,false);
        return realNewInstance(v);
    }

    public abstract UniversalViewHolder<T> realNewInstance(View v);
}
