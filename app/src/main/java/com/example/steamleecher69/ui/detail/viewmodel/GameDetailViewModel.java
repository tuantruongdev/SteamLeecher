package com.example.steamleecher69.ui.detail.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.steamleecher69.data.model.GameOverView;
import com.example.steamleecher69.data.repository.GameDataRepository;
import com.example.steamleecher69.ui.detail.callback.IDetailVIewModelCallBack;

/**
 * Created by macos on 03,August,2022
 */
public class GameDetailViewModel extends ViewModel implements IDetailVIewModelCallBack {
    GameDataRepository gameDataRepository;
    MutableLiveData<GameOverView> currentGame;

    public GameDetailViewModel() {
        gameDataRepository = GameDataRepository.getInstance();
        currentGame = new MutableLiveData<>();
    }

    @Override
    public void onResponseData(GameOverView game) {
        // gameDetailViewCallBack.updateGameInfo(game);
        currentGame.postValue(game);
        Log.d(TAG, "onResponseData: zzz");
    }

    public LiveData<GameOverView> getCurrentGame() {
        return currentGame;
    }

    public void getGameInfo(String appId) {
        gameDataRepository.getGameInfo(this, appId);
    }
}
