package org.jarvis.code.util;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.jarvis.code.R;

import java.util.List;

/**
 * Created by KimChheng on 7/14/2017.
 */

public final class Animator {

    public static void animateAD(final ImageView imageView, final Context context, final int imageIndex, final ArrayMap<Integer, Integer> images, final boolean forever) {
        Loggy.i(Animator.class, "Animating AD images at index:" + imageIndex);
        String imgUrl = Constants.BASE_URL + "mobile/image/view/";
        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 6000;
        int fadeOutDuration = 1000;
        imageView.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends
        //imageView.setImageResource(advertisements.get(imageIndex));
        Picasso.with(context).load(imgUrl + images.valueAt(imageIndex))
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
                    animateAD(imageView, context, imageIndex + 1, images, forever); //Calls itself until it gets to the end of the array
                } else {
                    if (forever) {
                        animateAD(imageView, context, 0, images, forever);  //Calls itself to start the animation all over again in a loop if forever = true
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


    public static void animatePromotion(final ImageView imageView, final Context context, final int imageIndex, final List<Integer> images, final boolean forever) {
        Loggy.i(Animator.class, "Animating promotion images at index:" + imageIndex);
        String imgUrl = Constants.BASE_URL + "mobile/image/view/";
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
                    animatePromotion(imageView, context, imageIndex + 1, images, forever); //Calls itself until it gets to the end of the array
                } else {
                    if (forever) {
                        animatePromotion(imageView, context, 0, images, forever);  //Calls itself to start the animation all over again in a loop if forever = true
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
