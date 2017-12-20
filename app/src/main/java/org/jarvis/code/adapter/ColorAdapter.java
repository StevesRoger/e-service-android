package org.jarvis.code.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jarvis.code.R;

/**
 * Created by ki.kao on 11/8/2017.
 */

public class ColorAdapter extends ArrayAdapter {

    private LayoutInflater inflater;

    public ColorAdapter(Context context, String[] colors) {
        super(context, R.layout.spinner_color, colors);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.spinner_color, parent, false);
        TextView color = (TextView) view.findViewById(R.id.itemColor);
        color.setBackgroundColor(Color.parseColor(getItem(position).toString()));
        return view;
    }

}
