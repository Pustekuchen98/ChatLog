package play.mickedplay.chatlog.database;

import play.mickedplay.chatlog.Chatlog;
import play.mickedplay.chatlog.ExceptionManager;
import play.mickedplay.chatlog.exception.ErrorType;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * Created by mickedplay on 10.05.2016 at 21:44 UTC+1.
 */
public class MySQLManager {

    private Chatlog chatlog;
    private DatabaseManager databaseManager;
    private ExceptionManager exceptionManager;
    private Connection connection;
    private boolean connectionAvailable;

    private SimpleDateFormat lastRecordDay;

    public MySQLManager(DatabaseManager databaseManager) {
        this.chatlog = databaseManager.getChatlog();
        this.databaseManager = databaseManager;
        this.exceptionManager = databaseManager.getChatlog().getExceptionManager();
        this.openConnection();
        this.createRequiredTables();
        if (databaseManager.getChatlog().getSettings().getArchiveType() == ArchiveType.MERGED) {
            this.fetchLastRecordDay();
        }
    }

    public boolean isConnectionAvailable() {
        return connectionAvailable;
    }

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

    public ResultSet query(String query) {
        try {
            return this.connection.prepareStatement(query).executeQuery(query);
        } catch (SQLException exception) {
            this.exceptionManager.create(exception, ErrorType.DATABASE_FETCH_RESULT);
            this.openConnection();
        }
        return null;
    }

    private void fetchLastRecordDay() {
        if (this.connectionAvailable) {
            try {
                ResultSet resultSet = this.query("SELECT lastRecordDay FROM cl_content");
                while (resultSet.next()) {
                    this.lastRecordDay = new SimpleDateFormat(resultSet.getString("lastRecordDay"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void createRequiredTables() {
        if (this.connectionAvailable) {
            this.update("CREATE TABLE IF NOT EXISTS cl_content(id INTEGER UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,urlKey VARCHAR(64),serverName VARCHAR(255),logComplete BOOLEAN,serverStart BIGINT(13),serverStop BIGINT(13))");
//            this.query("");
        }
    }
}