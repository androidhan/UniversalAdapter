package com.hanshao.universal;

import android.content.Context;
import android.view.View;

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

    public UniversalViewHolder newInstance(){
        View v = View.inflate(mContext,mResId,null);
        return realNewInstance(v);
    }


    public abstract UniversalViewHolder<T> realNewInstance(View v);
}
