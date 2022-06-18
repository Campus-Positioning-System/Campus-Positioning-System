package com.example.campus_positioning_system.Database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.test.core.app.ApplicationProvider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AppDatabaseSingelton {
    private static AppDatabase db;

   static public AppDatabase getInstance(){
        if(db != null)
            return db;
        return createDatabase();
    }
    static private AppDatabase createDatabase(){
       Object o = null;
       try {
           o = readDatabaseReference();
       }catch(Exception e){e.printStackTrace();}
        if (o instanceof AppDatabase)
            db = (AppDatabase) o;
        else {
            Context context = ApplicationProvider.getApplicationContext();
            db = Room.databaseBuilder(context, AppDatabase.class,"ReferenceData").build();
            DatabaseImporter.readFile(db);
            try {
                writeDatabaseObject();
            }catch(Exception e){e.printStackTrace();}
        }
        return db;
    }
    private static void writeDatabaseObject() throws FileNotFoundException, IOException {
        ObjectOutputStream out = new ObjectOutputStream( ApplicationProvider.getApplicationContext().openFileOutput("databaseRef.lol",Context.MODE_PRIVATE));
        out.writeObject(db);
    }
    private static Object readDatabaseReference() throws FileNotFoundException, IOException, ClassNotFoundException{
        ObjectInputStream in = new ObjectInputStream(ApplicationProvider.getApplicationContext().openFileInput("databaseRed.lol"));
        Object o = in.readObject();
        return o;
    }

}
