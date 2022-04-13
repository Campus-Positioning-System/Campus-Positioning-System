package com.example.campus_positioning_system;

import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    WifiScanner wifiScanner = new WifiScanner();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        AppDatabase database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "test").build();
        NNObjectDao nnObjectDao = database.nnObjectDao();
        List<NNObject> nnObjects = nnObjectDao.getAll();

        for(int i=0; i<nnObjects.size();++i){
            System.out.println(nnObjects.get(i));
        }




    }


    protected void setViewList() {
        ListView wifiListView = (ListView) findViewById(R.id.WifiListView);

        List<String> wifiList = wifiScanner.getList();
        //ArrayAdapter<String> wifiList_ArrayAdapter = new ArrayAdapter<>(this,android.R.layout.list_content, str);
    }
}