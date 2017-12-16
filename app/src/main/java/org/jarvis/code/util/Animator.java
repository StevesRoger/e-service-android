package org.jarvis.code.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.jarvis.code.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KimChheng on 7/14/2017.
 */

public final class Animator {

    private String imgUrl = Constants.BASE_URL + "mobile/image/view/";
    private ImageView imageView;
    private List<Integer> images;
    private Context context;

    public Animator(ImageView imageView, ArrayMap<Integer, Integer> map, Context context) {
        this.imageView = imageView;
        this.images = new ArrayList();
        for (Integer integer : map.values()) {
            images.add(integer);
        }
        this.context = context;
    }

    public Animator(ImageView imageView, List<Integer> images, Context context) {
        this.imageView = imageView;
        this.images = images;
        this.context = context;
    }

    public void animateAD(final int imageIndex, final boolean forever) {
        Loggy.i(Animator.class, "Animating AD images at index:" + imageIndex);
        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 6000;
        int fadeOutDuration = 1000;
        imageView.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends
        //imageView.setImageResource(advertisements.get(imageIndex));
        Picasso.with(context).load(imgUrl + images.get(imageIndex))
                .fit()
                //.placeholder(R.drawable.progress_spinning_circle)
                .error(R.drawable.no_ad_available)
                .into(imageView);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (images.size() - 1 > imageIndex) {
                    animateAD(imageIndex + 1, forever); //Calls itself until it gets to the end of the array
                } else {
                    if (forever) {
                        animateAD(0, forever);  //Calls itself to start the animation all over again in a loop if forever = true
                    }
                }
            }

            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
        });
    }


    public void animatePromotion(final int imageIndex, final boolean forever) {
        Loggy.i(Animator.class, "Animating promotion images at index:" + imageIndex);
        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 6000;
        int fadeOutDuration = 1000;
        imageView.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends
        //imageView.setImageResource(advertisements.get(imageIndex));

        Picasso.with(context).load(imgUrl + images.get(imageIndex)).fit().centerCrop()
                //.placeholder(R.drawable.progress_animation)
                .error(R.drawable.no_image_available)
                .into(imageView);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (images.size() - 1 > imageIndex) {
                    animatePromotion(imageIndex + 1, forever); //Calls itself until it gets to the end of the array
                } else {
                    if (forever) {
                        animatePromotion(0, forever);  //Calls itself to start the animation all over again in a loop if forever = true
                    }
                }
            }

            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
        });
    }

}
