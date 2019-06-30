/*
 *    Copyright (C) 2016 Tamic
 *
 *    link :https://github.com/Tamicer/Novate
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.ling.suandashi.net;


import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * ApiService
 */
public interface BaseApiService {

    @GET
    Call<ResponseBody> getCall(@Url String url, @QueryMap Map<String, Object> map);

    @POST()
    @FormUrlEncoded
    <T> Observable<Response<ResponseBody>> executePost(@Url() String url, @FieldMap Map<String, Object> maps);

    @GET()
    <T> Observable<Response<ResponseBody>> executeGet(@Url String url, @QueryMap Map<String, Object> map);

    @DELETE()
    <T> Observable<Response<ResponseBody>> executeDelete(@Url String url, @QueryMap Map<String, Object> maps);

    @PUT()
    <T> Observable<Response<ResponseBody>> executePut(@Url String url, @FieldMap Map<String, Object> maps);

    @POST()
    Observable<Response<ResponseBody>> postRequestBody(@Url() String url, @Body RequestBody Body);
}


