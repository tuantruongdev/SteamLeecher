package com.example.steamleecher69.utils;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.steamleecher69.data.model.GameOverView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by macos on 27,July,2022
 */
public class Regex {

    public static String toRawList(String raw) {
        if (raw == null || raw.trim().length() < 1) {
            Log.e(TAG, "onResponse: nothing response");
            return null;
        }
        Log.d(TAG, "toRawList: asss");

        raw = replaceUnnecessaryChar(raw);

        Pattern pattern = Pattern.compile("<!-- List Items -->(.*?)<!-- End List Items -->");
        Matcher matcher = pattern.matcher(raw);

        if (matcher.find()) {
            // Log.d(TAG, "onResponse: " + matcher.group(1));
            return matcher.group(1);
            // Log.d(TAG, "onResponse: " + matcher.group(1));
        }
        Log.d(TAG, "toRawList: not found");
        return null;
    }

    public static ArrayList<String> toStringGameList(String raw) {
        if (raw == null) {
            return new ArrayList<>();
        }
        ArrayList<String> stringGameList = new ArrayList<>();
        Pattern pattern = Pattern.compile(Const.Regex.ITEM);
        Matcher matcher = pattern.matcher(raw);
        while (matcher.find()) {
            stringGameList.add(matcher.group(0));
            Log.d(TAG, "toStringGameList: " + matcher.group(0));
        }
        return stringGameList;
    }

    public static GameOverView toOverViewGameArrayList(String raw) {
        String href, appid, tagids, image2x, name, finalPriceString = null, originalPriceString = null;
        int search_page, discountPercentage = 0;
        String releaseDate;
        long finalPrice;
        ArrayList<String> platforms = new ArrayList<>();

        boolean isDiscounted = isDiscounted(raw);

        try {
            search_page = Integer.parseInt(getFirstMatcher(Pattern.compile("1x, (.*?) 2x"), raw));
        } catch (Exception e) {
            search_page = 1;
        }
        href = getFirstMatcher(Pattern.compile(Const.Regex.HREF), raw);
        appid = getFirstMatcher(Pattern.compile(Const.Regex.APPID), raw);
        image2x = getFirstMatcher(Pattern.compile(Const.Regex.IMAGE2X), raw);
        name = getFirstMatcher(Pattern.compile(Const.Regex.NAME), raw);
        releaseDate = getFirstMatcher(Pattern.compile(Const.Regex.RELEASE_DATE), raw);

        try {
            if (isDiscounted) {
                originalPriceString = getFirstMatcher(Pattern.compile(Const.Regex.ORIGINAL_PRICE_STRING), raw);
                finalPriceString = getFirstMatcher(Pattern.compile(Const.Regex.FINAL_PRICE_STRING), raw);
                discountPercentage = getDiscountPercentage(getFirstMatcher(Pattern.compile(Const.Regex.DISCOUNT_PERCENTAGE), raw));
            } else {
                finalPriceString = getFirstMatcher(Pattern.compile(Const.Regex.FINAL_PRICE_STRING_NO_DISCOUNT), raw).trim();
            }
            Log.d(TAG, "toOverViewGameArrayList: " + finalPriceString);
            finalPrice = Integer.parseInt(getFirstMatcher(Pattern.compile(Const.Regex.FINAL_PRICE), raw));
        } catch (Exception e) {
            Log.e(TAG, "toOverViewGameArrayList: ", e);
            finalPrice = 0;
            discountPercentage = 0;
        }

        if (raw.contains(Const.Platforms.WIN_PLATFORM)) {
            platforms.add(Const.Platforms.WIN_PLATFORM);
        }
        if (raw.contains(Const.Platforms.MAC_PLATFORM)) {
            platforms.add(Const.Platforms.MAC_PLATFORM);
        }
        if (raw.contains(Const.Platforms.LINUX_PLATFORM)) {
            platforms.add(Const.Platforms.LINUX_PLATFORM);
        }

        return new GameOverView(href, appid, "", search_page, image2x, name, releaseDate, finalPriceString, finalPrice, discountPercentage, platforms, originalPriceString);
    }

