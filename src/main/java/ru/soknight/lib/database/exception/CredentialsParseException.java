package ru.soknight.lib.database.exception;

import lombok.Getter;
import ru.soknight.lib.database.DatabaseType;

@Getter
public class CredentialsParseException extends Exception {

    private final DatabaseType databaseType;

    public CredentialsParseException(String message, DatabaseType databaseType) {
        this(message, null, databaseType);
    }

    public CredentialsParseException(Throwable cause, DatabaseType databaseType) {
        this(cause.getMessage(), cause, databaseType);
    }

    public CredentialsParseException(String message, Throwable cause, DatabaseType databaseType) {
        super(message, cause, false, true);
        this.databaseType = databaseType;
    }

}
