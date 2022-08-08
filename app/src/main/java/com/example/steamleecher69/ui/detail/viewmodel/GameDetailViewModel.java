package com.example.steamleecher69.ui.detail.viewmodel;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.steamleecher69.data.local.db.Database;
import com.example.steamleecher69.data.model.api.GameOverView;
import com.example.steamleecher69.data.model.db.Game;
import com.example.steamleecher69.data.repository.GameDataRepository;
import com.example.steamleecher69.ui.detail.callback.IDetailVIewModelCallBack;

/**
 * Created by macos on 03,August,2022
 */
public class GameDetailViewModel extends ViewModel implements IDetailVIewModelCallBack {
    private GameDataRepository gameDataRepository;
    private MutableLiveData<GameOverView> currentGame;
    private String appId;
    private MutableLiveData<Integer> isFavorite;

    public GameDetailViewModel(Database db) {
        gameDataRepository = GameDataRepository.getInstance();
        gameDataRepository.setDb(db);
        currentGame = new MutableLiveData<>();
        isFavorite = new MutableLiveData<>();
    }

    @Override
    public void onResponseData(GameOverView game) {
        currentGame.postValue(game);
    }

    public LiveData<GameOverView> getCurrentGame() {
        return currentGame;
    }

    public LiveData<Integer> getCurrentIsFavorite(){ return isFavorite;}

    public void getGameInfo(String appId) {
        gameDataRepository.getGameInfo(this, appId);
    }

    public void saveGameToFavoriteList() {
        AsyncTask.execute(() -> {
            Game tempGame = currentGame.getValue();
            tempGame.setAppid(appId);
            gameDataRepository.saveGame(currentGame.getValue());
            Log.d(TAG, "saveGameToFavoriteList: " + gameDataRepository.isFavorite(currentGame.getValue().getAppid()));
        });
    }

    public void checkIsFavorite(String appId) {
        this.appId = appId;
        AsyncTask.execute(() -> {
            isFavorite.postValue(gameDataRepository.isFavorite(appId));
            Log.d(TAG, "appid: "+appId+ gameDataRepository.isFavorite(appId));
        });
    }

}
