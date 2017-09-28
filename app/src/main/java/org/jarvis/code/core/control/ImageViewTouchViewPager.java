package org.jarvis.code.core.control;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

/**
 * Created by KimChheng on 6/20/2017.
 */

public class ImageViewTouchViewPager extends ViewPager {

    public ImageViewTouchViewPager(Context context) {
        super(context);
    }

    public ImageViewTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ImageViewTouch) {
            ImageViewTouch imageViewTouch = (ImageViewTouch)v;
            return imageViewTouchCanScroll(imageViewTouch, dx);
        } else {
            return super.canScroll(v, checkV, dx, x, y);
        }
    }


    /**
     * Determines whether the ImageViewTouch can be scrolled.
     *
     * @param direction - positive direction value means scroll from right to left,
     *                  negative value means scroll from left to right
     * @return true if there is some more place to scroll, false - otherwise.
     */
    private boolean imageViewTouchCanScroll(ImageViewTouch imageViewTouch, int direction){
        int widthScreen = getWidthScreen();

        RectF bitmapRect = imageViewTouch.getBitmapRect();
        Rect imageViewRect = new Rect();
        getGlobalVisibleRect(imageViewRect);

        int widthBitmapViewTouch = (int)bitmapRect.width();

        if (null == bitmapRect) {
            return false;
        }

        if(widthBitmapViewTouch < widthScreen){
            return false;
        }

        if (direction < 0) {
            return Math.abs(bitmapRect.right - imageViewRect.right) > 1.0f;
        }else {
            return Math.abs(bitmapRect.left - imageViewRect.left) > 1.0f;
        }

    }

    private int getWidthScreen(){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}