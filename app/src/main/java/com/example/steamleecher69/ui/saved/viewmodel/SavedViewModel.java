package com.example.steamleecher69.ui.saved.viewmodel;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.steamleecher69.data.local.db.Database;
import com.example.steamleecher69.data.model.db.Game;
import com.example.steamleecher69.data.repository.GameDataRepository;
import com.example.steamleecher69.utils.Const;

import java.util.ArrayList;

/**
 * Created by macos on 08,August,2022
 */
public class SavedViewModel extends ViewModel {
    private GameDataRepository gameDataRepository;
    private MutableLiveData<ArrayList<Game>> listGameMutableLiveData;

    public SavedViewModel(Database db) {
        gameDataRepository = GameDataRepository.getInstance();
        gameDataRepository.setDb(db);
        listGameMutableLiveData = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Game>> getListGameMutableLiveData() {
        return listGameMutableLiveData;
    }

    public void getListGame() {
        AsyncTask.execute(() -> {
            ArrayList<Game> savedGame = gameDataRepository.getSavedGame();
            if (savedGame == null) return;
            listGameMutableLiveData.postValue(savedGame);
        });
    }

}
