package com.example.steamleecher69.data.remote.api;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.steamleecher69.utils.Const;

import org.riversun.okhttp3.OkHttp3CookieHelper;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by macos on 26,July,2022
 */
public class ApiFetch {
    private final OkHttpClient client;

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
