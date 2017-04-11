package com.hanshao.demo.sample.provider;

import android.content.Context;
import android.view.View;

import com.hanshao.demo.sample.holder.TowViewHolder;
import com.hanshao.universal.UniversalProvider;
import com.hanshao.universal.UniversalViewHolder;

/**
 * AUTHOR: hanshao
 * DATE: 17/4/10.
 * ACTION:
 */

public class TowProvider extends UniversalProvider<String> {
    public TowProvider(Context context, int resId) {
        super(context, resId);
    }

    @Override
    public UniversalViewHolder<String> realNewInstance(View v) {
        return new TowViewHolder(v);
    }
}
