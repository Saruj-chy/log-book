package com.agamilabs.logbook.Model;

import java.lang.reflect.Field;

public class SettingsModel {

    public String items;


    public SettingsModel() {
    }

    public Field[] getAllFields()
    {
        return this.getClass().getDeclaredFields();
    }

    public String getItems() {
        return items;
    }
}
