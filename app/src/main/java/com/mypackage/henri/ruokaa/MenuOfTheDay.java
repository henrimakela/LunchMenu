package com.mypackage.henri.ruokaa;

import java.util.ArrayList;

/**
 * Created by Henri on 17.9.2017.
 */

public class MenuOfTheDay {


    // xml tiedostosta valitut Tagit, jotka halutaan tallentaa objektiin. Kaikkia tageja ei tarvita, vaikka tiedostossa olisi muitakin
    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){

        String desc = this.description.replace("<br>", "");

        return this.title + "\n" + desc;
    }

}
