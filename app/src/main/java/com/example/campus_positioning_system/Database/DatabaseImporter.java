package com.example.campus_positioning_system;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseImporter {

    private static InputStream getStream() throws IOException{
        Context context = ApplicationProvider.getApplicationContext();
        return context.getAssets().open("databaseAsCsv.csv");
    }

    public static void readFile(AppDatabase db){
        NNObjectDao dao = db.nnObjectDao();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getStream()));
            reader.readLine();
           reader.lines()
                   .map(v->v.split("[!;]+"))
                    .forEach(v->dao.insert(new NNObject
                            (v[2],v[3],Float.parseFloat(v[4]),
                                    Converters.fromString(v[0]),Integer.parseInt(v[1]))));
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

    }

}
