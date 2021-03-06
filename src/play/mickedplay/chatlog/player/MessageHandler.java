package play.mickedplay.chatlog.player;

import play.mickedplay.chatlog.Chatlog;
import play.mickedplay.chatlog.database.ArchiveType;
import play.mickedplay.chatlog.message.ChatMessage;
import play.mickedplay.chatlog.message.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mickedplay on 13.05.2016 at 21:14 CEST.
 * You are not allowed to remove this comment.
 */
public class MessageHandler {

    private Chatlog chatlog;
    private CLPlayer clPlayer;
    private List<ChatMessage> chatMessages;

    public MessageHandler(CLPlayer clPlayer) {
        this.chatlog = clPlayer.getChatlog();
        this.clPlayer = clPlayer;
        this.chatMessages = new ArrayList<>();
    }

    /**
     * Creates and handles a new ChatMessage object
     *
     * @param message     The message (written by a player)
     * @param messageType The message type
     */
    public void addChatMessage(String message, MessageType messageType) {
        message = message.replace("'", "\\'");
        ArchiveType archiveType = this.clPlayer.getChatlog().getSettings().getArchiveType();
        ChatMessage chatMessage = new ChatMessage(this.chatlog, this.clPlayer.getUuid(), message, messageType);
        switch (archiveType) {
            case INSTANT:
                this.chatlog.getDatabaseManager().saveChatMessage(chatMessage);
                break;
            case ONSTOP:
                this.chatMessages.add(chatMessage);
                break;
        }
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }
}