    public static GameOverView toGameOverView(String raw, String appid) {
        String href, image2x, name, releaseDate, finalPriceString, originalPriceString = "", developer, publisher, description;
        ArrayList<String> videoList;
        int discount = 0;

        href = String.format("%s%s", Const.Url.STEAM_STORE_URL, Const.Url.GAME_INFO_ENDPOINT).replace("{APPID}", appid);
        //Log.d(TAG, "toGameOverView: "+href);
        name = getFirstMatcher(Pattern.compile(Const.Regex.Detail.NAME), raw);
        releaseDate = getFirstMatcher(Pattern.compile(Const.Regex.Detail.RELEASE_DATE), raw);
        description = getFirstMatcher(Pattern.compile(Const.Regex.Detail.DESCRIPTION), raw);

        String developerItem = getFirstMatcher(Pattern.compile(Const.Regex.Detail.DEVELOPER_ITEM), raw);
        developer = getFirstMatcher(Pattern.compile(Const.Regex.Detail.DEV_PUB), developerItem);

        String pubItem = getFirstMatcher(Pattern.compile(Const.Regex.Detail.PUBLISHER_ITEM), raw);
        publisher = getFirstMatcher(Pattern.compile(Const.Regex.Detail.DEV_PUB), pubItem);

        try {
            String buyGameItem = getFirstMatcher(Pattern.compile(Const.Regex.Detail.BUY_GAME_ITEM), raw);
            //   if (buyGameItem == null)

            if (buyGameItem.contains(Const.Regex.Detail.IS_DISCOUNT)) {
                discount = Integer.parseInt(getFirstMatcher(Pattern.compile(Const.Regex.Detail.DISCOUNT_PERCENTAGE), buyGameItem).replace("-", "").replace("%", ""));
                originalPriceString = getFirstMatcher(Pattern.compile(Const.Regex.Detail.ORIGINAL_PRICE_STRING), buyGameItem);
                finalPriceString = getFirstMatcher(Pattern.compile(Const.Regex.Detail.FINAL_PRICE_STRING), buyGameItem);
            } else {
                String priceItem = getFirstMatcher(Pattern.compile(Const.Regex.Detail.FINAL_PRICE_STRING_NO_DISCOUNT_ITEM), raw);
                finalPriceString = getFirstMatcher(Pattern.compile(Const.Regex.Detail.FINAL_PRICE_STRING_NO_DISCOUNT), priceItem);
            }

        } catch (Exception e) {
            description = "";
            originalPriceString = "";
            finalPriceString = "";
            Log.d(TAG, "toGameOverView: ", e);
        }

        videoList = getVideo480(raw);
//    public GameOverView(String href, String image2x, String name, String releaseDate, String finalPriceString, int discount, String originalPriceString, String developer, String publisher, String description, ArrayList<String> videoList) {
        return new GameOverView(href, "", name, releaseDate, finalPriceString, discount, originalPriceString, developer, publisher, description, videoList);
    }

    public static ArrayList<String> getVideo480(String raw) {
        ArrayList<String> vidList = new ArrayList<>();
        Matcher matcher = Pattern.compile(Const.Regex.Detail.VIDEO_480P).matcher(raw);
        while (matcher.find()) {
            Log.d(TAG, "getFirstMatcher: found");
            vidList.add(matcher.group(1));
        }
        return vidList;
    }

    public static String replaceUnnecessaryChar(String raw) {
        raw = raw.replace("\n", "")
                .replace("\\n", "")
                .replace("\\r", "")
                .replace("\r", "")
                .replace("\t", "")
                .replace("\\t", "")
                .replace("\\\"", "\"")
                .replace("\\/", "/");
        return raw;
    }

    static boolean isDiscounted(String raw) {
        return raw.contains(Const.Regex.DISCOUNT);
    }

    static String getFirstMatcher(Pattern pattern, String raw) {
        Matcher matcher = pattern.matcher(raw);
        while (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    static int getDiscountPercentage(String discount) {
        if (discount != null) {
            discount = discount.replace("-", "").replace("%", "");
            return Integer.parseInt(discount);
        }
        return 0;
    }
}
