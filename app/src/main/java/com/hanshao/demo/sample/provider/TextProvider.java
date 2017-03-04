package com.hanshao.demo.sample.provider;

import android.content.Context;
import android.view.View;

import com.hanshao.demo.sample.holder.TextViewHolder;
import com.hanshao.universal.UniversalProvider;
import com.hanshao.universal.UniversalViewHolder;

/**
 * AUTHOR: hanshao
 * DATE: 17/2/28.
 * ACTION:item提供者
 */

public class TextProvider extends UniversalProvider<String> {

    public TextProvider(Context context, int resId) {
        super(context, resId);
    }

    @Override
    public UniversalViewHolder<String> realNewInstance(View v) {
        return new TextViewHolder(v);
    }
}



