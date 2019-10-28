package com.e.mvvmlivedata.views.fragments;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.e.mvvmlivedata.R;
import com.e.mvvmlivedata.base.BaseFragment;
import com.e.mvvmlivedata.databinding.FragmentMainBinding;
import com.e.mvvmlivedata.models.NicePlace;
import com.e.mvvmlivedata.viewmodels.MainViewModel;
import com.e.mvvmlivedata.views.adapters.PlacesAdapter;
import com.e.mvvmlivedata.views.interfaces.IIssueDetail;
import com.e.mvvmlivedata.views.interfaces.NicePlaceContract;
import com.google.android.material.snackbar.Snackbar;

public class MainFragment extends BaseFragment implements NicePlaceContract , SwipeRefreshLayout.OnRefreshListener{

    private PlacesAdapter mAdapter;
    private MainViewModel mainViewModel;
    private FragmentMainBinding fragmentMainBinding;
    private IIssueDetail mIIssueDetail;

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMainBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_main, container, false);
        fragmentMainBinding.swipeRefreshLayout.setOnRefreshListener(this);

        return fragmentMainBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();
        initViewModel();
    }

    @Override
    protected void initViewModel() {
        mainViewModel.getNicePlaces().observe(this, nicePlaces -> mAdapter.notifyDataSetChanged());

        mainViewModel.getIsUpdating().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgressBar();
            } else {
                hideProgressBar();
                fragmentMainBinding.recyclerView.smoothScrollToPosition(mainViewModel.getNicePlaces().getValue().size() - 1);
            }
        });


    }

    @Override
    protected void initComponents() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        fragmentMainBinding.setViewModel(mainViewModel);
        mainViewModel.init();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new PlacesAdapter(this, mainViewModel.getNicePlaces().getValue());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentMainBinding.recyclerView.setLayoutManager(linearLayoutManager);
        fragmentMainBinding.setAdapter(mAdapter);
    }

    private void showProgressBar() {
        fragmentMainBinding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        fragmentMainBinding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPlaceClicked(NicePlace nicePlace) {

        Snackbar.make(fragmentMainBinding.mainLayout,nicePlace.getTitle(),Snackbar.LENGTH_LONG).show();
        mIIssueDetail.inflateFullScreenImageFragment(nicePlace.getImageUrl());
     //   Toast.makeText(getContext(), ""+nicePlace.getTitle(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIIssueDetail = (IIssueDetail) getActivity();

    }
    private void onItemsLoadComplete(){
        fragmentMainBinding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        fragmentMainBinding.recyclerView.smoothScrollToPosition(mainViewModel.getNicePlaces().getValue().size() - 1);
        onItemsLoadComplete();
    }
}
