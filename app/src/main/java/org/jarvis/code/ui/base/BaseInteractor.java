package org.jarvis.code.ui.base;

import org.jarvis.code.model.ResponseEntity;
import org.jarvis.code.service.FirebaseBroadcastReceiver;

import retrofit2.Callback;

/**
 * Created by ki.kao on 10/5/2017.
 */

public interface BaseInteractor<T> extends Callback<ResponseEntity<T>>, FirebaseBroadcastReceiver.BroadcastAction {
}
