package play.mickedplay.chatlog.message;

import play.mickedplay.chatlog.Chatlog;
import play.mickedplay.chatlog.Utilities;

import java.util.UUID;

/**
 * Created by mickedplay on 13.05.2016 at 21:41 CEST.
 * You are not allowed to remove this comment.
 */
public class ChatMessage {

    private UUID uuid;
    private String message;
    private boolean hidden;
    private MessageType messageType;
    private String time;

    public ChatMessage(Chatlog chatlog, UUID uuid, String message, MessageType messageType) {
        this.uuid = uuid;
        this.message = message;
        this.messageType = messageType;
        this.hidden = chatlog.getSettings().getBlacklist().contains(uuid.toString());
        this.time = Utilities.getCurrentDateTime();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTime() {
        return time;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getMessage() {
        return message;
    }

    public boolean isHidden() {
        return hidden;
    }
}