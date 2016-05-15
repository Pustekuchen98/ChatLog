package play.mickedplay.chatlog.message;

import play.mickedplay.chatlog.Helper;

import java.util.UUID;

/**
 * Created by mickedplay on 13.05.2016 at 21:41 UTC+1.
 */
public class ChatMessage {

    private UUID uuid;
    private String message;
    private boolean cancelled;
    private MessageType messageType;
    private String time;

    public ChatMessage(UUID uuid, String message, boolean cancelled, MessageType messageType) {
        this.uuid = uuid;
        this.message = message;
        this.cancelled = cancelled;
        this.messageType = messageType;
        this.time = Helper.getCurrentDateTime();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getTime() {
        return time;
    }
}