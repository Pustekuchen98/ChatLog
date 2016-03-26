package play.mickedplay.chatlog;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

/**
 * Created by Privat on 03.02.2016 at 22:23.
 */
public class Chatlog extends JavaPlugin {

    public static String prefix;
    private static Chatlog instance;
    private static Registry registry;
    private MessageLog messageLog;

    public static Chatlog getInstance() {
        return instance;
    }

    public static Registry getRegistry() {
        return registry;
    }

    public static String getMessage(Language language) {
        return Chatlog.getRegistry().getLanguageConfig().getString(language.name().toLowerCase()).replace("%PREFIX%", prefix);
    }

    public void onEnable() {
        this.instance = this;
        this.registry = new Registry(this);
        this.startLogService();
        for (Player all : Bukkit.getOnlinePlayers()) {
            messageLog.addGamePlayer(all);
        }
    }

    public void onDisable() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (Chatlog.getInstance().getMessageLog().getGamePlayer(all).hasRequestedURL()) {
                all.sendMessage(getMessage(Language.REQ_CHATLOG_URL).replace("%URL%", this.messageLog.getURL()));
            }
        }
        messageLog.save();
    }

    public MessageLog getMessageLog() {
        return this.messageLog;
    }

    private void startLogService() {
        this.messageLog = new MessageLog(this.getRandomUnusedLogKey());
    }

    private String getRandomUnusedLogKey() {
        char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        int logKeyLength = Chatlog.getRegistry().getConfigSettings().getLogKeyLength();
        String logKey = "";
        for (int i = 0; i < logKeyLength; i++) {
            logKey += chars[new Random().nextInt(chars.length)];
        }
        if (this.registry.getMysql().existsLog(logKey)) {
            return getRandomUnusedLogKey();
        }
        return logKey.toLowerCase();
    }
}