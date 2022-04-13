package com.example.campus_positioning_system;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NNObjectDao {
    @Query("SELECT * FROM nnobject")
    List<NNObject> getAll();
}
