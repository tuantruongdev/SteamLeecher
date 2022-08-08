package com.example.steamleecher69.data.model.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by macos on 07,August,2022
 */
@Entity(tableName = "games")
public class Game {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String appid;
    private String name;
    private String finalPriceString;
    private String image2x;

    public Game(String appid, String name, String finalPriceString, String image2x) {
        this.appid = appid;
        this.name = name;
        this.finalPriceString = finalPriceString;
        this.image2x = image2x;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFinalPriceString() {
        return finalPriceString;
    }

    public void setFinalPriceString(String finalPriceString) {
        this.finalPriceString = finalPriceString;
    }

    public String getImage2x() {
        return image2x;
    }

    public void setImage2x(String image2x) {
        this.image2x = image2x;
    }
}
