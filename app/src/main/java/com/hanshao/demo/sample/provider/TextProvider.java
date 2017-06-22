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

public class TextProvider extends UniversalProvider {

    public TextProvider(int resId) {
        super( resId);
    }

    @Override
    public UniversalViewHolder realNewInstance(View v) {
        return new TextViewHolder(v);
    }
}



