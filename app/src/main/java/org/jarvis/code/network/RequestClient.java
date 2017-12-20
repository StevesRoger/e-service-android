package org.jarvis.code.network;

import org.jarvis.code.model.Advertisement;
import org.jarvis.code.model.Product;
import org.jarvis.code.model.Promotion;
import org.jarvis.code.model.ResponseEntity;

import java.util.Map;

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

public interface RequestClient {

    @POST("mobile/product/fetch")
    Call<ResponseEntity<Product>> fetchProducts(@Query("offset") int offset, @Query("limit") int limit, @Query("type") String type);

    @Multipart
    @POST("mobile/customer/submit")
    Call<ResponseEntity<Map<String, Object>>> submitCustomer(@Part("json") RequestBody json, @Part MultipartBody.Part[] files);

    @POST("mobile/promotion/fetch")
    Call<ResponseEntity<Promotion>> fetchPromotions();

    @POST("mobile/advertisement/fetch")
    Call<ResponseEntity<Advertisement>> fetchAdvertisement();

}
