package com.example.myapplication;

import retrofit2.Call;
import java.util.HashMap;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface RetrofitInterface {
    @POST("/detail")
    Call<Detail>exectueDetail(@Body HashMap<String, String> map);

}
