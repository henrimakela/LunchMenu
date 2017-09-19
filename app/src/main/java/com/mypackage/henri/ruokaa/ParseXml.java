package com.mypackage.henri.ruokaa;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Henri on 17.9.2017.
 */

public class ParseXml {

    private ArrayList<MenuOfTheDay> menus;


    public ParseXml(){
        this.menus = new ArrayList<>();

    }

    public ArrayList<MenuOfTheDay> getMenus() {
        return menus;
    }

    public boolean parse(String xmlData){
        boolean status = true;
        boolean inEntry = false;
        MenuOfTheDay currentMenu = null;

        String textValue = "";

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                String tagName = xpp.getName();

                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if("item".equalsIgnoreCase(tagName)){
                            inEntry = true;
                            currentMenu = new MenuOfTheDay();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if("item".equalsIgnoreCase(tagName)){
                                menus.add(currentMenu);
                                inEntry = false;
                            }
                            else if("title".equalsIgnoreCase(tagName)){
                                currentMenu.setTitle(textValue);
                            }
                            else if("description".equalsIgnoreCase(tagName)){
                                currentMenu.setDescription(textValue);
                            }
                        }
                        break;
                    default:
                        //nothing

                }
                eventType = xpp.next();

            }

        }
        catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        return status;
    }
}
