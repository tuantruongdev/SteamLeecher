package com.example.steamleecher69.ui.main.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.steamleecher69.data.model.api.GameOverView;
import com.example.steamleecher69.data.repository.GameDataRepository;
import com.example.steamleecher69.ui.main.callback.IViewModelCallBack;
import com.example.steamleecher69.utils.Const;

import java.util.ArrayList;

/**
 * Created by macos on 27,July,2022
 */
public class MainActivityViewModel extends ViewModel implements IViewModelCallBack {
    private GameDataRepository gameDataRepository;
    private MutableLiveData<ArrayList<GameOverView>> listGameOverViewLiveData;
    private ArrayList<GameOverView> listGameOverView;
    private int queryCount = 0;
    private boolean isLoading = false;

    public MainActivityViewModel() {
        gameDataRepository = GameDataRepository.getInstance();
        listGameOverViewLiveData = new MutableLiveData<>();
        listGameOverView = new ArrayList<>();
    }

    @Override
    public void onResponseData(ArrayList<GameOverView> games) {
        Log.d(TAG, "onResponseData: ");
        isLoading = false;
        queryCount += games.size();
        listGameOverView.addAll(games);
        listGameOverView.add(null);
        listGameOverViewLiveData.postValue(listGameOverView);
    }

    public int getListGameSize() {
        return listGameOverView.size();
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void getListGame() {
        Log.d(TAG, "getListGame: is loading" + isLoading);
        initSearch();
        gameDataRepository.getGames(this, Const.Url.SEARCH_GAMES_ENDPOINT, "", 0, Const.Query.BASE_QUERY_COUNT);
    }

    public void searchListGame(String query) {
        if (isLoading == true) return;
        initSearch();
        gameDataRepository.getGames(this, Const.Url.SEARCH_GAMES_ENDPOINT, query, 0, Const.Query.BASE_QUERY_COUNT);
    }

    public void getMoreListGame() {
        Log.d(TAG, "getMoreListGame: 1");
        if (listGameOverView != null && listGameOverView.size() > 0 && listGameOverView.size() >= Const.Query.BASE_QUERY_COUNT) {
            listGameOverView.remove(getListGameSize() - 1);
            Log.d(TAG, "getMoreListGame:2 " + queryCount);
            gameDataRepository.getGames(this, Const.Url.SEARCH_GAMES_ENDPOINT, "", queryCount, Const.Query.MORE_QUERY_COUNT);
        }
    }

    private boolean initSearch() {
        isLoading = true;
        queryCount = 0;
        listGameOverView.clear();
        listGameOverViewLiveData.postValue(listGameOverView);
        return true;
    }

    public MutableLiveData<ArrayList<GameOverView>> getListGameOverViewLiveData() {
        return listGameOverViewLiveData;
    }

    public void setListGameOverViewLiveData(MutableLiveData<ArrayList<GameOverView>> gameOverViewLiveData) {
        this.listGameOverViewLiveData = gameOverViewLiveData;
    }
}
