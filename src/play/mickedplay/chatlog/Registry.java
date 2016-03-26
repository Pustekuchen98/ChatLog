package play.mickedplay.chatlog;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import play.mickedplay.chatlog.event.PlayerListener;
import play.mickedplay.chatlog.log.CommandChatlog;
import play.mickedplay.chatlog.sql.MySQL;

import java.io.File;
import java.io.IOException;

/**
 * Created by Privat on 05.02.2016 at 21:35.
 */
public class Registry {

    private Chatlog chatlog;
    private ConfigSettings configSettings;
    private MySQL mysql;

    private File languageFile;
    private FileConfiguration languageConfig;

    public Registry(Chatlog chatlog) {
        this.chatlog = chatlog;
        this.configSettings = new ConfigSettings(this.chatlog);
        boolean disable = true;
        if (this.configSettings.isSettingsValid()) {
            this.languageFile = new File("plugins/Chatlog", this.configSettings.getLanguage() + ".yml");
            this.languageConfig = YamlConfiguration.loadConfiguration(this.languageFile);
            this.setupLanguageFile();

            this.mysql = new MySQL(this);
            if (mysql.isConnectionSuccessful()) {
                this.registerEvents();
                this.registerCommands();
                disable = false;
            }
        }
        if (disable) {
            Bukkit.getPluginManager().disablePlugin(Chatlog.getInstance());
        }
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this.chatlog);
    }

    private void registerCommands() {
        Chatlog chatlog = Chatlog.getInstance();
        chatlog.getCommand("chatlog").setExecutor(new CommandChatlog());
        chatlog.getCommand("chatlog").setUsage(Chatlog.prefix + "/chatlog <Name>");
    }

    public ConfigSettings getConfigSettings() {
        return configSettings;
    }

    public MySQL getMysql() {
        return mysql;
    }

    public FileConfiguration getLanguageConfig() {
        return languageConfig;
    }

    private void setupLanguageFile() {
        if (!this.languageFile.exists()) {
            switch (this.configSettings.getLanguage()) {
                case "de":
                    this.languageConfig.set(Language.REQ_CHATLOG_URL.name().toLowerCase(), "%PREFIX%Die angeforderte Chatlog-URL: §e%URL%");
                    this.languageConfig.set(Language.REQ_CHATLOG_AVAILABLE_SOON.name().toLowerCase(), "%PREFIX%Der Chatlog ist beim nächsten Serverstop unter folgender URL erreichbar: §e%URL%");
                    break;
                default:
                    this.languageConfig.set(Language.REQ_CHATLOG_URL.name().toLowerCase(), "%PREFIX%Your requested chatlog-url: §e%URL%");
                    this.languageConfig.set(Language.REQ_CHATLOG_AVAILABLE_SOON.name().toLowerCase(), "%PREFIX%The chatlog is available at the next server shutdown: §e%URL%");
                    break;
            }

            try {
                this.languageConfig.save(this.languageFile);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to save language file!");
            }
        }
    }
}