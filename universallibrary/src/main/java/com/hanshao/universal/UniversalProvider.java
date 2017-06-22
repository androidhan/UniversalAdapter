package com.hanshao.universal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * AUTHOR: hanshao
 * DATE: 17/2/27.
 */

public abstract class UniversalProvider {

    private int mResId;

    public UniversalProvider(int resId){
        mResId = resId;
    }

    public UniversalViewHolder newInstance(Context context,ViewGroup parent){
        View v = LayoutInflater.from(context).inflate(mResId,parent,false);
        UniversalViewHolder universalViewHolder = realNewInstance(v);
        universalViewHolder.attachActivity((Activity) context);
        return universalViewHolder;
    }

    public abstract UniversalViewHolder realNewInstance(View v);
}
