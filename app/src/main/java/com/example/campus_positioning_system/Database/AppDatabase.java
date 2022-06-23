package com.example.campus_positioning_system.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.test.core.app.ApplicationProvider;

import com.example.campus_positioning_system.Activitys.MainActivity;
import com.example.campus_positioning_system.NNObject;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


@Database(entities = {NNObject.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract NNObjectDao nnObjectDao();
    public volatile static AppDatabase db;

    public static AppDatabase getInstance(){
        if(db == null){
            Context context = MainActivity.mainContext();
            db = Room.databaseBuilder(context, AppDatabase.class,"ReferenceData").build();
            db.clearAllTables();
            if(db.nnObjectDao().getRelevantData(sample()).size() == 0)
                DatabaseImporter.readFile(db);
        }
        return db;
    }

    private static List<String> sample(){
        /*


        61/18/4;270;HFU Open;C2:FB:E4:84:75:A4;-75;36
        61/18/4;270;HFU Open;C2:FB:E4:84:72:FD;-62;1
        61/18/4;270;eduroam;BE:FB:E4:84:78:80;-65;48
        61/18/4;270;eduroam;BE:FB:E4:84:72:FC;-72;40
        61/18/4;270;eduroam;BE:FB:E4:84:78:81;-68;11
        */

        List<String> res = new LinkedList<String>();
        res.add("C2:FB:E4:84:75:A4");
        res.add("C2:FB:E4:84:72:FD");
        res.add("BE:FB:E4:84:78:80");
        res.add("BE:FB:E4:84:72:FC");
        res.add("BE:FB:E4:84:78:81");

        return res;
    }
}
