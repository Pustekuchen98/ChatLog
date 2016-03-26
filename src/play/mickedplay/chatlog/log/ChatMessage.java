package play.mickedplay.chatlog.log;

import play.mickedplay.chatlog.log.MessageType;

/**
 * Created by Privat on 04.02.2016 at 22:43.
 */
public class ChatMessage {

    private String message;
    private boolean wasCancelled;
    private MessageType messageType;
    private long timestamp;

    public ChatMessage(String message, boolean wasCancelled, MessageType messageType) {
        this.message = message;
        this.wasCancelled = wasCancelled;
        this.messageType = messageType;
        this.timestamp = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean wasCancelled() {
        return wasCancelled;
    }
}
