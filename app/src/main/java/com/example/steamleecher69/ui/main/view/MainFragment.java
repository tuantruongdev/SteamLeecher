package com.example.steamleecher69.ui.main.view;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.steamleecher69.R;
import com.example.steamleecher69.data.model.api.GameOverView;
import com.example.steamleecher69.data.model.db.Game;
import com.example.steamleecher69.databinding.FragmentMainBinding;
import com.example.steamleecher69.ui.detail.view.GameDetail;
import com.example.steamleecher69.ui.main.adapter.GameListAdapter;
import com.example.steamleecher69.ui.main.callback.IMainActivityViewCallBack;
import com.example.steamleecher69.ui.main.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

public class MainFragment extends Fragment implements IMainActivityViewCallBack, GameListAdapter.GameListListener {
    private FragmentMainBinding binding;
    private MainActivityViewModel mainActivityViewModel;
    private RecyclerView recyclerViewMain;
    private GameListAdapter gameListAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        mainActivityViewModel = new MainActivityViewModel();
        binding.setLifecycleOwner(this);
        binding.setMainActivityViewModel(mainActivityViewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycleView();
        gameListAdapter = new GameListAdapter(new ArrayList<>(), this, this);
        recyclerViewMain.setAdapter(gameListAdapter);
        mainActivityViewModel.getListGame();

        bind();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_toolbar_menu, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query + "", Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public void onGameClicked(GameOverView gameOverView) {
        Log.d(TAG, "onGameClicked: clicked");
        if (gameOverView == null) return;
        Game tempGame = new Game(gameOverView.getAppid(), gameOverView.getName(), gameOverView.getFinalPriceString(), gameOverView.getImage2x());
        new GameDetail().starter(getContext(), tempGame);
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


    private void bind() {
        mainActivityViewModel.getListGameOverViewLiveData().observe(getViewLifecycleOwner(), gameOverViews -> {
            if (gameOverViews == null) {
                return;
            }
            Log.d(TAG, "onCreate: response done");
            gameListAdapter.setGameList(gameOverViews);
        });
    }

    private void initRecycleView() {
        recyclerViewMain = binding.gameRecyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
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