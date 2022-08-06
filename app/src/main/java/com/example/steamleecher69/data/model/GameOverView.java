package com.example.steamleecher69.data.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by macos on 27,July,2022
 */
public class GameOverView
{
    private String href;
    private String appid;
    private String tagids;
    private int search_page ;
    private String image2x;
    private String name;
    private String releaseDate;
    private String finalPriceString;
    private long finalPrice;
    private int discount;
    private ArrayList<String> platforms;
    private String originalPriceString;
    private String developer;
    private String publisher;
    private String description;
    private ArrayList<String> videoList;

    public GameOverView(String href, String image2x, String name, String releaseDate, String finalPriceString, int discount, String originalPriceString, String developer, String publisher, String description, ArrayList<String> videoList) {
        this.href = href;
        this.image2x = image2x;
        this.name = name;
        this.releaseDate = releaseDate;
        this.finalPriceString = finalPriceString;
        this.discount = discount;
        this.originalPriceString = originalPriceString;
        this.developer = developer;
        this.publisher = publisher;
        this.description = description;
        this.videoList = videoList;
    }

    public GameOverView(String href, String appid, String tagids, int search_page, String image2x, String name, String releaseDate, String finalPriceString, long finalPrice, int discount, ArrayList<String> platforms, String originalPriceString) {
        this.href = href;
        this.appid = appid;
        this.tagids = tagids;
        this.search_page = search_page;
        this.image2x = image2x;
        this.name = name;
        this.releaseDate = releaseDate;
        this.finalPriceString = finalPriceString;
        this.finalPrice = finalPrice;
        this.discount = discount;
        this.platforms = platforms;
        this.originalPriceString = originalPriceString;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
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

    public String getImage2x() {
        return image2x;
    }

    public void setImage2x(String image2x) {
        this.image2x = image2x;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getFinalPriceString() {
        return finalPriceString;
    }

    public void setFinalPriceString(String finalPriceString) {
        this.finalPriceString = finalPriceString;
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

