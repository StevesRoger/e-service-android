package org.jarvis.code.ui.base;

import org.jarvis.code.model.read.ResponseEntity;

import retrofit2.Callback;

/**
 * Created by ki.kao on 10/5/2017.
 */

public interface BaseInteractor<T> extends Callback<ResponseEntity<T>> {
}
