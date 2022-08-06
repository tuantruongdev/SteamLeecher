package com.example.steamleecher69.utils;

/**
 * Created by macos on 26,July,2022
 */
public interface Const {
    interface Url {
        String STEAM_STORE_URL = "https://store.steampowered.com/";
        String TOP_SELLER_ENDPOINT = "search/?filter=topsellers";
        String MORE_GAMES_ENDPOINT = "search/results/?query&start={START}&count={COUNT}&dynamic_data=&sort_by=_ASC&snr=1_7_7_7000_7&filter=topsellers&infinite=1";
        String SEARCH_GAMES_ENDPOINT = "search/results/?query&start={START}&count={COUNT}&dynamic_data=&term={QUERY}&force_infinite=1&snr=1_7_7_151_7&infinite=1";
        String GAME_INFO_ENDPOINT = "app/{APPID}";
    }

    interface Cookie{
        String BIRTH_TIME = "birthtime";
        String WMC = "wants_mature_content";
        String BIRTH_TIME_VALUE = "943981201";
        String WMC_VALUE = "1";
    }

    interface Platforms {
        String MAC_PLATFORM = "platform_img mac";
        String WIN_PLATFORM = "platform_img win";
        String LINUX_PLATFORM = "platform_img linux";
    }

    interface Regex {
        String ITEM = "<a href(.*?)</a>";
        String HREF = "href=\"(.*?)\"";
        String APPID = "data-ds-appid=\"(.*?)\"";
        String IMAGE2X = "1x, (.*?) 2x";
        String NAME = "<span class=\"title\">(.*?)</span>";
        String RELEASE_DATE = "<div class=\"col search_released responsive_secondrow\">(.*?)</div>";
        String ORIGINAL_PRICE_STRING = "<strike>(.*?)</strike>";
        String FINAL_PRICE_STRING = "</span><br>(.*?) ";
        String FINAL_PRICE_STRING_NO_DISCOUNT = "<div class=\"col search_price  responsive_secondrow\">(.*?)</div>";
        String DISCOUNT_PERCENTAGE = "<span>(.*?)</span>";
        String FINAL_PRICE = "data-price-final=\"(.*?)\"";
        String DISCOUNT = "</strike>";

        interface Detail {
            String NAME = "class=\"apphub_AppName\">(.*?)</div>";
            String DEVELOPER_ITEM = "<b>Developer:</b>(.*?)</div>";
            String PUBLISHER_ITEM = "<b>Publisher:</b>(.*?)</div>";
            String DEV_PUB = ">(.*?)</a>";
            String RELEASE_DATE = "<div class=\"date\">(.*?)</div>";

            String BUY_GAME_ITEM = "<div class=\"game_purchase_action\">(.*?)<span>Add to Cart</span>";
            String IS_DISCOUNT = "discount_pct";
            String DISCOUNT_PERCENTAGE = "<div class=\"discount_pct\">(.*?)</div>";
            String ORIGINAL_PRICE_STRING = "<div class=\"discount_original_price\">(.*?)</div>";
            String FINAL_PRICE_STRING = "<div class=\"discount_final_price\">(.*?)</div>";

            String FINAL_PRICE_STRING_NO_DISCOUNT_ITEM = "<div class=\"game_purchase_action_bg\">(.*?)<div class=\"btn_addtocart\">";// match 2
            String FINAL_PRICE_STRING_NO_DISCOUNT = "\">(.*?)</div>";// match 2

            String DESCRIPTION = "<div class=\"game_description_snippet\">(.*?)</div>";
            String VIDEO_480P ="data-mp4-source=\"(.*?)\"";
        }
    }

    interface View {
        int VIEW_TYPE_ITEM = 0;
        int VIEW_TYPE_LOADING = 1;
    }

    interface Query {
        int BASE_QUERY_COUNT = 50;
        int MORE_QUERY_COUNT = 20;
    }

    interface Game {
        String GAME_NAME = "gameName";
        String GAME_FINAL_PRICE = "gameFinalPrice";
        String GAME_ID = "gameId";
        String GAME_URL = "gameUrl";
        String GAME_IMG = "gameImg";
    }
}
