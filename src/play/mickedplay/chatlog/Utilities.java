package play.mickedplay.chatlog;

import play.mickedplay.chatlog.database.DatabaseManager;

import java.util.Random;

/**
 * Created by mickedplay on 13.05.2016 at 22:07 CEST.
 * You are not allowed to remove this comment.
 */
public class Utilities {

    private final static String[] charArray = "abcdefghijklmnopqrstuvwxyz0123456789".split("");

    public static String getCurrentDateTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String generateUrlKey(DatabaseManager databaseManager, int keyLength) {
        String urlKey = "";
        for (int i = 0; i < keyLength; i++) urlKey += charArray[new Random().nextInt(charArray.length)];
        return databaseManager.checkForExistingUrlKey(urlKey) ? generateUrlKey(databaseManager, keyLength) : urlKey.toLowerCase();
    }

    public static String getServerFolder(Chatlog chatlog) {
        String folderPath = chatlog.getPlugin().getServer().getWorldContainer().getAbsolutePath();
        return folderPath.substring(0, folderPath.length() - 1);
    }
}