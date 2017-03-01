package com.hanshao.demo.sample.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanshao.demo.sample.R;
import com.hanshao.demo.sample.bean.ItemInfo;
import com.hanshao.universal.UniversalViewHolder;

/**
 * AUTHOR: hanshao
 * DATE: 17/2/28.
 * ACTION:
 */

public class FristWaterfalViewHolder extends UniversalViewHolder<ItemInfo> {


    private ImageView mImageView;
    private TextView mTextView;

    public FristWaterfalViewHolder(View v) {
        super(v);
    }

    @Override
    protected void initView(View v) {
        mImageView = (ImageView) v.findViewById(R.id.image_view);
        mTextView = (TextView) v.findViewById(R.id.text_view);
    }

    @Override
    public void refreshUi(ItemInfo data) {
        mTextView.setText(data.content);
        mImageView.setImageResource(data.imageId);
    }
}
