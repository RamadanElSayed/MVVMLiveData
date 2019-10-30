package com.e.mvvmlivedata.views.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.e.mvvmlivedata.R;
import com.e.mvvmlivedata.databinding.LayoutListitemBinding;
import com.e.mvvmlivedata.models.NicePlace;
import com.e.mvvmlivedata.views.interfaces.NicePlaceContract;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private List<NicePlace> mNicePlaces;
    private NicePlaceContract placeContract;


    public PlacesAdapter(NicePlaceContract nicePlaceContract,List<NicePlace> nicePlaces) {
        mNicePlaces = nicePlaces;
        this.placeContract = nicePlaceContract;
    }

    @NonNull
    @Override
    public PlacesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutListitemBinding listitemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.layout_listitem, viewGroup, false);
        return new ViewHolder(listitemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesAdapter.ViewHolder viewHolder, int i) {

        NicePlace nicePlace = mNicePlaces.get(i);
        viewHolder.layoutListitemBinding.setModel(nicePlace);
        viewHolder.layoutListitemBinding.setNiceClicked(placeContract);
        viewHolder.bind(nicePlace);


    }

    @Override
    public int getItemCount() {
        if (mNicePlaces != null) {
            return mNicePlaces.size();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LayoutListitemBinding layoutListitemBinding;

        ViewHolder(LayoutListitemBinding listitemBinding) {
            super(listitemBinding.getRoot());
            this.layoutListitemBinding = listitemBinding;
        }

        void bind(Object obj) {
            layoutListitemBinding.setVariable(BR.model, obj);
            layoutListitemBinding.executePendingBindings();
        }
    }
}
