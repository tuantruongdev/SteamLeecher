package com.example.steamleecher69.ui.saved.view;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.steamleecher69.R;
import com.example.steamleecher69.data.local.db.Database;
import com.example.steamleecher69.data.model.db.Game;
import com.example.steamleecher69.databinding.FragmentSavedBinding;
import com.example.steamleecher69.ui.detail.view.GameDetail;
import com.example.steamleecher69.ui.saved.adapter.SavedListAdapter;
import com.example.steamleecher69.ui.saved.viewmodel.SavedViewModel;

import java.util.ArrayList;

public class SavedFragment extends Fragment implements SavedListAdapter.GameListListener {
    private FragmentSavedBinding binding;
    private RecyclerView recyclerViewMain;
    private SavedListAdapter gameListAdapter;
    private SavedViewModel savedViewModel;

    public SavedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved, container, false);
        savedViewModel = new SavedViewModel(Database.getInstance(getContext()));
        binding.setLifecycleOwner(this);
//        binding.setMainActivityViewModel(mainActivityViewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initRecycleView();
        gameListAdapter = new SavedListAdapter(new ArrayList<>(), this);
        recyclerViewMain.setAdapter(gameListAdapter);
        savedViewModel.getListGame();

        bind();
    }

    @Override
    public void onResume() {
        super.onResume();
        savedViewModel.getListGame();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            savedViewModel.getListGame();
        }
        Log.d(TAG, "onHiddenChanged: " + hidden);
    }

    @Override
    public void onGameClicked(Game game) {
        new GameDetail().starter(getContext(), game);
    }


    private void bind() {
        savedViewModel.getListGameMutableLiveData().observe(getViewLifecycleOwner(), gameOverViews -> {
            if (gameOverViews == null) {
                return;
            }
            Log.d(TAG, "onCreate: response done");
            gameListAdapter.setGameList(gameOverViews);
        });
    }

    private void initRecycleView() {
        recyclerViewMain = binding.savedRec;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewMain.setLayoutManager(linearLayoutManager);
    }
}