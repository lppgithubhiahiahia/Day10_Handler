package com.example.lyp.day10_handler.api;

import com.example.lyp.day10_handler.beans.Artical;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MyServe {

    //https://www.wanandroid.com/project/list/1/json?cid=294
    public String url="https://www.wanandroid.com/project/list/1/";


    @GET("json?cid=294")
    Observable<Artical> getData();

}
