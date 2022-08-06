package com.example.steamleecher69.ui.main.view;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.steamleecher69.R;
import com.example.steamleecher69.data.model.GameOverView;
import com.example.steamleecher69.databinding.ActivityMainBinding;
import com.example.steamleecher69.ui.detail.view.GameDetail;
import com.example.steamleecher69.ui.main.adapter.GameListAdapter;
import com.example.steamleecher69.ui.main.callback.IMainActivityViewCallBack;
import com.example.steamleecher69.ui.main.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GameListAdapter.GameListListener, IMainActivityViewCallBack {
    ActivityMainBinding activityMainBinding;
    MainActivityViewModel mainActivityViewModel;
    RecyclerView recyclerViewMain;
    GameListAdapter gameListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityViewModel = new MainActivityViewModel();
        activityMainBinding.setLifecycleOwner(this);
        activityMainBinding.setMainActivityViewModel(mainActivityViewModel);

        initRecycleView();
        gameListAdapter = new GameListAdapter(new ArrayList<>(), this, this);
        recyclerViewMain.setAdapter(gameListAdapter);
        mainActivityViewModel.getListGame();

        bind();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query + "", Toast.LENGTH_SHORT).show();
                mainActivityViewModel.searchListGame(query);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        myActionMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mainActivityViewModel.getListGame();
                return true;
            }
        });

        searchView.setOnCloseListener(() -> {
            mainActivityViewModel.getListGame();
            return false;
        });
        return true;
    }


    @Override
    public void onGameClicked(GameOverView gameOverView) {
        Log.d(TAG, "onGameClicked: clicked");
        new GameDetail().starter(this, gameOverView);
    }

    @Override
    public void setGameImage(ImageView img, String url) {
        Glide.with(this)
                .load(url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.capsule_231x87)
                .into(img);
    }

    private void bind(){
        mainActivityViewModel.getListGameOverViewLiveData().observe(this, gameOverViews -> {
            if (gameOverViews == null) {
                return;
            }
            Log.d(TAG, "onCreate: response done");
            gameListAdapter.setGameList(gameOverViews);
        });

    }

    private void initRecycleView() {
        recyclerViewMain = activityMainBinding.gameRecyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewMain.setLayoutManager(linearLayoutManager);
        gameListAdapter = new GameListAdapter(new ArrayList<GameOverView>(), this, this);

        recyclerViewMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!mainActivityViewModel.isLoading()) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mainActivityViewModel.getListGameSize() - 1) {

                        Log.d(TAG, "onScrolled: loading");
                        mainActivityViewModel.setLoading(true);
                        mainActivityViewModel.getMoreListGame();
                    }
                }
            }
        });
    }
}


//https://store.steampowered.com/