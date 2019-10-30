package com.e.mvvmlivedata.repositories;

import android.annotation.SuppressLint;
import android.util.Log;
import android.webkit.ValueCallback;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.e.mvvmlivedata.models.NicePlace;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Singleton pattern
 */
//Repo for getting the dataSources...
public class NicePlaceRepository {

    private static NicePlaceRepository instance;
    private ArrayList<NicePlace> dataSet = new ArrayList<>();
    private ArrayList<NicePlace> dataSet2 = new ArrayList<>();
    private  MutableLiveData<List<NicePlace>> data;
    public static NicePlaceRepository getInstance(){
        if(instance == null){
            instance = new NicePlaceRepository();
        }

        return instance;
    }

    // Pretend to get data from a webservice or online source
    public MutableLiveData<List<NicePlace>> getNicePlaces(){
        getNicePlaceObserver();
        data = new MutableLiveData<>();
        data.setValue(dataSet2);
        return data;
    }


    public void getNicePlaceObserver()
    {
        getNicePlaceObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<NicePlace>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(NicePlace nicePlace) {

                dataSet2.add(nicePlace);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

                dataSet2.add(
                        new NicePlace("https://i.redd.it/obx4zydshg601.jpg",
                                "C")
                );
                data.postValue(dataSet2);
                Log.v("hhhhh",dataSet2.size()+"111");

            }
        });


    }

    private Observable<NicePlace> getNicePlaceObservable()
    {
        setNicePlaces();
        return Observable.create(emitter -> {
               for(NicePlace nicePlace:dataSet)
                    if(!emitter.isDisposed())
                        emitter.onNext(nicePlace);

            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        });
    }

    private void setNicePlaces(){
        dataSet.add(
                new NicePlace("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg",
                        "Havasu Falls")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/tpsnoz5bzo501.jpg",
                        "Trondheim")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/qn7f9oqu7o501.jpg",
                        "Portugal")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/j6myfqglup501.jpg",
                        "Rocky Mountain National Park")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/0h2gm1ix6p501.jpg",
                        "Havasu Falls")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/k98uzl68eh501.jpg",
                        "Mahahual")
        );
        dataSet.add(
                new NicePlace("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg",
                        "Frozen Lake")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/obx4zydshg601.jpg",
                        "Austrailia")
        );
    }
}






