package org.jarvis.code.ui.main;

import android.content.Context;

import org.jarvis.code.model.ResponseEntity;
import org.jarvis.code.model.read.Advertisement;
import org.jarvis.code.util.Loggy;
import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ki.kao on 10/4/2017.
 */

public class AdvertisementInteractorImpl implements AdvertisementInteractor<Advertisement> {

    private MainPresenter<MainView> presenter;
    private List<Integer> advertisement;

    public AdvertisementInteractorImpl(MainPresenter presenter) {
        this.presenter = presenter;
        if (presenter != null)
            this.advertisement = ((MainActivity) presenter.activity()).advertisement;
    }

    @Override
    public void onResponse(Call<ResponseEntity<Advertisement>> call, Response<ResponseEntity<Advertisement>> response) {
        if (presenter != null && response.isSuccessful())
            presenter.onAnimateAD(response.body().getData());
    }

    @Override
    public void onFailure(Call<ResponseEntity<Advertisement>> call, Throwable t) {
        Loggy.e(MainActivity.class, t.getMessage());
        if (presenter != null)
            presenter.showSnackBar(t.getMessage());
        //presenter.toastMessage(t.getMessage());
    }

    @Override
    public void onNewItem(Context context, JSONArray jsonArray) {

    }

    @Override
    public void onUpdateItem(Context context, JSONArray jsonArray) {

    }

    @Override
    public void onDeleteItem(Context context, JSONArray jsonArray) {

    }
}
