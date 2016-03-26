package play.mickedplay.chatlog.log;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import play.mickedplay.chatlog.Chatlog;
import play.mickedplay.chatlog.Language;

/**
 * Created by Privat on 05.02.2016 at 23:10.
 */
public class CommandChatlog implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("chatlog")) {
            if (args.length >= 0) {
                player.sendMessage(Chatlog.getMessage(Language.REQ_CHATLOG_AVAILABLE_SOON).replace("%URL%", Chatlog.getInstance().getMessageLog().getURL()));
                GamePlayer gamePlayer = Chatlog.getInstance().getMessageLog().getGamePlayer(player);
                gamePlayer.setHasRequestedURL(true);
                return true;
            }
        }
        return false;
    }
}