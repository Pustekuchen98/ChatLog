package play.mickedplay.chatlog.player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import play.mickedplay.chatlog.Chatlog;

/**
 * Created by mickedplay on 15.05.2016 at 13:40 CEST.
 * You are not allowed to remove this comment.
 */
public class CommandChatlogExecutor implements CommandExecutor {

    private Chatlog chatlog;

    public CommandChatlogExecutor(Chatlog chatlog) {
        this.chatlog = chatlog;
        chatlog.getPlugin().getCommand("chatlog").setExecutor(this);
    }

    /**
     * Executes a command
     *
     * @param commandSender The command sender
     * @param command       The command
     * @param label         The command name
     * @param args          Command arguments
     * @return Returns true, if a command was executed successfully
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("chatlog")) {
            if (args.length >= 0) {
                commandSender.sendMessage(this.chatlog.getPrefix() + "Chatlog url: " + this.chatlog.getSettings().getBaseUrl() + "?log=" + this.chatlog.getDatabaseManager().getReference());
                return true;
            }
        }
        return false;
    }
}