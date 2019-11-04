package com.e.mvvmlivedata.repositories;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.e.mvvmlivedata.models.NicePlace;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.e.mvvmlivedata.base.BaseFragment.TAG;


/**
 * Singleton pattern
 */
//Repo for getting the dataSources...
public class NicePlaceRepository {

    private static NicePlaceRepository instance;
    private ArrayList<NicePlace> dataSet = new ArrayList<>();
    private ArrayList<NicePlace> dataSetFromObserver = new ArrayList<>();
    private MutableLiveData<Boolean> mIsUpdating;

    private MutableLiveData<List<NicePlace>> mutableLiveDataList;
    private CompositeDisposable disposable = new CompositeDisposable();

    public static NicePlaceRepository getInstance() {
        if (instance == null) {
            instance = new NicePlaceRepository();
        }
        return instance;
    }

    // Pretend to get data from a webservice or online source
    public MutableLiveData<List<NicePlace>> getNicePlaces() {
        getNicePlaceObserver();
        mutableLiveDataList = new MutableLiveData<>();
        mutableLiveDataList.setValue(dataSetFromObserver);
        return mutableLiveDataList;
    }

    public MutableLiveData<Boolean> getmIsUpdating() {
        mIsUpdating = new MutableLiveData<>();
        return mIsUpdating;
    }
/*
The distinct operator works very well with primitive datatypes. But if you want to use it with a custom datatype, you need to override the equals() and hashCode() methods.

The below example avoids duplicate notes emitted by an Observable.

prepareNotes() returns list of notes including duplicate notes.
getNotesObservable() creates an Observable that emits list of notes
In Note model, equals() and hashCode() methods are overridden to make comparison between objects work.
 */
    private void getNicePlaceObserver() {
        disposable.add(getNicePlaceObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).skip(2).takeLast(9).distinct().repeat(2).filter(new Predicate<NicePlace>() {
                    @Override
                    public boolean test(NicePlace nicePlace) throws Exception {
                        return !nicePlace.getTitle().equalsIgnoreCase("Havasu Falls");
                    }
                }).subscribeWith(new DisposableObserver<NicePlace>() {
                    @Override
                    public void onNext(NicePlace nicePlace) {
                        dataSetFromObserver.add(nicePlace);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        dataSetFromObserver.add(
                                new NicePlace("https://i.redd.it/obx4zydshg601.jpg",
                                        "C")
                        );
                        mutableLiveDataList.postValue(dataSetFromObserver);
                        Log.v("hhhhh", dataSetFromObserver.size() + "111");
                    }
                }));


    }

    private Observable<NicePlace> getNicePlaceObservable() {
        setNicePlaces();
        return Observable.create(emitter -> {
            for (NicePlace nicePlace : dataSet)
                if (!emitter.isDisposed())
                    emitter.onNext(nicePlace);

            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        });
    }



    public void observeOnNewPlace(NicePlace nicePlace) {
        disposable.add(addNewNicePlace(nicePlace).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                mIsUpdating.postValue(false);
            }

            @Override
            public void onError(Throwable e) {

            }
        }));

        //TODO FROM operator

       // The Observer emits the array onNext(Integer[] integers) so you will always have 1 emission irrespective of length of the array.
//        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
//
//        Observable.just(numbers)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Integer[]>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer[] integers) {
//                        Log.d(TAG, "onNext: " + integers.length);
//
//                        // you might have to loop through the array
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


        //TODO MATH
        /*
        RxJava Mathematical Operators – Max, Min, Sum, Average, Count & Reduce
         */
        Observable
                .range(1, 10)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer number, Integer sum) throws Exception {
                        return sum + number;
                    }
                })
                .subscribe(new MaybeObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.e(TAG, "Sum of numbers from 1 - 10 is: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });
    }

    private Completable addNewNicePlace(NicePlace nicePlace) {
        return Completable.create(emitter -> {
            if (!emitter.isDisposed()) {
                mIsUpdating.postValue(true);
                dataSetFromObserver.add(nicePlace);
                mutableLiveDataList.postValue(dataSetFromObserver);
                Thread.sleep(2000);
                emitter.onComplete();
            }
        });
    }

    private void setNicePlaces() {
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
                new NicePlace("https://i.redd.it/k98uzl68eh501.jpg",
                        "Mahahual")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/k98uzl68eh501.jpg",
                        "Mahahual")
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

    public void onDestroy() {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }
}






