package com.pyrojewel.widget;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

/**
 * Created by Jimmy on 2016/10/13 0013.
 */
public class StrikeThruTextView extends androidx.appcompat.widget.AppCompatTextView {

    public StrikeThruTextView(Context context) {
        this(context, null);
    }

    public StrikeThruTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrikeThruTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        TextPaint paint = getPaint();
        paint.setAntiAlias(true);
        paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }

}
