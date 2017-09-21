package com.mypackage.henri.ruokaa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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



    }
}
