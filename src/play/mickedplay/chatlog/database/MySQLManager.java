package play.mickedplay.chatlog.database;

import play.mickedplay.chatlog.exception.ErrorType;
import play.mickedplay.chatlog.exception.ExceptionManager;

import java.sql.*;

/**
 * Created by mickedplay on 10.05.2016 at 21:44 CEST.
 * You are not allowed to remove this comment.
 */
public class MySQLManager {

    private DatabaseManager databaseManager;
    private ExceptionManager exceptionManager;
    private Connection connection;
    private boolean connectionAvailable;

    public MySQLManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.exceptionManager = databaseManager.getChatlog().getExceptionManager();
        this.openConnection();
        this.createRequiredTables();
    }

    public boolean isConnectionAvailable() {
        return connectionAvailable;
    }

    /**
     * Opens a connection to a database
     */
    private void openConnection() {
        try {
            int port = this.databaseManager.getChatlog().getSettings().getPort();
            String hostname = this.databaseManager.getChatlog().getSettings().getHostname();
            String database = this.databaseManager.getChatlog().getSettings().getDatabase();
            String username = this.databaseManager.getChatlog().getSettings().getUsername();
            String password = this.databaseManager.getChatlog().getSettings().getPassword();
            this.connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database + "?autoReconnect=true", username, password);
            this.connectionAvailable = true;
        } catch (SQLException exception) {
            this.exceptionManager.create(exception, ErrorType.DATABASE_CANT_CONNECT);
        }
    }

    /**
     * Executes a mysql command
     *
     * @param query The query
     */
    public void update(String query) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.executeUpdate(query);
            preparedStatement.close();
        } catch (SQLException exception) {
            this.exceptionManager.create(exception, ErrorType.DATABASE_UPDATE);
            this.openConnection();
        }
    }

    /**
     * Returns a ResultSet which was requested with the given query
     *
     * @param query The query
     * @return A ResultSet which contains the requested data
     */
    public ResultSet query(String query) {
        try {
            return this.connection.prepareStatement(query).executeQuery(query);
        } catch (SQLException exception) {
            this.exceptionManager.create(exception, ErrorType.DATABASE_FETCH_RESULT);
            this.openConnection();
        }
        return null;
    }

    /**
     * Creates the main database table
     */
    private void createRequiredTables() {
        if (this.connectionAvailable) {
            this.update("CREATE TABLE IF NOT EXISTS _storage(id INTEGER UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,reference VARCHAR(64),server VARCHAR(255),completed BOOLEAN,start BIGINT(13),stop BIGINT(13))");
        }
    }
}