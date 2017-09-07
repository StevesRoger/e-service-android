package org.jarvis.code.util;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by KimChheng on 6/18/2017.
 */

public final class Validator {

    private Context context;
    private List<String> validControl;

    public Validator(Context context) {
        this.context = context;
        this.validControl = new ArrayList<>();
    }

    public void setRequired(Map<Integer, TextView> controls) {
        for (Map.Entry<Integer, TextView> entry : controls.entrySet()) {
            setRequired(entry.getKey(), entry.getValue());
        }
    }

    public void setRequired(int captionId, TextView textView) {
        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(context.getResources().getString(captionId));
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    public void requiredTextField(EditText text) {
        if (!isValidText(text))
            validControl.add(text.getHint().toString().trim());
    }

    public void isEmptyTextField(EditText text) {
        isEmptyTextField(text, text.getHint().toString().trim());
    }

    public void isEmptyTextField(EditText text, String msg) {
        if (isEmpty(text))
            validControl.add(msg);
    }

    public boolean isValidText(EditText text) {
        return !isEmpty(text) && text.getText().toString().trim().matches("[a-zA-Z \\u0080-\\u9fff]*+");
    }

    public boolean isEmpty(EditText text) {
        return text.getText().toString().trim().equals("");
    }

    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        for (String msg : validControl)
            builder.append(msg).append("\r\n");
        return builder.toString();
    }

    public boolean isValid() {
        return validControl.isEmpty();
    }

    public void clear() {
        validControl.clear();
    }

}
