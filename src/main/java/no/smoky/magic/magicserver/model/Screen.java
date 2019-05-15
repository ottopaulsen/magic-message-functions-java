package no.smoky.magic.magicserver.model;

import com.google.cloud.Timestamp;

public class Screen {
    private String name;
    private String key;
    private Timestamp refreshTime;

    public Screen() {
    }

    /**
     * @return the refreshTime
     */
    public Timestamp getRefreshTime() {
        return refreshTime;
    }

    /**
     * @param refreshTime the refreshTime to set
     */
    public void setRefreshTime(Timestamp refreshTime) {
        this.refreshTime = refreshTime;
    }

    public Screen(String name, String key) {
        this.name = name;
        this.key = key;
    }

    /**
     * @return the key
     */ 
    public String getKey() {
        return key;
    }    

    /**
     * @param key the key to set
     */ 
    public void setKey(String key) {
        this.key = key;
    }    

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}