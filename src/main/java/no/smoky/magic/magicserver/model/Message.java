
package no.smoky.magic.magicserver.model;

import com.google.cloud.Timestamp;
import lombok.Data;

@Data
public class Message {
    private String message;
    private String sentBy;
    private String sentByEmail;
    private Timestamp sentTime;
    private long validMinutes;

    public Message() {
        
    }

    public Message(String message, String sentBy, String sentByEmail, Timestamp sentTime, long validMinutes) {
        this.message = message;
        this.sentBy = sentBy;
        this.sentByEmail = sentByEmail;
        this.sentTime = sentTime;
        this.validMinutes = validMinutes;
    }
}
