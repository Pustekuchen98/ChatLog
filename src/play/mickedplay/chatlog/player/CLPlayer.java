package play.mickedplay.chatlog.player;

import org.bukkit.entity.Player;
import play.mickedplay.chatlog.Chatlog;
import play.mickedplay.chatlog.message.MessageType;

import java.util.UUID;

/**
 * Created by mickedplay on 10.05.2016 at 21:30 UTC+1.
 */
public class CLPlayer {

    private Chatlog chatlog;
    private Player player;
    private MessageHandler messageHandler;

    public CLPlayer(Chatlog chatlog, Player player) {
        this.chatlog = chatlog;
        this.player = player;
        this.messageHandler = new MessageHandler(this);
    }

    public Chatlog getChatlog() {
        return chatlog;
    }

    public void addChatMessage(String message, boolean cancelled, MessageType messageType) {
        this.messageHandler.addChatMessage(message, cancelled, messageType);
    }

    public UUID getUuid() {
        return player.getUniqueId();
    }

    public void sendMessage(String message) {
        this.player.sendMessage(message);
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }
}
