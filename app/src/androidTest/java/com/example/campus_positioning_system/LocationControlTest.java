package com.example.campus_positioning_system;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.campus_positioning_system.Database.AppDatabase;
import com.example.campus_positioning_system.Database.DatabaseImporter;
import com.example.campus_positioning_system.Database.NNObjectDao;
import com.example.campus_positioning_system.LocationNavigation.LocationControl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class LocationControlTest {
/*
58/19/3;270;eduroam;;;36
58/19/3;270;HFU Open;;;11
58/19/3;270;HFU Open;;-;36
58/19/3;270;HFU Open;;;40
58/19/3;270;HFU Open;;;44
58/19/3;270;HFU Open;;;11
58/19/3;270;HFU Open;;;36
58/19/3;270;HFU Open;;;1
58/19/3;270;HFU Guest;;;36
58/19/3;270;HFU Guest;;;8
58/19/3;270;eduroam;;;48
58/19/3;270;eduroam;;;40
58/19/3;270;eduroam;;;11
58/19/3;270;eduroam;;;6
58/19/3;270;HFU Open;;;44
58/19/3;270;HFU Guest;;;48
58/19/3;270;eduroam;;;36
58/19/3;270;eduroam;;;44
60/20/3;0;eduroam;;;1
60/20/3;0;HFU Open;;;11
60/20/3;0;HFU Guest;;;36
60/20/3;0;HFU Guest;;;44
*/
    private AppDatabase db;
    private NNObjectDao dao;


    public static List<NNObject> createData1() {    //Node = 58/19/3
        NNObject n1 = new NNObject("C2:FB:E4:80:84:21",(float)-81,null,270);
        NNObject n2 = new NNObject("B4:FB:E4:84:78:80",(float)-77,null,270);
        NNObject n3 = new NNObject("BE:FB:E4:84:75:A4",(float)-75,null,270);
        NNObject n4 = new NNObject("B6:FB:E4:21:B2:B7",(float)-86,null,270);
        NNObject n5 = new NNObject("BE:FB:E4:84:72:FD",(float)-70,null,270);
        NNObject n6 = new NNObject("C2:FB:E4:84:78:81",(float)-63,null,270);
        NNObject n7 = new NNObject("B4:FB:E4:84:75:A4",(float)-65,null,270);
        NNObject n8 = new NNObject("B4:FB:E4:21:B2:B7",(float)-83,null,270);
        NNObject n9 = new NNObject("BE:FB:E4:80:96:D9",(float)-89,null,270);
        NNObject n10 = new NNObject("86:8A:20:0F:32:C3",(float)-91,null,270);
        NNObject n11 = new NNObject("86:8A:20:0A:F9:7E",(float)90,null,270);
        NNObject n12 = new NNObject("C2:FB:E4:84:72:FC",(float)-77,null,270);
        NNObject n13 = new NNObject("B6:FB:E4:31:B2:B7",(float)-88,null,270);
        NNObject n14 = new NNObject("C2:FB:E4:84:78:81",(float)-73,null,270);
        NNObject n15 = new NNObject("C2:FB:E4:84:75:A4",(float)-76,null,270);
        NNObject n16 = new NNObject("C2:FB:E4:84:72:FD",(float)-69,null,270);
        NNObject n17 = new NNObject("B4:FB:E4:80:96:D9",(float)-88,null,270);
        NNObject n18 = new NNObject("B4:FB:E4:C4:CE:42",(float)-87,null,270);
        NNObject n19 = new NNObject("BE:FB:E4:84:78:80",(float)-78,null,270);
        NNObject n20 = new NNObject("BE:FB:E4:84:72:FC",(float)-77,null,270);
        NNObject n21 = new NNObject("BE:FB:E4:84:78:81",(float)-73,null,270);
        NNObject n22 = new NNObject("B6:FB:E4:21:B2:B6",(float)-84,null,270);
        List<NNObject> res = new LinkedList<>();
        res.add(n1);
        res.add(n2);
        res.add(n3);
        res.add(n4);
        res.add(n5);
        res.add(n6);
        res.add(n7);
        res.add(n8);
        res.add(n9);
        res.add(n10);
        res.add(n11);
        res.add(n12);
        res.add(n13);
        res.add(n14);
        res.add(n15);
        res.add(n16);
      //  res.add(n17);
  //     res.add(n19);
  //      res.add(n20);
   //     res.add(n21);
//        res.add(n22);
        return res;
    }


    @Before
    public void createDB(){
        db = AppDatabase.getInstance();
        dao = db.nnObjectDao();
    }


    @Test
    public void locaterTest(){
        Double accuracy = 11.0;
        List<NNObject> search = new LinkedList<>();


        search = createData1();
        Node res1 = new Node("",58,19,3);
       Node test1 = new LocationControl().locate(search);
       assert(euclideanDistance(res1,test1) <= accuracy);


        search = DatabaseImporter.getTestData("Text_3.txt");
        Node res4 = new Node("",45,11,1);
        Node test4 = new LocationControl().locate(search);
        assert(euclideanDistance(res4,test4) <= accuracy);

        search = DatabaseImporter.getTestData("Text_4.txt");
        Node res5 = new Node("",46,30,1);
        Node test5 = new LocationControl().locate(search);
        assert(euclideanDistance(res5,test5) <= accuracy);

        search = DatabaseImporter.getTestData("Text.txt");
        Node res2 = new Node("",90,21,1);
        Node test2 = new LocationControl().locate(search);
        //assert(euclideanDistance(res2,test2) <= accuracy);

        search = DatabaseImporter.getTestData("Text_2.txt");
        Node res3 = new Node("",97,25,1);
        Node test3 = new LocationControl().locate(search);
       // assert(euclideanDistance(res3,test3) <= accuracy);

    }






    private double euclideanDistance(Node a, Node b){
        return Math.sqrt(Math.pow(a.getX() - b.getX(),2) + Math.pow(a.getY() - b.getY(), 2));
    }

}

