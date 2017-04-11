package com.hanshao.demo.sample.holder;

import android.view.View;
import android.widget.TextView;

import com.hanshao.demo.sample.R;
import com.hanshao.universal.UniversalViewHolder;

/**
 * AUTHOR: hanshao
 * DATE: 17/3/3.
 * ACTION:
 */

public class TextWasterfalViewHolder extends UniversalViewHolder<String> {

    private TextView mTextView;

    public TextWasterfalViewHolder(View v) {
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
