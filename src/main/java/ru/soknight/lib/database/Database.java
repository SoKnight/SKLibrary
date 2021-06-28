package ru.soknight.lib.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import ru.soknight.lib.configuration.Configuration;
import ru.soknight.lib.database.credentials.AuthDatabaseCredentials;
import ru.soknight.lib.database.credentials.DatabaseCredentials;
import ru.soknight.lib.database.exception.*;
import ru.soknight.lib.tool.Validate;

import java.sql.SQLException;

@Getter
public class Database {

    private final Plugin plugin;
    private final DatabaseCredentials credentials;
    private final ConnectionSource bootstrapConnection;

    public Database(Plugin plugin, Configuration config) throws
            CredentialsParseException,
            DatabaseBootstrapException,
            DriverNotFoundException,
            DriverLoadException,
            SQLException,
            UnknownDatabaseTypeException
    {
        this(plugin, config.getSection("database"));
    }

    public Database(Plugin plugin, ConfigurationSection databaseSection) throws
            CredentialsParseException,
            DatabaseBootstrapException,
            DriverNotFoundException,
            DriverLoadException,
            SQLException,
            UnknownDatabaseTypeException
    {
        this.plugin = plugin;

        // check is database section exists
        if(databaseSection == null)
            throw new DatabaseBootstrapException("'database' section was not found in the main config!");

        String typeId = databaseSection.getString("type");
        DatabaseType databaseType = DatabaseType.getById(typeId);

        // check is database connection configuration exists
        ConfigurationSection typeSection = databaseSection.getConfigurationSection(typeId);
        if(typeSection == null)
            throw new DatabaseBootstrapException("'database." + typeId + "' section was not found in the main config!");

        this.credentials = DatabaseCredentials.parse(typeSection, databaseType);

        // loading the database JDBC driver
        credentials.loadDriver(plugin);

        // trying to connect
        this.bootstrapConnection = establishConnection();
    }

    public Database complete() {
        bootstrapConnection.closeQuietly();
        return this;
    }

    public ConnectionSource establishConnection() throws SQLException {
        Validate.notNull(credentials, "credentials");

        String url = credentials.getConnectionUrl(plugin);
        if(!credentials.isAuthRequired())
            return new JdbcConnectionSource(url);

        if(credentials instanceof AuthDatabaseCredentials) {
            AuthDatabaseCredentials authCredentials = (AuthDatabaseCredentials) credentials;
            String username = authCredentials.getUsername();
            String password = authCredentials.getPassword();
            return new JdbcConnectionSource(url, username, password);
        }

        throw new IllegalArgumentException(
                "credentials auth is required, but it is not an instance of the AuthDatabaseCredentials!"
        );
    }

    public Database createTable(Class<?> daoClass) throws SQLException {
        Validate.notNull(bootstrapConnection, "bootstrapConnection");

        TableUtils.createTableIfNotExists(bootstrapConnection, daoClass);
        return this;
    }

    public Database createTables(Class<?>... daoClasses) throws SQLException {
        Validate.notNull(daoClasses, "daoClasses");
        Validate.notNull(bootstrapConnection, "bootstrapConnection");

        for(Class<?> daoClass : daoClasses)
            TableUtils.createTableIfNotExists(bootstrapConnection, daoClass);

        return this;
    }

}
