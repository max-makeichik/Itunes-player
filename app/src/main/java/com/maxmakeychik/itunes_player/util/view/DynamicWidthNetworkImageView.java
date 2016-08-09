package com.maxmakeychik.itunes_player.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DynamicWidthNetworkImageView extends ImageView {
    private float mAspectRatio = 1.5f;

    public DynamicWidthNetworkImageView(Context context) {
        super(context);
    }

    public DynamicWidthNetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicWidthNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        setMeasuredDimension((int) (measuredHeight / mAspectRatio), measuredHeight);
    }

    public void setAspectRatio(float aspectRatio) {
        mAspectRatio = aspectRatio;
        requestLayout();
    }
}