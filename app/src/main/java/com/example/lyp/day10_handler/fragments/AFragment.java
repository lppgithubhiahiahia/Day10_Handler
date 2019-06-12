package com.example.lyp.day10_handler.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lyp.day10_handler.MyReceiver;
import com.example.lyp.day10_handler.R;
import com.example.lyp.day10_handler.adapter.ReAdapter;
import com.example.lyp.day10_handler.api.MyServe;
import com.example.lyp.day10_handler.beans.Artical;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class AFragment extends Fragment implements ReAdapter.MyOnClick {


    private RecyclerView rv;
    private ArrayList<Artical.DataBean.DatasBean> list;
    private ReAdapter adapter;

    public AFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(MyServe.url)
                .build();

        MyServe myServe = retrofit.create(MyServe.class);

        Observable<Artical> observable = myServe.getData();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Artical>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Artical artical) {

                        if (artical!=null){

                            list.addAll(artical.getData().getDatas());

                            adapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initView(View view) {
        rv = (RecyclerView) view.findViewById(R.id.rv);

        list = new ArrayList<>();

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new ReAdapter(getActivity(), list);

        adapter.setMyOnClick(this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(int i, Artical.DataBean.DatasBean datasBean) {
        Intent intent = new Intent(getActivity(), MyReceiver.class);

        intent.putExtra("title",datasBean.getTitle());

        getActivity().sendBroadcast(intent);

    }
}
