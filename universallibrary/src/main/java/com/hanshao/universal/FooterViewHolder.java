package com.hanshao.universal;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * AUTHOR: hanshao
 * DATE: 17/2/25.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {

    private  TextView textFaild;
    private  LinearLayout linearLayoutLoad;
    private  LinearLayout linearLayoutFaild;
    private OnTryListener mOnTryListener;


    public FooterViewHolder(View itemView) {
        super(itemView);
        textFaild = (TextView) itemView.findViewById(R.id.text_faild);
        linearLayoutLoad = (LinearLayout) itemView.findViewById(R.id.load);
        linearLayoutFaild = (LinearLayout) itemView.findViewById(R.id.faild);
        textFaild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mOnTryListener!= null)
                {
                    mOnTryListener.onTry();
                }
            }
        });

    }


    public void setOnTryListener(OnTryListener listener ){
        this.mOnTryListener = listener;
    }

    public void setCurrentStatus(int status) {

        if(status == UniversalAdapter.LOADING){
            linearLayoutLoad.setVisibility(View.VISIBLE);
            linearLayoutFaild.setVisibility(View.GONE);
        }
        else {
            linearLayoutLoad.setVisibility(View.GONE);
            linearLayoutFaild.setVisibility(View.VISIBLE);
        }

    }
}