package com.example.steamleecher69.data.local.db;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.steamleecher69.data.local.db.dao.GameDAO;
import com.example.steamleecher69.data.model.db.Game;
import com.example.steamleecher69.utils.Const;


/**
 * Created by macos on 19,July,2022
 */
@androidx.room.Database(entities = {Game.class}, version = 420)
public abstract class Database extends RoomDatabase {
    private static Database instance;

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, Database.class, Const.Database.DB_NAME)
                    .build();
        }
        return instance;
    }

    public abstract GameDAO gameDAO();
}
