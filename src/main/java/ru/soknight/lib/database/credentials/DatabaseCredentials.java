package ru.soknight.lib.database.credentials;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import ru.soknight.lib.database.DatabaseType;
import ru.soknight.lib.database.exception.CredentialsParseException;
import ru.soknight.lib.database.exception.DriverLoadException;
import ru.soknight.lib.database.exception.DriverNotFoundException;

public interface DatabaseCredentials {

    String getConnectionUrl(Plugin plugin);

    void loadDriver(Plugin plugin) throws DriverNotFoundException, DriverLoadException;

    default boolean isAuthRequired() {
        return false;
    }

    static DatabaseCredentials parse(ConfigurationSection config, DatabaseType databaseType)
            throws CredentialsParseException
    {
        return CredentialsParser.parse(config, databaseType);
    }

}
