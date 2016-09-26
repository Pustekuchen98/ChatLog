package play.mickedplay.chatlog;

import play.mickedplay.chatlog.database.DatabaseManager;
import play.mickedplay.chatlog.exception.ExceptionManager;
import play.mickedplay.chatlog.player.PlayerManager;

/**
 * Created by mickedplay on 10.05.2016 at 21:14 CEST.
 * You are not allowed to remove this comment.
 */
public class Chatlog {

    private CLPlugin clPlugin;
    private ExceptionManager exceptionManager;
    private ConfigManager configManager;
    private PlayerManager playerManager;
    private DatabaseManager databaseManager;

    private String prefix;
    private Language language;

    private boolean enabled = true;

    public Chatlog(CLPlugin clPlugin) {
        this.clPlugin = clPlugin;
        this.exceptionManager = new ExceptionManager(this);
        this.configManager = new ConfigManager(this);
        this.playerManager = new PlayerManager(this);
        this.databaseManager = new DatabaseManager(this);
    }

    public CLPlugin getPlugin() {
        return clPlugin;
    }

    public ExceptionManager getExceptionManager() {
        return exceptionManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public ConfigSettings getSettings() {
        return configManager.getSettings();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}