package no.smoky.magic.magicserver.model;

import java.util.Map;

import lombok.Data;

@Data
public class ScreenPOST {
    private String secret;
    private String name;
    private Map<String, String> users;

    public ScreenPOST(String name, String secret, Map<String, String> users) {
        this.setName(name);
        this.setSecret(secret);
        this.setUsers(users);
    }

}