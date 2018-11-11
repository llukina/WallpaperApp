package com.example.android.wallpaperapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android.wallpaperapp.Adapters.PhotosAdapter;
import com.example.android.wallpaperapp.Models.Photo;
import com.example.android.wallpaperapp.R;
import com.example.android.wallpaperapp.Webservices.ApiInterface;
import com.example.android.wallpaperapp.Webservices.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosFragment extends Fragment {

    private final String TAG = PhotosFragment.class.getSimpleName();
    @BindView(R.id.progressbar_photos_fragment)
    ProgressBar progressBar;
    @BindView(R.id.recyclerview_photos_fragment)
    RecyclerView recyclerView;
    private PhotosAdapter photosAdapter;
    private List<Photo> photos = new ArrayList<>();


    private Unbinder unbinder;

    public PhotosFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        photosAdapter = new PhotosAdapter(getActivity(), photos);
        recyclerView.setAdapter(photosAdapter);

        showProgressBar(true);
        getPhotos();

        return view;
    }

    private void getPhotos() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Photo>> call = apiInterface.getPhotos();
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.isSuccessful()) {
                    photos.addAll(response.body());
                    photosAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "fail " + response.message());
                }

                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.e(TAG, "fail " + t.getMessage());
                showProgressBar(false);

            }
        });
    }

    private void showProgressBar(boolean isShown) {
        if (isShown) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
