package no.smoky.magic.magicserver.model;

import com.google.cloud.Timestamp;

import lombok.Data;

@Data
public class Screen {
    private String key;
    private String name;
    private Timestamp refreshTime;
    private String users;

    // Constructors

    // public Screen(String name) {
    //     this(name, "");
    // }

    public Screen(String name, String key) {
        this.setName(name);
        this.setKey(key);
        this.users = "";
        this.refreshTime = Timestamp.now();
    }

    // Standard overrides

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Screen s = (Screen) o;
        return name.equals(s.name) &&
               key.equals(s.key) &&
               users.equals(s.users);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    public static String SEP = ", ";
    @Override
    public String toString() {
        return key + SEP + name + SEP + refreshTime + SEP + users;
    }

    // Getters and setters

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





}