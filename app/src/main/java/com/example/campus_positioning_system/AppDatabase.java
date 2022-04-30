package com.example.campus_positioning_system;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {NNObject.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract NNObjectDao nnObjectDao();

    public NNObjectDao getNNObjectDao() {
        return this.nnObjectDao();
    }
}
