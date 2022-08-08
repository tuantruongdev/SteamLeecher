package com.example.steamleecher69.data.model.api;

import com.example.steamleecher69.data.model.db.Game;

import java.util.ArrayList;

/**
 * Created by macos on 27,July,2022
 */
public class GameOverView extends Game {
    private String href;
    private String tagids;
    private int search_page ;
    private String releaseDate;
    private long finalPrice;
    private int discount;
    private ArrayList<String> platforms;
    private String originalPriceString;
    private String developer;
    private String publisher;
    private String description;
    private ArrayList<String> videoList;

    public GameOverView(String appid, String name, String finalPriceString, String image2x) {
        super(appid, name, finalPriceString, image2x);
    }

    public GameOverView(String appid, String name, String finalPriceString, String image2x, String href, String tagids, int search_page, String releaseDate, long finalPrice, int discount, ArrayList<String> platforms, String originalPriceString, String developer, String publisher, String description, ArrayList<String> videoList) {
        super(appid, name, finalPriceString, image2x);
        this.href = href;
        this.tagids = tagids;
        this.search_page = search_page;
        this.releaseDate = releaseDate;
        this.finalPrice = finalPrice;
        this.discount = discount;
        this.platforms = platforms;
        this.originalPriceString = originalPriceString;
        this.developer = developer;
        this.publisher = publisher;
        this.description = description;
        this.videoList = videoList;
    }

    public GameOverView(String href, String appid, String s, int search_page, String image2x, String name, String releaseDate, String finalPriceString, long finalPrice, int discountPercentage, ArrayList<String> platforms, String originalPriceString) {
        super(appid,name,finalPriceString,image2x);
        this.tagids = s;
        this.search_page = search_page;
        this.href = href;
        this.releaseDate = releaseDate;
        this.finalPrice= finalPrice;
        this.discount = discountPercentage;
        this.platforms = platforms;
        this.originalPriceString = originalPriceString;
    }

    public GameOverView(String href, String s, String name, String releaseDate, String finalPriceString, int discount, String originalPriceString, String developer, String publisher, String description, ArrayList<String> videoList) {
        super("",name,finalPriceString,"");
        this.tagids = s;
        this.href = href;
        this.releaseDate = releaseDate;
        this.originalPriceString = originalPriceString;
        this.discount = discount;
        this.developer = developer;
        this.publisher = publisher;
        this.description = description;
        this.videoList = videoList;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTagids() {
        return tagids;
    }

    public void setTagids(String tagids) {
        this.tagids = tagids;
    }

    public int getSearch_page() {
        return search_page;
    }

    public void setSearch_page(int search_page) {
        this.search_page = search_page;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(long finalPrice) {
        this.finalPrice = finalPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public ArrayList<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(ArrayList<String> platforms) {
        this.platforms = platforms;
    }

    public String getOriginalPriceString() {
        return originalPriceString;
    }

    public void setOriginalPriceString(String originalPriceString) {
        this.originalPriceString = originalPriceString;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getVideoList() {
        return videoList;
    }

    public void setVideoList(ArrayList<String> videoList) {
        this.videoList = videoList;
    }


}

