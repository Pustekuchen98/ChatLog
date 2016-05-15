package play.mickedplay.chatlog.player;

import play.mickedplay.chatlog.Chatlog;
import play.mickedplay.chatlog.database.ArchiveType;
import play.mickedplay.chatlog.message.ChatMessage;
import play.mickedplay.chatlog.message.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mickedplay on 13.05.2016 at 21:14 UTC+1.
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
     * Handles new ChatMessage objects
     */
    public void addChatMessage(String message, boolean cancelled, MessageType messageType) {
        ArchiveType archiveType = this.clPlayer.getChatlog().getSettings().getArchiveType();
        ChatMessage chatMessage = new ChatMessage(this.clPlayer.getUuid(), message, cancelled, messageType);
        if (archiveType == ArchiveType.INSTANT) {
            this.chatlog.getDatabaseManager().saveChatMessage(chatMessage);
        } else if (archiveType == ArchiveType.ONSTOP) {
            this.chatMessages.add(chatMessage);
        } else if (archiveType == ArchiveType.MERGED) {

        }
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }
}