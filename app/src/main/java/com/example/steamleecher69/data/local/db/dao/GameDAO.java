package com.example.steamleecher69.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.steamleecher69.data.model.db.Game;

import java.util.List;

/**
 * Created by macos on 07,August,2022
 */
@Dao
public interface GameDAO {
    @Insert
    void insertGame(Game game);

    @Query("SELECT * FROM games")
    List<Game> getListGame();

    @Query("SELECT * FROM games Where appid like :appid")
    List<Game> getGameByAppId(String appid);

    @Query("SELECT Count(*) FROM games Where appid like :appid")
    int countByAppId(String appid);

    @Query("DELETE FROM games WHERE appid like :q")
    void updateGame(String q);

}
