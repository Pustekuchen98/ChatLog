package play.mickedplay.chatlog;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

/**
 * Created by Privat on 05.02.2016 at 22:20.
 */
public class ConfigSettings {

    private Chatlog chatlog;
    private FileConfiguration config;
    private boolean settingsValid;

    private String websiteURL;
    private String serverName;
    private int logKeyLength;
    private String language;
    private boolean isGamemodus;

    public ConfigSettings(Chatlog chatlog) {
        this.chatlog = chatlog;
        this.config = chatlog.getConfig();
        this.chatlog.getConfig().options().copyDefaults(true);
        this.chatlog.saveDefaultConfig();
        this.retrieveConfigSettings();
        this.checkValues();
    }

    public boolean isSettingsValid() {
        return settingsValid;
    }

    private void retrieveConfigSettings() {
        this.websiteURL = config.getString("websiteURL");
        this.serverName = config.getString("serverName");
        this.logKeyLength = config.getInt("logKeyLength");
        this.language = config.getString("language");
        this.isGamemodus = config.getBoolean("gamemodus");

        String configPrefix = Chatlog.getInstance().getConfig().getString("prefix");
        Chatlog.prefix = configPrefix;
        Chatlog.prefix = ChatColor.translateAlternateColorCodes('&', configPrefix);
    }

    private void checkValues() {
        boolean valid = true;
        ArrayList<String> invalidValues = new ArrayList<>();
        if (!websiteURL.endsWith("/")) {
            this.websiteURL += "/";
        }
        if (!(logKeyLength >= 6 && logKeyLength <= 255)) {
            valid = false;
            invalidValues.add("logKeyLength");
        }
        if (!valid) {
            for (String value : invalidValues) {
                System.err.println("Invalid config value given: " + value);
            }
        }
        invalidValues.clear();
        this.settingsValid = valid;
    }

    public boolean isGamemodus() {
        return isGamemodus;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public String getServerName() {
        return serverName;
    }

    public int getLogKeyLength() {
        return logKeyLength;
    }

    public String getLanguage() {
        return language;
    }
}