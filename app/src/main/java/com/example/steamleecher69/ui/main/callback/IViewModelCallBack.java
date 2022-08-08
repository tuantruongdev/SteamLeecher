package com.example.steamleecher69.ui.main.callback;

import com.example.steamleecher69.data.model.api.GameOverView;

import java.util.ArrayList;

/**
 * Created by macos on 27,July,2022
 */
public interface IViewModelCallBack {
     void onResponseData(ArrayList<GameOverView> games);

}
