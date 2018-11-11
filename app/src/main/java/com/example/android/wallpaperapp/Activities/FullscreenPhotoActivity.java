package com.example.android.wallpaperapp.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.android.wallpaperapp.Models.Photo;
import com.example.android.wallpaperapp.R;
import com.example.android.wallpaperapp.Utils.Functions;
import com.example.android.wallpaperapp.Utils.GlideApp;
import com.example.android.wallpaperapp.Utils.RealmController;
import com.example.android.wallpaperapp.Webservices.ApiInterface;
import com.example.android.wallpaperapp.Webservices.ServiceGenerator;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullscreenPhotoActivity extends AppCompatActivity {

    @BindView(R.id.activity_fullscreen_photo_photo)
    ImageView fullScreenPhoto;

    @BindView(R.id.activity_fullscreen_photo_avatar)
    CircleImageView userAvatar;

    @BindView(R.id.activity_fullscreen_photo_fab_menu)
    FloatingActionMenu fabMenu;

    @BindView(R.id.activity_fullscreen_fab_favorite)
    FloatingActionButton fabFavorite;

    @BindView(R.id.activity_fullscreen_fab_wallpaper)
    FloatingActionButton fabWalpaper;

    @BindView(R.id.activity_fullscreen_photo_username)
    TextView username;

    @BindDrawable(R.drawable.ic_check_favorited)
    Drawable icFavorite;

    @BindDrawable(R.drawable.ic_check_favorite)
    Drawable icFavorited;

    private Bitmap photoBitmap;

    private RealmController realmController;
    private Photo photo;

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        String photoId = intent.getStringExtra("photoId");
        getPhoto(photoId);

        realmController = new RealmController();
        if (realmController.photoExists(photoId)) {
            fabFavorite.setImageDrawable(icFavorited);
        }
    }

    private void getPhoto(String id) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<Photo> call = apiInterface.getPhoto(id);
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                photo = response.body();
                UpdateUI(photo);
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {

            }
        });
    }

    private void UpdateUI(Photo photo) {
        try {
            username.setText(photo.getUser().getUsername());
            GlideApp
                    .with(FullscreenPhotoActivity.this)
                    .load(photo.getUser().getProfileImage().getSmall())
                    .into(userAvatar);

            GlideApp
                    .with(FullscreenPhotoActivity.this)
                    .asBitmap()
                    .load(photo.getUrl().getRegular())
                    .centerCrop()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            fullScreenPhoto.setImageBitmap(resource);
                            photoBitmap = resource;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.activity_fullscreen_fab_favorite)
    public void setFabFavorite() {
        if (realmController.photoExists(photo.getId())) {
            realmController.deletePhoto(photo);
            fabFavorite.setImageDrawable(icFavorited);
            Toast.makeText(FullscreenPhotoActivity.this, "Image removed from favorites", Toast.LENGTH_SHORT).show();
        } else {
            realmController.savePhoto(photo);
            fabFavorite.setImageDrawable(icFavorite);
            Toast.makeText(FullscreenPhotoActivity.this, "Image added to favorites", Toast.LENGTH_SHORT).show();
        }

        fabMenu.close(true);
    }

    @OnClick(R.id.activity_fullscreen_fab_wallpaper)
    public void setFabWallpaper() {
        if (photoBitmap != null) {
            if (Functions.setWallpaper(FullscreenPhotoActivity.this, photoBitmap)) {
                Toast.makeText(FullscreenPhotoActivity.this, "Wallpaper set!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(FullscreenPhotoActivity.this, "Action Failed", Toast.LENGTH_SHORT).show();
            }
        }
        fabMenu.close(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
