package play.mickedplay.chatlog;

import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.ChatColor;
import play.mickedplay.chatlog.database.ArchiveType;

/**
 * Created by mickedplay on 05.02.2016 at 22:20 CEST.
 * You are not allowed to remove this comment.
 */
public class ConfigSettings {

    private String hostname, database, username, password;
    private int port;
    private int parameterLength;
    private String baseUrl, serverName;

    private ArchiveType archiveType;

    public ConfigSettings(ConfigManager configManager) {
        this.hostname = configManager.getConfigFile().getString("MySQL.hostname");
        this.database = configManager.getConfigFile().getString("MySQL.database");
        this.username = configManager.getConfigFile().getString("MySQL.username");
        this.password = configManager.getConfigFile().getString("MySQL.password");
        this.port = configManager.getConfigFile().getInt("MySQL.port");

        this.parameterLength = configManager.getConfigFile().getInt("parameterLength");
        this.baseUrl = configManager.getConfigFile().getString("baseUrl");
        this.serverName = configManager.getConfigFile().getString("serverName");

        this.archiveType = ArchiveType.valueOf(configManager.getConfigFile().getString("archiveType"));

        configManager.getChatlog().setPrefix(StringEscapeUtils.unescapeJava(ChatColor.translateAlternateColorCodes('&', configManager.getConfigFile().getString("prefix"))));
        configManager.getChatlog().setLanguage(Language.valueOf(configManager.getConfigFile().getString("language")));
    }

    public String getHostname() {
        return hostname;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public int getParameterLength() {
        return parameterLength;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getServerName() {
        return serverName;
    }

    public ArchiveType getArchiveType() {
        return archiveType;
    }
}