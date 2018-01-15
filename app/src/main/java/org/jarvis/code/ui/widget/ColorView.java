package org.jarvis.code.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jarvis.code.R;

/**
 * Created by KimChheng on 11/3/2017.
 */

public class ColorView extends LinearLayout {

    private String text;
    private int count;
    private int colorWidth;
    private int colorHeight;
    private LinearLayout container;

    public ColorView(Context context) {
        this(context, null);
    }

    public ColorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ColorView, 0, 0);
        text = attributes.getString(R.styleable.ColorView_text);
        count = attributes.getInt(R.styleable.ColorView_array_view, 0);
        colorWidth = attributes.getLayoutDimension(R.styleable.ColorView_view_color_width, 0);
        colorHeight = attributes.getLayoutDimension(R.styleable.ColorView_view_color_height, 0);
        attributes.recycle();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_color, this, true);

        ((TextView) getChildAt(0)).setText(text);
        container = (LinearLayout) getChildAt(1);
    }

    public void setColor(String value) {
        if (value != null && !value.isEmpty())
            setColor(value.split(","));
    }

    public void setColor(String[] values) {
        if (container != null)
            container.removeAllViews();
        for (String str : values) {
            int color = Color.parseColor(str);
            CircleView view = new CircleView(getContext());
            LayoutParams params = new LayoutParams(colorWidth, colorHeight);
            params.setMargins(10, 0, 0, 0);
            view.setLayoutParams(params);
            view.setColor(color);
            container.addView(view);
        }
    }

}
