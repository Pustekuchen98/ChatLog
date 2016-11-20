package play.mickedplay.chatlog.exception;

/**
 * Created by mickedplay on 13.05.2016 at 20:09 CEST.
 * You are not allowed to remove this comment.
 */
public enum ErrorType {

    // @formatter:off
    DATABASE_CANT_CONNECT("Connection to database failed. Please check your config settings."),
    DATABASE_UPDATE("Failed to store informations in your database!"),
    DATABASE_FETCH_RESULT("Failed to read database informations!"),
    LANG_CONFIG_SAVE_FAILED("Failed to save language file!");
    // @formatter:on

    private String description;

    ErrorType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
