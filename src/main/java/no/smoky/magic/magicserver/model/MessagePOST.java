
package no.smoky.magic.magicserver.model;

import lombok.Data;

@Data
public class MessagePOST {
    private String message;
    private long lifetime;
}
