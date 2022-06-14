package com.example.campus_positioning_system;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.campus_positioning_system.Database.AppDatabase;
import com.example.campus_positioning_system.Database.NNObjectDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {
    private NNObjectDao nnObjectDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        nnObjectDao = db.getNNObjectDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        NNObject nnObject = new NNObject("Hallo", (float)12, new Node("",1,2,3),1);
        nnObjectDao.insert(nnObject);
        List<String> macList = new ArrayList<>(Arrays.asList("Hallo"));
        List<NNObject> byRelevantData = nnObjectDao.getRelevantData(macList);
        //assert(nnObject.compareTo(byRelevantData.get(0)) == 0);
        System.out.println(byRelevantData.get(0).toString());
    }
}

