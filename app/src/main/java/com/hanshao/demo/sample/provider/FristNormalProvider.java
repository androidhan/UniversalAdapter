package com.hanshao.demo.sample.provider;

import android.content.Context;
import android.view.View;

import com.hanshao.demo.sample.bean.ItemInfo;
import com.hanshao.demo.sample.holder.FristNormalViewHolder;
import com.hanshao.universal.UniversalProvider;
import com.hanshao.universal.UniversalViewHolder;

/**
 * AUTHOR: hanshao
 * DATE: 17/2/28.
 * ACTION:
 */

public class FristNormalProvider extends UniversalProvider {
    public FristNormalProvider( int resId) {
        super( resId);
    }

    @Override
    public UniversalViewHolder realNewInstance(View v) {
        return new FristNormalViewHolder(v);
    }
}
