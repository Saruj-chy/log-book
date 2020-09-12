package com.agamilabs.logbook.Model;

import java.lang.reflect.Field;

public class Main_Model {
   /* String recentActivity;
    String clients;
    String projects;

    public Main_Model() {
    }

    public Main_Model(String recentActivity, String clients, String projects) {
        this.recentActivity = recentActivity;
        this.clients = clients;
        this.projects = projects;
    }

    public String getRecentActivity() {
        return recentActivity;
    }

    public String getClients() {
        return clients;
    }

    public String getProjects() {
        return projects;
    }

    public Field[] getAllFields()
    {
        return this.getClass().getDeclaredFields();
    }*/

   String group, group_id;
   String item, item_id;

    public Main_Model() {
    }

    public Main_Model(String itemId, String group_id, String group, String item) {
        this.group = group;
        this.item = item;
        this.item_id = itemId;
        this.group_id = group_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public String getItem_id() {
        return item_id;
    }
    public String getItem() {
        return item;
    }

    public String getGroup() {
        return group;
    }

    public Field[] getAllFields()
    {
        return this.getClass().getDeclaredFields();
    }
}
