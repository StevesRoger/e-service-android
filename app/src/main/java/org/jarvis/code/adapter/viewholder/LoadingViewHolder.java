package org.jarvis.code.adapter.viewholder;

import android.view.View;
import android.widget.ProgressBar;

import org.jarvis.code.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ki.kao on 12/4/2017.
 */

public class LoadingViewHolder extends BaseViewHolder {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
