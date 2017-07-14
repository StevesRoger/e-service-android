package org.jarvis.code.util;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by KimChheng on 6/18/2017.
 */

public final class ValidateUtil {

    public static void setRequired(String caption, TextView textView) {
        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(caption);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    public static boolean isValid(EditText text) {
        return !isEmpty(text) && text.getText().toString().trim().matches("^[a-zA-Z]+(( )+[a-zA-z]+)*$");
    }

    public static boolean isEmpty(EditText text) {
        return text.getText().toString().trim().equals("");
    }
}
