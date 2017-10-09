package org.jarvis.code.ui.main;

import org.jarvis.code.model.read.Advertisement;
import org.jarvis.code.model.read.ResponseEntity;
import org.jarvis.code.util.Loggy;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ki.kao on 10/4/2017.
 */

public class AdvertisementInteractorImpl implements AdvertisementInteractor<Advertisement> {

    private MainPresenter<MainView> presenter;

    public AdvertisementInteractorImpl(MainPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResponse(Call<ResponseEntity<Advertisement>> call, Response<ResponseEntity<Advertisement>> response) {
        if (presenter != null && response.isSuccessful()) {
            presenter.onAnimateAD(response.body().getData());
        }
    }

    @Override
    public void onFailure(Call<ResponseEntity<Advertisement>> call, Throwable t) {
        Loggy.e(MainActivity.class, t.getMessage());
        presenter.showSnackBar(t.getMessage());
        //presenter.showMessage(t.getMessage());
    }
}
