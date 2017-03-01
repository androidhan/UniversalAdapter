package com.hanshao.demo.sample.provider;

import android.content.Context;
import android.view.View;

import com.hanshao.demo.sample.holder.FristViewHolder;
import com.hanshao.universal.UniversalProvider;
import com.hanshao.universal.UniversalViewHolder;

/**
 * AUTHOR: hanshao
 * DATE: 17/2/28.
 * ACTION:item提供者
 */

public class FristProvider extends UniversalProvider<String> {

    public FristProvider(Context context, int resId) {
        super(context, resId);
    }

    @Override
    public UniversalViewHolder<String> realNewInstance(View v) {
        return new FristViewHolder(v);
    }
}



