package com.hanshao.demo.sample.provider;

import android.content.Context;
import android.view.View;

import com.hanshao.demo.sample.holder.FourViewHolder;
import com.hanshao.demo.sample.holder.ThreeViewHolder;
import com.hanshao.universal.UniversalProvider;
import com.hanshao.universal.UniversalViewHolder;

/**
 * AUTHOR: hanshao
 * DATE: 17/4/10.
 * ACTION:
 */

public class FourProvider extends UniversalProvider{
    public FourProvider( int resId) {
        super(resId);
    }

    @Override
    public UniversalViewHolder realNewInstance(View v) {
        return new FourViewHolder(v);
    }
}
