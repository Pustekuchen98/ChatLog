package play.mickedplay.chatlog.database;

import play.mickedplay.chatlog.Chatlog;
import play.mickedplay.chatlog.ConfigManager;
import play.mickedplay.chatlog.Utilities;
import play.mickedplay.chatlog.exception.ErrorType;
import play.mickedplay.chatlog.message.ChatMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mickedplay on 10.05.2016 at 21:43 CEST.
 * You are not allowed to remove this comment.
 */
public class DatabaseManager {

    private Chatlog chatlog;
    private MySQLManager mySQLManager;
    private ConfigManager configManager;

    private String reference, server, start;

    public DatabaseManager(Chatlog chatlog) {
        this.chatlog = chatlog;
        this.configManager = chatlog.getConfigManager();
        this.mySQLManager = new MySQLManager(this);
        if (mySQLManager.isConnectionAvailable()) this.prepare();
        else chatlog.setEnabled(false);
    }

    public Chatlog getChatlog() {
        return chatlog;
    }

    public boolean isConnectionAvailable() {
        return mySQLManager.isConnectionAvailable();
    }

    public String getReference() {
        return reference;
    }

    public void prepare() {
        this.reference = Utilities.generateUrlKey(this, this.configManager.getSettings().getParameterLength());
        this.server = this.configManager.getSettings().getServer();
        this.start = Utilities.getCurrentDateTime();
        this.mySQLManager.update("INSERT INTO _storage(reference,server,completed,start)VALUES('" + this.reference + "','" + this.server + "',false,'" + this.start + "')");
        this.mySQLManager.update("CREATE TABLE IF NOT EXISTS " + this.reference + "(id INTEGER UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,uuid VARCHAR(36),tstamp BIGINT(13),category VARCHAR(32),message VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,hidden BOOLEAN)");
    }

    /**
     * Checks if a reference already exists
     */
    public boolean checkForExistingUrlKey(String urlKey) {
        try {
            ResultSet resultSet = this.mySQLManager.query("SELECT reference FROM _storage WHERE reference='" + urlKey + "'");
            return resultSet.next() && resultSet.getString("reference") != null;
        } catch (SQLException sqlException) {
            this.chatlog.getExceptionManager().create(sqlException, ErrorType.DATABASE_FETCH_RESULT);
        }
        return false;
    }

    /**
     * Checks if the current chatlog table is empty or not
     */
    public void finishChatlog(ArchiveType archiveType) {
        switch (archiveType) {
            case INSTANT:
                if (this.checkForEmptyTable()) this.removeTableData();
                break;
            case ONSTOP:
                this.chatlog.getPlayerManager().getCLPlayers().values().forEach(clPlayer -> clPlayer.getMessageHandler().getChatMessages().forEach(this::saveChatMessage));
                if (this.checkForEmptyTable()) this.removeTableData();
                break;
            case MERGED:
                break;
            default:
        }
        this.mySQLManager.update("UPDATE _storage SET completed=true,stop='" + Utilities.getCurrentDateTime() + "' WHERE reference='" + this.reference + "'");
    }

    /**
     * Removes empty table informations
     */
    private void removeTableData() {
        this.mySQLManager.update("DROP TABLE " + this.reference);
        this.mySQLManager.update("DELETE FROM _storage WHERE reference='" + this.reference + "'");
    }

    /**
     * Stores a given chatmessage object to database
     */
    public void saveChatMessage(ChatMessage chatMessage) {
        this.mySQLManager.update("INSERT INTO " + this.reference + "(uuid,tstamp,category,message,hidden)VALUES('" + chatMessage.getUuid().toString() + "','" + chatMessage.getTime() + "','" + chatMessage.getMessageType().toString() + "','" + chatMessage.getMessage() + "'," + chatMessage.isHidden() + ")");
    }

    /**
     * Checks if the current chatlog table is empty or not
     */
    private boolean checkForEmptyTable() {
        try {
            while (this.mySQLManager.query("SELECT * FROM " + this.reference).next()) {
                return false;
            }
        } catch (SQLException sqlException) {
            this.chatlog.getExceptionManager().create(sqlException, ErrorType.DATABASE_FETCH_RESULT);
        }
        return true;
    }
}