package no.smoky.magic.magicserver.model;

import java.util.HashMap;
import java.util.Map;

import com.google.cloud.Timestamp;

import lombok.Data;

@Data
public class Screen {
    private String name;
    private Timestamp refreshTime;
    private Map<String, String> users;

    public Screen() {
    }

    public Screen(String name) {
        this.setName(name);
        this.refreshTime = Timestamp.now();
        this.users = new HashMap<String, String>();
    }

    public Screen(String name, Timestamp refreshTime, Map<String, String> users) {
        this.setName(name);
        this.setRefreshTime(refreshTime);
        this.setUsers(users);
    }

    public Screen(ScreenPOST rest) {
        this.setName(rest.getName());
        this.setUsers(rest.getUsers());
        this.setRefreshTime(Timestamp.now());
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
               users.equals(s.users);
    }
}