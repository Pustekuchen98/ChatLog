package play.mickedplay.chatlog.log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Privat on 04.02.2016 at 22:36.
 */
public class GamePlayer {

    private List<ChatMessage> chatMessages;
    private boolean hasRequested;

    public GamePlayer() {
        this.chatMessages = new ArrayList();
        this.hasRequested = false;
    }

    public void addChatMessage(String message, boolean wasCancelled, MessageType messageType) {
        this.chatMessages.add(new ChatMessage(message, wasCancelled, messageType));
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setHasRequestedURL(boolean hasRequested) {
        this.hasRequested = hasRequested;
    }

    public boolean hasRequestedURL() {
        return this.hasRequested;
    }
}
