package com.hanshao.demo.sample.holder;

import android.view.View;
import android.widget.TextView;

import com.hanshao.demo.sample.R;
import com.hanshao.universal.UniversalViewHolder;

/**
 * AUTHOR: hanshao
 * DATE: 17/2/28.
 * ACTION:第一种类Item
 */

public class TextViewHolder extends UniversalViewHolder<String> {

    private TextView mTextView;

    public TextViewHolder(View v) {
        super(v);
    }

    @Override
    protected void initView(View v) {
        mTextView = (TextView) v.findViewById(R.id.text_view);

    }

    @Override
    public void refreshUi(String data) {
        mTextView.setText(data);
    }

}
