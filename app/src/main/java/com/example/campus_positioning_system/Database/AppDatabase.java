package com.example.campus_positioning_system.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.test.core.app.ApplicationProvider;

import com.example.campus_positioning_system.NNObject;

import java.io.Serializable;


@Database(entities = {NNObject.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract NNObjectDao nnObjectDao();
    public volatile static AppDatabase db;

    public NNObjectDao k() {
        return this.nnObjectDao();
    }


    public static AppDatabase getInstance(){
        if(db == null){
            Context context = ApplicationProvider.getApplicationContext();
            db = Room.databaseBuilder(context, AppDatabase.class,"ReferenceData").build();
            if(db.nnObjectDao().getAllData().size() == 0)
                DatabaseImporter.readFile(db);
        }
        return db;

    }
}
