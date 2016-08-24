package play.mickedplay.chatlog.database;

import play.mickedplay.chatlog.Chatlog;
import play.mickedplay.chatlog.ConfigManager;
import play.mickedplay.chatlog.Helper;
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

    private String urlKey, serverName, serverStart;

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

    public String getUrlKey() {
        return urlKey;
    }

    public void prepare() {
        this.urlKey = Helper.generateUrlKey(this, this.configManager.getSettings().getParameterLength());
        this.serverName = this.configManager.getSettings().getServerName();
        this.serverStart = Helper.getCurrentDateTime();
        this.mySQLManager.update("INSERT INTO cl_content (urlKey,serverName,logComplete,serverStart)VALUES('" + this.urlKey + "','" + this.serverName + "',false,'" + this.serverStart + "')");
        this.mySQLManager.update("CREATE TABLE IF NOT EXISTS log_" + this.urlKey + "(id INTEGER UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,message VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,uuid VARCHAR(36),type VARCHAR(32),time BIGINT(13))");
    }

    /**
     * Checks if a urlKey already exists
     */
    public boolean checkForExistingUrlKey(String urlKey) {
        try {
            ResultSet resultSet = this.mySQLManager.query("SELECT urlKey FROM cl_content WHERE urlKey='" + urlKey + "'");
            return resultSet.next() && resultSet.getString("urlKey") != null;
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
        this.mySQLManager.update("UPDATE cl_content SET logComplete = true, serverStop = '" + Helper.getCurrentDateTime() + "' WHERE urlKey = '" + this.urlKey + "'");
    }

    /**
     * Removes empty table informations
     */
    private void removeTableData() {
        this.mySQLManager.update("DROP TABLE log_" + this.urlKey);
        this.mySQLManager.update("DELETE FROM cl_content WHERE urlKey='" + this.urlKey + "'");
    }

    /**
     * Stores a given chatmessage object to database
     */
    public void saveChatMessage(ChatMessage chatMessage) {
        this.mySQLManager.update("INSERT INTO log_" + this.urlKey + "(message,uuid,type,time)VALUES('" + chatMessage.getMessage() + "','" + chatMessage.getUuid().toString() + "','" + chatMessage.getMessageType().toString() + "','" + chatMessage.getTime() + "')");
    }

    /**
     * Checks if the current chatlog table is empty or not
     */
    private boolean checkForEmptyTable() {
        try {
            while (this.mySQLManager.query("SELECT * FROM log_" + this.urlKey).next()) {
                return false;
            }
        } catch (SQLException sqlException) {
            this.chatlog.getExceptionManager().create(sqlException, ErrorType.DATABASE_FETCH_RESULT);
        }
        return true;
    }
}