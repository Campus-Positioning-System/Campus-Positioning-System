package com.example.campus_positioning_system;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.campus_positioning_system.Database.AppDatabase;
import com.example.campus_positioning_system.Database.NNObjectDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {
    private NNObjectDao nnObjectDao;
    private AppDatabase db;

    @Before
    public void createDb() {
      db = AppDatabase.getInstance();
      db.clearAllTables();
      nnObjectDao = db.nnObjectDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        List list = nnObjectDao.getAllData();
        TreeSet<NNObject> tree = new TreeSet<>(list);
        assert(list.size() == 46140);

    }
}

