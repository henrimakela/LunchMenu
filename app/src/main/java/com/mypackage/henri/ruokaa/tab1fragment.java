package com.mypackage.henri.ruokaa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);


        menuList = (ListView)view.findViewById(R.id.ruokalistaView);
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://www.amica.fi/modules/MenuRss/MenuRss/CurrentWeek?costNumber=0217&language=fi");
        return view;
    }

    private class DownloadData extends AsyncTask<String, Void, String> {
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

            ArrayAdapter<MenuOfTheDay> adapter = new ArrayAdapter<MenuOfTheDay>(getView().getContext(), R.layout.list_item, xmlParser.getMenus());
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
