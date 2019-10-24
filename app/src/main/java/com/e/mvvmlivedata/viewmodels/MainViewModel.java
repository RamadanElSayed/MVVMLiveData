package com.e.mvvmlivedata.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.e.mvvmlivedata.models.NicePlace;
import com.e.mvvmlivedata.repositories.NicePlaceRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    //for getting data
    // MutableLiveData where we can setValue and Post and LiveData  we can not set but we can get ..
    private MutableLiveData<List<NicePlace>> mNicePlaces;
    // for checking the get more ..and progress par
    private MutableLiveData<NicePlace>nicePlaceMutableLiveData;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        if(mNicePlaces != null){
            return;
        }
        NicePlaceRepository mRepo = NicePlaceRepository.getInstance();
        mNicePlaces = mRepo.getNicePlaces();
        nicePlaceMutableLiveData=new MutableLiveData<>();
    }

    @SuppressLint("StaticFieldLeak")
    public void addNewValue(final NicePlace nicePlace){
        mIsUpdating.setValue(true);

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<NicePlace> currentPlaces = mNicePlaces.getValue();
                assert currentPlaces != null;
                currentPlaces.add(nicePlace);

                mNicePlaces.postValue(currentPlaces);
                mIsUpdating.postValue(false);
            }

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }



    public LiveData<List<NicePlace>> getNicePlaces(){
        return mNicePlaces;
    }

    public void setNicePlaceLive()
    {
        NicePlace nicePlace= new NicePlace(
                "https://i.imgur.com/ZcLLrkY.jpg",
                "Washington");
        nicePlaceMutableLiveData.setValue(nicePlace);
    }
    public LiveData<NicePlace>getAddingNicePlace()
    {
        return nicePlaceMutableLiveData;
    }
    public LiveData<Boolean> getIsUpdating(){
        return mIsUpdating;
    }
}
