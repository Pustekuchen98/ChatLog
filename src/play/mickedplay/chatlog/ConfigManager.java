package play.mickedplay.chatlog;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by mickedplay on 10.05.2016 at 21:15 CEST.
 * You are not allowed to remove this comment.
 */
public class ConfigManager {

    private Chatlog chatlog;
    private FileConfiguration configFile;
    private ConfigSettings configSettings;

    public ConfigManager(Chatlog chatlog) {
        this.chatlog = chatlog;
        this.configFile = chatlog.getPlugin().getConfig();
        chatlog.getPlugin().getConfig().options().copyDefaults(true);
        chatlog.getPlugin().saveDefaultConfig();
        this.configSettings = new ConfigSettings(this);
    }

    public Chatlog getChatlog() {
        return chatlog;
    }

    public FileConfiguration getConfigFile() {
        return configFile;
    }

    public ConfigSettings getSettings() {
        return configSettings;
    }
}