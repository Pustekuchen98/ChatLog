package play.mickedplay.chatlog.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import play.mickedplay.chatlog.Chatlog;
import play.mickedplay.chatlog.log.GamePlayer;
import play.mickedplay.chatlog.log.MessageType;

/**
 * Created by Privat on 23.02.2016 at 21:53.
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        Chatlog.getInstance().getMessageLog().addGamePlayer(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void asyncPlayerChat(AsyncPlayerChatEvent e) {
        GamePlayer gamePlayer = Chatlog.getInstance().getMessageLog().getGamePlayer(e.getPlayer());
        gamePlayer.addChatMessage(e.getMessage(), e.isCancelled(), e.isCancelled() ? MessageType.CANCELLED : MessageType.SUCCESSFUL);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void commandPreprocess(PlayerCommandPreprocessEvent e) {
        GamePlayer gamePlayer = Chatlog.getInstance().getMessageLog().getGamePlayer(e.getPlayer());
        gamePlayer.addChatMessage(e.getMessage(), e.isCancelled(), e.isCancelled() ? MessageType.CANCELLED : MessageType.COMMAND);
    }
}