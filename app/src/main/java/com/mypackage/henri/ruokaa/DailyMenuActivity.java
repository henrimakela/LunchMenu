package com.mypackage.henri.ruokaa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //vastaanotetaan intent ja sen tuoma string

        i = getIntent();
        dailyList = (ListView)findViewById(R.id.dailyMenuList);

        //tallennetaan intentistä tuotu data
        String desc = i.getStringExtra("Description");


        //tekstin kaunistelua
        desc = desc.replace("<br>", "");
        desc = desc.replace("PÄIVÄNSALAATTI:", "");
        desc = desc.replaceAll("(?m)^\\s", "xxx");
        String[] foods = desc.split("([a-z])\\1+\\1+");

        ArrayList<String> foodList = new ArrayList<>();

        for(String f : foods){
            foodList.add(f);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, foodList){


            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view =  super.getView(position, convertView, parent);

                if(position % 2 == 1){
                    view.setBackgroundColor(Color.parseColor("#FFB6B546"));
                }
                else{
                    view.setBackgroundColor(Color.parseColor("#FFCCCB4C"));
                }

                return view;
            }
        };

        dailyList.setAdapter(adapter);

        //LISÄÄ RUOAN LEMPIRUOKIIN.
        dailyList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String ruoka = dailyList.getItemAtPosition(i).toString();

                // Poistetaan turhia toistuvia tietoja tekstistä tallennettaessa ja lisätään kirjaimia xxx helpottamaan ruokien erottamista lukuvaiheessa.

                String[] ruokaR = ruoka.split(":");
                addToFavorites(ruokaR[1] + "xxx");

                return false;
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }


    private void addToFavorites(String txt){

        String FILENAME = "favorites";
        String text = txt;

        //JOS TIEDOSTO ON JO OLEMASSA NIIN LISÄTÄÄN RUOKA TIEDOSTOON.
        //https://developer.android.com/reference/java/io/FileOutputStream.html


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
