package com.mypackage.henri.ruokaa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DailyMenuActivity extends AppCompatActivity {

    Intent i;
    ListView dailyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_menu);

        //vastaanotetaan intent ja sen tuoma string

        i = getIntent();
        dailyList = (ListView)findViewById(R.id.dailyMenuList);

        //tallennetaan intentistä tuotu data
        String desc = i.getStringExtra("Description");


        // sekavaa tekstin kaunistelua
        desc = desc.replace("<br>", "");

        String[] foods = desc.split("\\)");
        ArrayList<String> foodList = new ArrayList<>();
        for(String f : foods){
            String[] r = f.split("\\(");
            foodList.add(r[0]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, foodList);
        dailyList.setAdapter(adapter);

        //LISÄÄ RUOAN LEMPIRUOKIIN. JOKU SELKEÄMPI VAIHTOEHTO KUIN LONGCLICK?
        dailyList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String ruoka = dailyList.getItemAtPosition(i).toString();
                addToFavorites(ruoka);

                return false;
            }
        });



    }




    private void addToFavorites(String txt){

        String FILENAME = "favorites";
        String text = txt;

        //JOS TIEDOSTO ON JO OLEMASSA NIIN LISÄTÄÄN RUOKA TIEDOSTOON.
        //https://developer.android.com/reference/java/io/FileOutputStream.html
        //NÄKÖJÄÄN VOIS TEHDÄ FILEWRITERILLÄ KOSKA DATA ON PELKKÄÄ TEKSTIMUOTOA.

        if(fileExist(FILENAME)){
            try {
                FileOutputStream fileOutputStream = openFileOutput(FILENAME, Context.MODE_APPEND);

                fileOutputStream.write(text.getBytes());
                fileOutputStream.close();
                Toast.makeText(this, "Lisätty suosikkeihin", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            // JOS TIEDOSTOA EI OLE NIIN LUODAAN SE. MODE_PRIVATE LUO TIEDOSTON AINOASTAAN SOVELLUKSEN KÄYTTÖÖN.
            try {
                FileOutputStream fileOutputStream = openFileOutput(FILENAME, Context.MODE_PRIVATE);

                fileOutputStream.write(text.getBytes());
                fileOutputStream.close();
                Toast.makeText(this, "Lisätty suosikkeihin", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




    }

    //TARKISTETAAN ONKO TIEDOSTO OLEMASSA

    public boolean fileExist(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }


}
