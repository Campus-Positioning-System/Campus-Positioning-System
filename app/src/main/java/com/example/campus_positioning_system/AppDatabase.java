package com.example.campus_positioning_system;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NNObject.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NNObjectDao nnObjectDao();
}
