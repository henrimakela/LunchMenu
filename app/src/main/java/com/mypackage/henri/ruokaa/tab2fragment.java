package com.mypackage.henri.ruokaa;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Henri on 20.9.2017.
 */

public class tab2fragment extends Fragment {
    private static final String TAG = "tab2fragment";

    ImageButton addFavorite;
    ArrayList<String> suosikkiRuoat;
    public static ListView suosikkiRuoatList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment, container, false);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFavorites();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        suosikkiRuoatList = (ListView)view.findViewById(R.id.list);

        loadFavorites();

        return view;
    }

    private String readFromFile(String filename){
        //https://developer.android.com/reference/java/io/FilterInputStream.html
        //TÄSSÄKIN TOIMISI FILEREADER KOSKA PELKKÄÄ TEKSTIÄ

        String ruuat;
        if(fileExist(filename)){
            //JOS TIEDOSTO ON OLEMASSA NIIN TEKSTI LUETAAN SIITÄ. TÄMÄ PITÄÄ OLLA KOSKA MUUTEN OHJELMA KAATUU JOS SUOSIKKEJA EI OLE LISÄTTY
            try {
                FileInputStream fileInputStream = getActivity().openFileInput(filename);
                //INPUTSTREAMREADER MUUTTAA BITIT TIEDOSTOSTA KIRJAIMIKSI
                InputStreamReader inputStr =  new InputStreamReader(fileInputStream);
                // BUFFEREDREADER LUKEE PUSKUROIMALLA KIRJAIMIA EDELTÄ JOTTA LUKEMINEN SUJUU SULAVASTI

                BufferedReader bf = new BufferedReader(inputStr);

                //STRINGBUFFER ON KUIN STRING MUTTA SIIHEN VOI SYÖTTÄÄ MITÄ TAHANSA DATATYYPPIÄ JA SE MUUTTAA SEN STRINGIKSI.
                //SE EI MYÖSKÄÄN OLISI TÄSSÄ VÄLTTÄMÄTTÄ TARPEELLINEN
                StringBuffer sb = new StringBuffer();

                while((ruuat=bf.readLine()) != null){
                    sb.append(ruuat + "\n");

                }

                return sb.toString();

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        // JOS TIEDOSTOA EI OLE NIIN PALAUTETAAN TYHJÄÄ
        return "";
    }

    private void loadFavorites(){


        suosikkiRuoat = new ArrayList<>();
        String foodz = readFromFile("favorites");
        String[] foodsR= foodz.split("([a-z])\\1+\\1+");
        for(String r : foodsR){

            suosikkiRuoat.add(r);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.list_item, suosikkiRuoat){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if(position % 2 == 1){
                    view.setBackgroundColor(Color.parseColor("#FFB6B546"));
                }
                else{
                    view.setBackgroundColor(Color.parseColor("#FFCCCB4C"));
                }

                return view;
            }
        };
        suosikkiRuoatList.setAdapter(adapter);

    }




    private boolean fileExist(String fname){
        File file = getActivity().getFileStreamPath(fname);
        return file.exists();
    }


}
