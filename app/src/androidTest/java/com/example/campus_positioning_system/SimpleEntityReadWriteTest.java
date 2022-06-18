package com.example.campus_positioning_system;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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
      db = AppDatabaseSingelton.getInstance();
        //   db.clearAllTables();
        nnObjectDao = db.getNNObjectDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        List list = nnObjectDao.getAllData();
        assert(list.size() == 46141);

    }
}

