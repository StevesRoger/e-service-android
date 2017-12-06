package org.jarvis.code.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.jarvis.code.model.BaseResponse;

import butterknife.ButterKnife;

/**
 * Created by ki.kao on 12/4/2017.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    protected Context context;
    protected BaseResponse object;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setData(BaseResponse object) {
        this.object = object;
    }
}
