package com.agamilabs.logbook.Model;

import java.lang.reflect.Field;

public class SettingsModels {

    public String section_id;
    public String section_title;
    public String section_identifier ;

    public Field[] getAllFields(){
        return this.getClass().getDeclaredFields() ;
    }

    public SettingsModels() {
    }

    public SettingsModels(String section_id, String section_title, String section_identifier) {
        this.section_id = section_id;
        this.section_title = section_title;
        this.section_identifier = section_identifier;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getSection_title() {
        return section_title;
    }

    public void setSection_title(String section_title) {
        this.section_title = section_title;
    }

    public String getSection_identifier() {
        return section_identifier;
    }

    public void setSection_identifier(String section_identifier) {
        this.section_identifier = section_identifier;
    }

    @Override
    public String toString() {
        return "SettingModels{" +
                "section_id='" + section_id + '\'' +
                ", section_title='" + section_title + '\'' +
                ", section_identifier='" + section_identifier + '\'' +
                '}';
    }
}
