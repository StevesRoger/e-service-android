package org.jarvis.code.util;

import android.content.Context;
import android.view.View;

/**
 * Created by ki.kao on 9/7/2017.
 */

public final class ComponentFactory {

    private Context context;
    private View view;

    public ComponentFactory(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public <T> T build(Class<T> clazz, int id) {
        return (T) view.findViewById(id);
    }
}
