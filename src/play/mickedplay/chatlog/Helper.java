package play.mickedplay.chatlog;

import play.mickedplay.chatlog.database.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by mickedplay on 13.05.2016 at 22:07 UTC+1.
 */
public class Helper {

    private final static String[] charArray = "abcdefghijklmnopqrstuvwxyz0123456789".split("");

    public static String getCurrentDateTime() {
//        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        return String.valueOf(System.currentTimeMillis());
    }

    public static String generateUrlKey(DatabaseManager databaseManager, int keyLength) {
        String urlKey = "";
        for (int i = 0; i < keyLength; i++) {
            urlKey += charArray[new Random().nextInt(charArray.length)];
        }
        if (databaseManager.checkForExistingUrlKey(urlKey)) {
            return generateUrlKey(databaseManager, keyLength);
        }
        return urlKey.toLowerCase();
    }
}