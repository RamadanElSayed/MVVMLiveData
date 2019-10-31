package com.e.mvvmlivedata.viewmodels;
import android.app.Application;
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
    private MutableLiveData<Boolean> mIsUpdating;
    private NicePlaceRepository mRepo;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        if (mNicePlaces != null) {
            return;
        }
        mRepo = NicePlaceRepository.getInstance();
        mNicePlaces = mRepo.getNicePlaces();
        mIsUpdating=mRepo.getmIsUpdating();
    }

    private void addNewValue(final NicePlace nicePlace) {

        mRepo.observeOnNewPlace(nicePlace);
    }

    public LiveData<List<NicePlace>> getNicePlaces() {
        return mNicePlaces;
    }

    public void setNicePlaceLive() {
        NicePlace nicePlace = new NicePlace(
                "https://i.imgur.com/ZcLLrkY.jpg",
                "Washington");

        addNewValue(nicePlace);
    }

    public LiveData<Boolean> getIsUpdating() {
        return mIsUpdating;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepo.onDestroy();
    }
}
