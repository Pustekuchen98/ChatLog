package play.mickedplay.chatlog.player;

import org.bukkit.entity.Player;
import play.mickedplay.chatlog.Chatlog;

import java.util.HashMap;

/**
 * Created by mickedplay on 10.05.2016 at 21:28 CEST.
 * You are not allowed to remove this comment.
 */
public class PlayerManager {

    private Chatlog chatlog;
    private HashMap<Player, CLPlayer> clPlayers;

    public PlayerManager(Chatlog chatlog) {
        this.chatlog = chatlog;
        this.clPlayers = new HashMap<>();
    }

    /**
     * Registeres new CLPlayer object
     */
    public void addCLPlayer(Player player) {
        if (!this.clPlayers.containsKey(player)) {
            this.clPlayers.put(player, new CLPlayer(this.chatlog, player));
        }
    }

    public CLPlayer getCLPlayer(Player player) {
        return clPlayers.get(player);
    }

    public HashMap<Player, CLPlayer> getCLPlayers() {
        return clPlayers;
    }
}
