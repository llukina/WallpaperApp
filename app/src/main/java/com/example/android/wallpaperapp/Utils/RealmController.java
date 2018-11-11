package com.example.android.wallpaperapp.Utils;

import com.example.android.wallpaperapp.Models.Photo;

import java.util.List;

import io.realm.Realm;

public class RealmController {
    private final Realm realm;

    public RealmController() {
        realm = Realm.getDefaultInstance();
    }

    public void savePhoto(Photo photo) {
        realm.beginTransaction();
        realm.copyToRealm(photo);
        realm.commitTransaction();
    }

    public void deletePhoto(Photo photo) {
        final Photo fPhoto = photo;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Photo resultPhoto = realm.where(Photo.class).equalTo("id", fPhoto.getId()).findFirst();
                resultPhoto.deleteFromRealm();
            }
        });
    }

    public boolean photoExists(String photoId) {
        Photo resultPhoto = realm.where(Photo.class).equalTo("id", photoId).findFirst();
        if (resultPhoto == null)
            return false;
        return true;
    }

    public List<Photo> getPhotos() {
        return realm.where(Photo.class).findAll();
    }
}
