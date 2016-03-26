package play.mickedplay.chatlog;

import org.bukkit.entity.Player;
import play.mickedplay.chatlog.log.ChatMessage;
import play.mickedplay.chatlog.log.GamePlayer;
import play.mickedplay.chatlog.sql.MySQL;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Privat on 05.02.2016 at 23:33.
 */
public class MessageLog {

    private String logKey;
    private long timestamp;
    private Map<Player, GamePlayer> gamePlayers;

    public MessageLog(String logKey) {
        this.logKey = logKey;
        this.timestamp = System.currentTimeMillis();
        this.gamePlayers = new HashMap<>();
    }

    /*
        Stores all messages to database
     */
    public void save() {
        boolean noMessage = true;
        for (Player player : gamePlayers.keySet()) {
            if (gamePlayers.get(player).getChatMessages().size() > 0) {
                noMessage = false;
                break;
            }
        }
        if (!noMessage) {
            MySQL mySQL = Chatlog.getRegistry().getMysql();
            String logTable = mySQL.getLogTablePrefix() + this.logKey;
            String servername = Chatlog.getRegistry().getConfigSettings().getServerName();
            mySQL.update("INSERT INTO " + mySQL.getMainTableName() + "(log_key,log_table,server_name,server_start,server_stop)VALUES('" + logKey + "','" + logTable + "','" + servername + "'," + this.timestamp + "," + System.currentTimeMillis() + ");");

            mySQL.update("CREATE TABLE IF NOT EXISTS " + logTable + "(id INTEGER UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY, " +
                    "message VARCHAR(100),user_uuid VARCHAR(36),type VARCHAR(32),datetime BIGINT(13))");

            String logQuery = "INSERT INTO " + logTable + "(message,user_uuid,type,datetime)VALUES";
            for (Player player : gamePlayers.keySet()) {
                GamePlayer gamePlayer = gamePlayers.get(player);
                for (ChatMessage chatMessage : gamePlayer.getChatMessages()) {
                    logQuery += "('" + chatMessage.getMessage() + "','";
                    logQuery += player.getUniqueId().toString() + "','";
                    logQuery += chatMessage.getMessageType() + "',";
                    logQuery += chatMessage.getTimestamp() + "),";
                }
            }
            logQuery = logQuery.substring(0, logQuery.length() - 1) + ";";
            mySQL.update(logQuery);
        }
    }

    /*
        Returns the final chatlog url
     */
    public String getURL() {
        String url = Chatlog.getRegistry().getConfigSettings().getWebsiteURL();
        return url.endsWith("?log=") ? url + this.logKey : url + "?log=" + this.logKey;
    }

    public void addGamePlayer(Player player) {
        this.gamePlayers.put(player, new GamePlayer());
    }

    public GamePlayer getGamePlayer(Player player) {
        return this.gamePlayers.get(player);
    }
}