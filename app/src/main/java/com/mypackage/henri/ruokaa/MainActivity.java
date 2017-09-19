package com.mypackage.henri.ruokaa;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ListView menuList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuList = (ListView)findViewById(R.id.ruokalistaView);



        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://www.amica.fi/modules/MenuRss/MenuRss/CurrentWeek?costNumber=0217&language=fi");


    }







    private class DownloadData extends AsyncTask<String, Void, String>{
        private static final String TAG = "DownloadData";

        @Override
        protected String doInBackground(String... strings) {

            String rssFeed = downloadXML(strings[0]);
            return rssFeed;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            ParseXml xmlParser = new ParseXml();
            xmlParser.parse(s);

            ArrayAdapter<MenuOfTheDay> adapter = new ArrayAdapter<MenuOfTheDay>(MainActivity.this, R.layout.list_item, xmlParser.getMenus());
            menuList.setAdapter(adapter);






        }

        private String downloadXML(String urlPath){
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
}
