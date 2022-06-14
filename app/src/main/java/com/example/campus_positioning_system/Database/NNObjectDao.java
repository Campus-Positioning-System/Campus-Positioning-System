package com.example.campus_positioning_system.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.campus_positioning_system.NNObject;

import java.util.List;
import java.util.TreeSet;

@Dao
public interface NNObjectDao {

    @Insert
    void insertNNObjects(List<NNObject> list);

    @Insert
    void insert(NNObject obj);

    @Query("Select * FROM NNObject")
    List<NNObject> getAllData();

    @Query("SELECT * FROM NNObject WHERE Mac IN (:list)")
    List<NNObject> getRelevantData(List<String> list);
}
