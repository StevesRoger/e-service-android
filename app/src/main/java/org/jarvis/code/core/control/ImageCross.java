package org.jarvis.code.core.control;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.jarvis.code.R;

import java.io.File;

/**
 * Created by KimChheng on 6/8/2017.
 */

public class ImageCross extends FrameLayout implements View.OnClickListener {

    private View root;
    private ImageView photo;
    private ImageButton close;
    private File file;
    private String type;

    public ImageCross(@NonNull Context context) {
        this(context, null);
    }

    public ImageCross(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageCross(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        if (isInEditMode())
            return;

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View customView = null;

        if (inflater != null)
            customView = inflater.inflate(R.layout.image_upload, this);

        if (customView == null)
            return;

        root = customView.findViewById(R.id.root);
        photo = (ImageView) customView.findViewById(R.id.galleryItemImage);
        close = (ImageButton) customView.findViewById(R.id.btnRemove);
        close.setOnClickListener(this);

    }

    public View getRoot() {
        return root;
    }

    public ImageView getPhoto() {
        return photo;
    }

    public View getBtnClose() {
        return close;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setPhoto(Bitmap bitmap) {
        if (photo != null)
            photo.setImageBitmap(bitmap);
    }

    public void setPhoto(int id) {
        if (photo != null)
            photo.setImageResource(id);
    }

    public void setPhoto(Uri uri) {
        if (photo != null)
            photo.setImageURI(uri);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void onClick(View view) {
        this.setVisibility(View.GONE);
        ViewParent viewParent = root.getParent().getParent();
        if (viewParent instanceof LinearLayout) {
            final LinearLayout linearLayout = (LinearLayout) viewParent;

            linearLayout.post(new Runnable() {
                @Override
                public void run() {
                    linearLayout.removeView(ImageCross.this);
                }
            });
        }
    }

}
