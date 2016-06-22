package play.mickedplay.chatlog.exception;

/**
 * Created by mickedplay on 13.05.2016 at 20:09 UTC+1.
 */
public enum ErrorType {

    DATABASE_CANT_CONNECT("Eine Verbindung zur Datenbank ist fehlgeschlagen. Bitte überprüfe die Zugangsdaten auf ihre Richtigkeit."),
    DATABASE_UPDATE("Erforderliche Datensätze konnten nicht in der Datenbank gespeichert werden."),
    DATABASE_FETCH_RESULT("Ein Fehler ist beim Lesen von Datenbankdatensätzen aufgetreten.");

    private String description;

    ErrorType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
