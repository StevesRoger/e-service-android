package org.jarvis.code.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_color, this, true);
        TextView lable = (TextView) view.findViewById(R.id.lblColor);
        lable.setText(text);
    }

    public void renderColor(String value) {
        if (value != null && !value.isEmpty())
            renderColor(value.split(","));
    }

    public void renderColor(String[] values) {
        if (getChildCount() > 2)
            removeViews(1, values.length);
        for (String str : values) {
            int color = Color.parseColor(str);
            View view = new View(getContext());
            LayoutParams params = new LayoutParams(colorWidth, colorHeight);
            params.setMargins(10, 20, 0, 0);
            view.setLayoutParams(params);
            view.setBackgroundColor(color);
            this.addView(view);
        }
    }

    public void renderColor(int[] colors) {

    }

}
