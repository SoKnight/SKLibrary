package ru.soknight.lib.database.credentials;

public interface AuthDatabaseCredentials extends DatabaseCredentials {

    String getUsername();

    String getPassword();

    @Override
    default boolean isAuthRequired() {
        return true;
    }

}
