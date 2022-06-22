package com.example.campus_positioning_system.Database;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.example.campus_positioning_system.Activitys.MainActivity;
import com.example.campus_positioning_system.NNObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseImporter {

    private static InputStream getStream(String s) throws IOException{
        Context context = MainActivity.mainContext();
        return context.getAssets().open(s);
    }


    public static List<NNObject> getTestData(String filepath){
        List<NNObject> res = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getStream(filepath)));
            reader.readLine();
            reader.lines().map(v->v.split("[!,]+"))
                    .forEach(v->res.add(new NNObject
                            (v[1].replace('\"',' ').trim(),Float.parseFloat(v[2].replace('\"',' ').trim()),
                                    null,0)));
        }catch(Exception e){e.printStackTrace();}
        return res;
    }

    public static void readFile(AppDatabase db){
        NNObjectDao dao = db.nnObjectDao();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getStream("csv_combined_filtered.csv")));
            reader.readLine();
           reader.lines()
                   .map(v->v.split("[!;]+"))
                    .forEach(v->dao.insert(new NNObject
                            (v[3].trim(),Float.parseFloat(v[4]),
                                    Converters.fromString(v[0]),Integer.parseInt(v[1]))));
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

    }

}
