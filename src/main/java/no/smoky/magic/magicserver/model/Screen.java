package no.smoky.magic.magicserver.model;

import com.google.cloud.Timestamp;

import lombok.Data;

@Data
public class Screen {
    private String name;
    private String key;
    private Timestamp refreshTime;
    private String users;

    public Screen() {
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public Timestamp getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Timestamp refreshTime) {
        this.refreshTime = refreshTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Screen(String name, String key) {
        this.setName(name);
        this.setKey(key);
    }


}