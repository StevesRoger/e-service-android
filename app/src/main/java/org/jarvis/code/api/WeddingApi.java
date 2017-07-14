package org.jarvis.code.api;

import org.jarvis.code.core.model.response.Product;
import org.jarvis.code.core.model.response.ResponseEntity;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by KimChheng on 5/28/2017.
 */

public interface WeddingApi {

    @POST("mobile/product/fetch")
    Call<ResponseEntity<Product>> fetchProducts(@Query("offset") int offset, @Query("limit") int limit);

    @Multipart
    @POST("mobile/customer/submit")
    Call<ResponseEntity<String>> submitCustomer(@Part("json") RequestBody json, @Part MultipartBody.Part[] files);

}
