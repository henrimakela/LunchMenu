package com.mypackage.henri.ruokaa;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Henri on 20.9.2017.
 */

public class tab1fragment extends Fragment{

    private static final String TAG = "tab1fragment";
    ListView menuList;
    Intent dailyMenuIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);


        menuList = (ListView)view.findViewById(R.id.ruokalistaView);
        dailyMenuIntent = new Intent(getContext(), DailyMenuActivity.class);

        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://www.amica.fi/modules/MenuRss/MenuRss/CurrentWeek?costNumber=0217&language=fi");


        //Päivän menun tarkastelu. Avaa uuden activityn ja lähettää tiedot intentillä

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MenuOfTheDay m = (MenuOfTheDay)menuList.getItemAtPosition(i);
                String desc = m.getDescription();

                dailyMenuIntent.putExtra("Description", desc);
                startActivity(dailyMenuIntent);
            }
        });


        //Menun jakaminen
        menuList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String text = menuList.getItemAtPosition(i).toString();

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));

                return false;
            }
        });



        return view;
    }

    //Lataa xml tiedoston sivulta AsyncTaskilla, jotta lataus ei kuormita käytettävyyttä.
    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadData";

        @Override
        protected String doInBackground(String... strings) {

            // parametrina oleva string on ladattava data sivulta. String.. tarkoittaa että stringeja voi olla useampi.
            // esim kahdesta urlista ladatut tiedot
            String rssFeed = downloadXML(strings[0]);
            return rssFeed;  //==> lähettää metodiin onPostExecute parametriksi
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            ParseXml xmlParser = new ParseXml();
            xmlParser.parse(s);// muutetaan parametrissä lähetetty xml objektiksi ja lisätään Arraylistiin jossa on kaikki menut

            // adapteri jossa lisätään list_viewiin kaikki MenuOfTheDay objektit getMenus()-metodilla
            ArrayAdapter<MenuOfTheDay> adapter = new ArrayAdapter<MenuOfTheDay>(getView().getContext(), R.layout.list_item, xmlParser.getMenus());
            menuList.setAdapter(adapter);


        }

        private String downloadXML(String urlPath){

            //ladataan xml sivulta. Metodi joka lataa tiedot, mutta käytetään DownloadData-luokan sisällä, jotta siitä saadaan AsyncTask
            StringBuilder xmlResult = new StringBuilder();

            try{
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();


                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charsRead;

                char[] inputBuffer = new char[500];

                while(true){
                    charsRead = reader.read(inputBuffer);

                    if(charsRead < 0){
                        break;
                    }
                    if(charsRead > 0){
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }
                reader.close();

                return xmlResult.toString();

            }


            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }


    }
    //EI TOIMI VIELÄ
    private void checkForFaves(){

        for(int i = 0; i < tab2fragment.suosikkiRuoatList.getCount(); i++){
            String suosikki = tab2fragment.suosikkiRuoatList.getItemAtPosition(i).toString();
            for(int j = 0; j < menuList.getCount(); j++){
                if(menuList.getItemAtPosition(j).toString().contains(suosikki)){
                    menuList.getChildAt(j).setBackgroundColor(getResources().getColor(R.color.favorite));
                }
            }
        }


    }
}
