package play.mickedplay.chatlog;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import play.mickedplay.chatlog.database.ArchiveType;
import play.mickedplay.chatlog.lang.Language;
import play.mickedplay.chatlog.message.MessageType;
import play.mickedplay.chatlog.player.CommandChatlogExecutor;

/**
 * Created by mickedplay on 10.05.2016 at 21:12 CEST.
 * <p>
 * <p>
 * 2016 - Diese Version eines Chatlogs wurde von mickedplay geschrieben und ist urheberrechtlich durch geistiges Eigentum geschützt.
 * (Vielen Dank an SpigotMC! - https://www.spigotmc.org/)
 * Für weitere Informationen siehe https://www.spigotmc.org/resources/chatlog.18954/ oder http://mickedplay.de/
 * <p>
 * Du darfst diesen Kommentar nicht entfernen.
 * You are not allowed to remove this comment.
 */
public class CLPlugin extends JavaPlugin implements Listener {

    private Chatlog chatlog;

    public void onEnable() {
        this.chatlog = new Chatlog(this);
        if (!this.chatlog.isEnabled()) {
            getLogger().severe("No database connection available. Please check your connection details.");
            Bukkit.getPluginManager().disablePlugin(chatlog.getPlugin());
            return;
        }
        Bukkit.getPluginManager().registerEvents(this, this);
        new CommandChatlogExecutor(this.chatlog);
        new Language(this.chatlog.getConfigManager());
        Bukkit.getOnlinePlayers().forEach(player -> this.chatlog.getPlayerManager().addCLPlayer(player));
    }

    public void onDisable() {
        if (this.chatlog.getDatabaseManager().isConnectionAvailable()) {
            ArchiveType archiveType = this.chatlog.getSettings().getArchiveType();
            switch (archiveType) {
                case INSTANT:
                case ONSTOP:
                    this.chatlog.getDatabaseManager().finishChatlog(archiveType);
                    break;
                default:
            }
        }
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        this.chatlog.getPlayerManager().addCLPlayer(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void asyncPlayerChat(AsyncPlayerChatEvent e) {
        this.chatlog.getPlayerManager().getCLPlayer(e.getPlayer()).addChatMessage(e.getMessage(), !e.isCancelled() ? MessageType.SUCCESSFUL : MessageType.CANCELLED);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void commandPreprocess(PlayerCommandPreprocessEvent e) {
        this.chatlog.getPlayerManager().getCLPlayer(e.getPlayer()).addChatMessage(e.getMessage(), !e.isCancelled() ? MessageType.COMMAND : MessageType.CANCELLED);
    }
}