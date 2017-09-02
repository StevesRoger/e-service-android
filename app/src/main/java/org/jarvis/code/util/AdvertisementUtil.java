package org.jarvis.code.util;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jarvis.code.R;
import org.jarvis.code.api.RequestClient;
import org.jarvis.code.core.model.response.Advertisement;
import org.jarvis.code.core.model.response.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KimChheng on 7/14/2017.
 */

public final class AdvertisementUtil implements Callback<ResponseEntity<Advertisement>> {

    private List<Integer> images = new ArrayList<>();
    private RequestClient requestClient;
    private Context context;
    private ImageView imageView;
    private static String imgUrl = Constant.BASE_URL + "mobile/image/view/";

    public AdvertisementUtil(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
        this.requestClient = RequestFactory.build(RequestClient.class);
        this.requestClient.fetchAdvertisement().enqueue(this);
    }

    public void animate(final ImageView imageView, final List<Integer> images, final int imageIndex, final boolean forever) {

        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 3000;
        int fadeOutDuration = 1000;

        imageView.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends
        //imageView.setImageResource(images.get(imageIndex));
        Picasso.with(context).load(imgUrl + images.get(imageIndex))
                .fit()
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
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
                    animate(imageView, images, imageIndex + 1, forever); //Calls itself until it gets to the end of the array
                } else {
                    if (forever) {
                        AdvertisementUtil.this.requestClient.fetchAdvertisement().enqueue(AdvertisementUtil.this);
                        //animate(imageView, images, 0, forever);  //Calls itself to start the animation all over again in a loop if forever = true
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

    @Override
    public void onResponse(Call<ResponseEntity<Advertisement>> call, Response<ResponseEntity<Advertisement>> response) {
        if (response.code() == 200) {
            ResponseEntity<Advertisement> responseEntity = response.body();
            if (responseEntity.getData() != null && !responseEntity.getData().isEmpty()) {
                for (Advertisement advertisement : responseEntity.getData()) {
                    images.add(advertisement.getImage());
                }
                animate(imageView, images, 0, true);
            }
        }
    }

    @Override
    public void onFailure(Call<ResponseEntity<Advertisement>> call, Throwable t) {
        Jog.i(AdvertisementUtil.class, t.getMessage());
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
    }
}
