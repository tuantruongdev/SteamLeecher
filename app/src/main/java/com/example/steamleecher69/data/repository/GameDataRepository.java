package com.example.steamleecher69.data.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.steamleecher69.data.api.ApiFetch;
import com.example.steamleecher69.data.model.GameOverView;
import com.example.steamleecher69.ui.detail.callback.IDetailVIewModelCallBack;
import com.example.steamleecher69.ui.main.callback.IViewModelCallBack;
import com.example.steamleecher69.utils.Const;
import com.example.steamleecher69.utils.Regex;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by macos on 27,July,2022
 */
public class GameDataRepository {
    private static GameDataRepository instance;
    ApiFetch apiFetch = new ApiFetch();

    private GameDataRepository() {
    }

    public static GameDataRepository getInstance() {
        if (instance == null) {
            instance = new GameDataRepository();
        }
        return instance;
    }

    public void getGames(IViewModelCallBack viewModelCallBack, String endpoint, String query, int start, int count) {
        Log.d(TAG, "getGames: ");
        try {
            if (count == 0) {
                endpoint = endpoint.replace("{START}", String.valueOf(count))
                        .replace("{COUNT}", String.valueOf(count))
                        .replace("{QUERY}", query);
                getAllGame(viewModelCallBack, endpoint);
                return;
            }
            endpoint = endpoint.replace("{START}", String.valueOf(start))
                    .replace("{COUNT}", String.valueOf(count))
                    .replace("{QUERY}", query);
            getAllGame(viewModelCallBack, endpoint);

        } catch (Exception e) {
            Log.e(TAG, "getGames: ", e);
        }
    }

    public void getAllGame(IViewModelCallBack viewModelCallBack, String endpoint) throws IOException {

        Call call = apiFetch.sendRequest(endpoint);
        Log.d(TAG, "getAllGame: " + endpoint);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: failed");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String r = response.body().string();
                String rawList = Regex.toRawList(r);
                ArrayList<String> stringGameList = Regex.toStringGameList(rawList);
                ArrayList<GameOverView> gamesOverView = new ArrayList<>();
                for (String game : stringGameList) {
                    gamesOverView.add(Regex.toOverViewGameArrayList(game));
                }
                viewModelCallBack.onResponseData(gamesOverView);
                //Log.d(TAG, "onResponse: "+ r.replace("\n", "").replace("\r",""));
            }
        });
    }

    public void getGameInfo(IDetailVIewModelCallBack detailVIewModelCallBack, String appid) {
        String endpoint = Const.Url.GAME_INFO_ENDPOINT.replace("{APPID}", appid);
        Log.d(TAG, "getGameInfo: "+ endpoint);
        Call call = apiFetch.sendRequest(endpoint);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String r = response.body().string();
                r = Regex.replaceUnnecessaryChar(r);
                Log.d(TAG, "onResponse: "+ r);
                GameOverView go = Regex.toGameOverView(r,appid);
                detailVIewModelCallBack.onResponseData(go);
            }
        });
    }

}
