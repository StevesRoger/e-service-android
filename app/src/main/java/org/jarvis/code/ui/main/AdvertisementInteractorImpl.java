package org.jarvis.code.ui.main;

import android.widget.Toast;

import org.jarvis.code.model.read.Advertisement;
import org.jarvis.code.model.read.ResponseEntity;
import org.jarvis.code.util.Loggy;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ki.kao on 10/4/2017.
 */

public class AdvertisementInteractorImpl implements AdvertisementInteractor {

    private MainPresenter presenter;

    public AdvertisementInteractorImpl(MainPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResponse(Call<ResponseEntity<Advertisement>> call, Response<ResponseEntity<Advertisement>> response) {
        if (response.isSuccessful()) {
            presenter.onAnimateAD(response.body().getData());
        }
    }

    @Override
    public void onFailure(Call<ResponseEntity<Advertisement>> call, Throwable t) {
        Loggy.e(MainActivity.class, t.getMessage());
        Toast.makeText(presenter.context(), t.getMessage(), Toast.LENGTH_LONG).show();
    }
}
