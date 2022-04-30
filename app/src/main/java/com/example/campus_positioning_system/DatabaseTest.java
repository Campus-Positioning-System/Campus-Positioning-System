package com.example.campus_positioning_system;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import java.util.List;

public class DatabaseTest {

    public DatabaseTest(Context context) {


        AppDatabase database = Room.databaseBuilder(context, AppDatabase.class, "test").build();
        NNObjectDao nnObjectDao = database.nnObjectDao();
        List<NNObject> nnObjects = nnObjectDao.getAllData();

        for(int i=0; i<nnObjects.size();++i){
            System.out.println(nnObjects.get(i));
        }
    }


}
