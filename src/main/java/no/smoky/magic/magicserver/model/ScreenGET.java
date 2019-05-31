package no.smoky.magic.magicserver.model;

import lombok.Data;

@Data
public class ScreenGET {
    private String name;
    private String key;

    public ScreenGET(String name, String key) {
        this.setName(name);
        this.setKey(key);
    }
}