package play.mickedplay.chatlog;

import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.ChatColor;
import play.mickedplay.chatlog.database.ArchiveType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mickedplay on 05.02.2016 at 22:20 CEST.
 * You are not allowed to remove this comment.
 */
public class ConfigSettings {

    private String hostname, database, username, password;
    private int port;
    private int parameterLength;
    private String baseUrl, server;
    private List<String> blacklist;

    private ArchiveType archiveType;
    private String langType;

    public ConfigSettings(ConfigManager configManager) {
        this.hostname = configManager.getConfigFile().getString("MySQL.hostname");
        this.database = configManager.getConfigFile().getString("MySQL.database");
        this.username = configManager.getConfigFile().getString("MySQL.username");
        this.password = configManager.getConfigFile().getString("MySQL.password");
        this.port = configManager.getConfigFile().getInt("MySQL.port");

        this.parameterLength = configManager.getConfigFile().getInt("parameterLength");
        this.baseUrl = configManager.getConfigFile().getString("baseUrl");
        this.server = configManager.getConfigFile().getString("server");

        this.archiveType = ArchiveType.valueOf(configManager.getConfigFile().getString("archiveType"));
        this.blacklist = new ArrayList<>(Arrays.asList(configManager.getConfigFile().getString("blacklist").split(";")));

        this.langType = configManager.getConfigFile().getString("language");

        configManager.getChatlog().setPrefix(StringEscapeUtils.unescapeJava(ChatColor.translateAlternateColorCodes('&', configManager.getConfigFile().getString("prefix"))));
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

    public String getServer() {
        return server;
    }

    public ArchiveType getArchiveType() {
        return archiveType;
    }

    public String getLangType() {
        return langType;
    }

    public List<String> getBlacklist() {
        return blacklist;
    }
}