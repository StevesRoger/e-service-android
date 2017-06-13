package org.jarvis.code.api;

import org.jarvis.code.core.model.Product;
import org.jarvis.code.core.model.ResponseEntity;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by KimChheng on 5/28/2017.
 */

public interface WeddingApi {

    @POST("mobile/product/fetch")
    public Call<ResponseEntity<Product>> fetchProducts(@Query("offset") int offset, @Query("limit") int limit);
}
