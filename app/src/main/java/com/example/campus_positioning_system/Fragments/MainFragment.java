package com.example.campus_positioning_system.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.campus_positioning_system.Activitys.MainActivity;
import com.example.campus_positioning_system.LocationNavigation.WifiScanner;
import com.example.campus_positioning_system.Map.DrawingAssistant;
import com.example.campus_positioning_system.R;
import com.ortiz.touchview.TouchImageView;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static boolean onlyOnce = true;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static View rootViewSave;

    public static TouchImageView getMapView() {
        return rootViewSave.findViewById(R.id.map1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*
        Also... der grobe Plan ist erstmal hier alles zu starten. Also. Sensor Activity - WifiScanner
        Dann den Drawing Assistant damit zu füttern und ab geht die Party... Hoffentlich.
         */

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rootViewSave = rootView;

        if(onlyOnce) {
            TouchImageView mapView = rootView.findViewById(R.id.map1);
            TouchImageView dotView = rootView.findViewById(R.id.dot);

            TextView textView = rootView.findViewById(R.id.stockwerkView);

            WifiScanner.setStockwerkView(textView);

            WifiScanner wifiScanner = new WifiScanner(MainActivity.mainContext());
            new Thread(wifiScanner).start();

            DrawingAssistant drawingAssistant = new DrawingAssistant(dotView, mapView);
            drawingAssistant.start();

            onlyOnce = false;
        }
        // Inflate the layout for this fragment
        return rootView;
    }
}