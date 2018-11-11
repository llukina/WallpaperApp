package com.example.android.wallpaperapp.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.wallpaperapp.Fragments.CollectionsFragment;
import com.example.android.wallpaperapp.Fragments.FavoriteFragment;
import com.example.android.wallpaperapp.Fragments.PhotosFragment;
import com.example.android.wallpaperapp.R;
import com.example.android.wallpaperapp.Utils.Functions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        PhotosFragment photosFragment = new PhotosFragment();
        Functions.changeMainFragment(MainActivity.this, photosFragment);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_photos) {
            PhotosFragment photosFragment = new PhotosFragment();
            Functions.changeMainFragment(MainActivity.this, photosFragment);
        } else if (id == R.id.nav_collections) {
            CollectionsFragment collectionsFragment = new CollectionsFragment();
            Functions.changeMainFragment(MainActivity.this, collectionsFragment);
        } else if (id == R.id.nav_favorite) {
            FavoriteFragment favoriteFragment = new FavoriteFragment();
            Functions.changeMainFragment(MainActivity.this, favoriteFragment);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
