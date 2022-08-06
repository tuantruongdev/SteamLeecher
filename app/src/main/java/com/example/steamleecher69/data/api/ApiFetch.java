package com.example.steamleecher69.data.api;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.steamleecher69.data.model.GameOverView;
import com.example.steamleecher69.utils.Const;
import com.example.steamleecher69.utils.Regex;
import com.google.android.exoplayer2.C;

import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by macos on 26,July,2022
 */
public class ApiFetch {
    final OkHttpClient client;

    public ApiFetch() {
        OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
        cookieHelper.setCookie(Const.Url.STEAM_STORE_URL, Const.Cookie.BIRTH_TIME, Const.Cookie.BIRTH_TIME_VALUE);
        cookieHelper.setCookie(Const.Url.STEAM_STORE_URL, Const.Cookie.WMC, Const.Cookie.WMC_VALUE);
        client = new OkHttpClient().newBuilder().cookieJar(cookieHelper.cookieJar()).build();;
    }

    public Call sendRequest(String endpoint) {
        Log.d(TAG, "sendRequest: ");
        Request rq = new Request.Builder().url(Const.Url.STEAM_STORE_URL + endpoint)
                .build();
        return client.newCall(rq);
    }
}
