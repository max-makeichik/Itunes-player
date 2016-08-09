package com.maxmakeychik.itunes_player.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Created by admin on 08.08.2016.
 */
public class ControlImageButton extends ImageButton {
    public ControlImageButton(Context context) {
        super(context);
    }

    public ControlImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ControlImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ControlImageButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setEnabled(boolean enabled){
        setAlpha(enabled ? 1.0f : 0.4f);
        setClickable(enabled);
    }
}
