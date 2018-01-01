package org.jarvis.code.ui.main;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jarvis.code.model.Advertisement;
import org.jarvis.code.model.ResponseEntity;
import org.jarvis.code.util.Constants;
import org.jarvis.code.util.Loggy;
import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ki.kao on 10/4/2017.
 */

public class AdvertisementInteractorImpl implements AdvertisementInteractor<Advertisement> {

    private MainPresenter<MainView> presenter;
    private Gson gson;

    public AdvertisementInteractorImpl(MainPresenter presenter) {
        this.presenter = presenter;
        this.gson = new Gson();
    }

    @Override
    public void onResponse(Call<ResponseEntity<Advertisement>> call, Response<ResponseEntity<Advertisement>> response) {
        if (presenter != null && response.code() == 200)
            presenter.onAnimateAD(response.body().getData());
    }

    @Override
    public void onFailure(Call<ResponseEntity<Advertisement>> call, Throwable t) {
        Loggy.e(AdvertisementInteractorImpl.class, t.getMessage());
    }

    @Override
    public void onNewItem(Context context, JSONArray jsonArray) throws Exception {
        for (int i = 0; i < jsonArray.length(); i++) {
            Advertisement ad = gson.fromJson(jsonArray.getString(i), Advertisement.class);
            Constants.advertisement.put(ad.getId(), ad.getImage());
        }
    }

    @Override
    public void onUpdateItem(Context context, JSONArray jsonArray) throws Exception {

    }

    @Override
    public void onDeleteItem(Context context, JSONArray jsonArray) throws Exception {
        for (int i = 0; i < jsonArray.length(); i++) {
            Constants.advertisement.remove(jsonArray.getInt(i));
        }
    }
}
