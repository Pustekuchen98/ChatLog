package play.mickedplay.chatlog.lang;

import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.ChatColor;
import play.mickedplay.chatlog.Chatlog;
import play.mickedplay.chatlog.ConfigManager;

/**
 * Created by mickedplay on 20.11.2016 at 13:52 CEST.
 * You are not allowed to remove this comment.
 */
public class Language {

    public static String chatlogCommand;
    private ConfigManager configManager;
    private Chatlog chatlog;

    public Language(ConfigManager configManager) {
        this.configManager = configManager;
        this.chatlog = configManager.getChatlog();
        this.getAndModifyLanguageStrings();
    }

    private void getAndModifyLanguageStrings() {
        String langPath = "lang." + this.configManager.getSettings().getLangType() + ".";

        chatlogCommand = this.configManager.getChatlog().getPlugin().getConfig().getString(langPath + "chatlogCommand");
        chatlogCommand = chatlogCommand.replace("%PREFIX%", this.chatlog.getPrefix());
        chatlogCommand = chatlogCommand.replace("%BASE_URL%", this.configManager.getSettings().getBaseUrl());
        chatlogCommand = chatlogCommand.replace("%LOG_KEY%", this.chatlog.getDatabaseManager().getReference());
        chatlogCommand = StringEscapeUtils.unescapeJava(ChatColor.translateAlternateColorCodes('&', chatlogCommand));
    }
}