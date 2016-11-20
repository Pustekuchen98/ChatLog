package play.mickedplay.chatlog.exception;

import org.apache.commons.lang.exception.ExceptionUtils;
import play.mickedplay.chatlog.Chatlog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mickedplay on10.05.2016at 21:55 CEST.
 * You are not allowed to remove this comment.
 */

public class ExceptionManager {

    private Chatlog chatlog;
    private ArrayList<String> stackTraceList;
    private File exceptionDirectory;

    public ExceptionManager(Chatlog chatlog) {
        this.chatlog = chatlog;
        this.stackTraceList = new ArrayList<>();
        this.exceptionDirectory = new File(chatlog.getPlugin().getDataFolder(), "exceptions");
        if (!exceptionDirectory.exists()) exceptionDirectory.mkdirs();
    }
    /**

     * Stores an exception in a directory
     *
     * @param exception The exception
     * @param errorType The errorType
     */
    public void create(Exception exception, ErrorType errorType) {
        this.stackTraceList.addAll(Arrays.asList(errorType.getDescription(), "", ExceptionUtils.getStackTrace(exception)));
        try {
            Date date = Calendar.getInstance().getTime();
            String absoluteFilePath = this.exceptionDirectory.getAbsolutePath() + "/Exception_" + new SimpleDateFormat("yyyy-MM-dd").format(date) + "_" + new SimpleDateFormat("HH-mm-hh").format(date) + ".txt";
            if (new File(absoluteFilePath).createNewFile()) {
                Files.write(Paths.get(absoluteFilePath), this.stackTraceList, Charset.forName("iso-8859-2"), StandardOpenOption.APPEND);
                this.stackTraceList.clear();
                this.chatlog.getPlugin().getLogger().severe("AN ERROR HAS OCCURED! For more details see " + absoluteFilePath);
            } else {
                exception.printStackTrace();
                this.chatlog.getPlugin().getLogger().severe("FAILED TO CREATE EXCEPTION FILE! PLEASE CHECK DIRECTORY WRITE PERMISSIONS!");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}