package play.mickedplay.chatlog.sql;

import org.bukkit.configuration.file.FileConfiguration;
import play.mickedplay.chatlog.Chatlog;
import play.mickedplay.chatlog.Registry;
import play.mickedplay.chatlog.ConfigSettings;

import java.sql.*;

public class MySQL {
    private Registry registry;
    private String database;
    private String hostname;
    private int port;
    private String username;
    private String password;
    private String mainTableName;
    private String logTablePrefix;
    private boolean connectionSuccessful;
    private Connection connection;

    public MySQL(Registry registry) {
        this.registry = registry;
        FileConfiguration config = Chatlog.getInstance().getConfig();
        this.hostname = config.getString("MySQL.hostname");
        this.port = config.getInt("MySQL.port");
        this.database = config.getString("MySQL.database");
        this.username = config.getString("MySQL.username");
        this.password = config.getString("MySQL.password");
        this.mainTableName = "chatlog";
        this.logTablePrefix = "log_";
        this.connectionSuccessful = false;
        this.connect();
        this.createMainTable();
    }

    public boolean isConnectionSuccessful() {
        return connectionSuccessful;
    }

    public String getMainTableName() {
        return mainTableName;
    }

    public String getLogTablePrefix() {
        return logTablePrefix;
    }

    private void connect() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database + "?autoReconnect=true", username, password);
            this.connectionSuccessful = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String qry) {
        try {
            PreparedStatement st = connection.prepareStatement(qry);
            st.executeUpdate(qry);
            st.close();
        } catch (SQLException ex) {
            connect();
            ex.printStackTrace();
        }
    }

    public ResultSet query(String qry) {
        try {
            return connection.prepareStatement(qry).executeQuery(qry);
        } catch (SQLException ex) {
            connect();
            ex.printStackTrace();
        }
        return null;
    }

    private void createMainTable() {
        if (this.connectionSuccessful) {
            ConfigSettings configSettings = registry.getConfigSettings();
            this.update("CREATE TABLE IF NOT EXISTS " + this.mainTableName +
                    "(id INTEGER UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY, " +
                    "log_key VARCHAR(" + configSettings.getLogKeyLength() + "),log_table VARCHAR(255),server_name VARCHAR(255),server_start BIGINT(13),server_stop BIGINT(13))");
        }
    }

    public boolean existsLog(String logKey) {
        try {
            ResultSet rs = query("SELECT log_key FROM " + this.mainTableName + " WHERE log_key= '" + logKey + "'");
            if (rs.next()) {
                return rs.getString("log_key") != null;
            }
            return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}