package ru.soknight.lib.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import ru.soknight.lib.configuration.Configuration;
import ru.soknight.lib.database.credentials.AuthDatabaseCredentials;
import ru.soknight.lib.database.credentials.DatabaseCredentials;
import ru.soknight.lib.database.exception.*;
import ru.soknight.lib.database.migration.MigrationManager;
import ru.soknight.lib.database.migration.exception.*;
import ru.soknight.lib.database.migration.runtime.MigrationDataConverter;
import ru.soknight.lib.database.migration.runtime.WrappedDataConverter;
import ru.soknight.lib.tool.Validate;

import java.sql.SQLException;

@Getter
public class Database {

    private final Plugin plugin;
    private final DatabaseCredentials credentials;
    private final ConnectionSource bootstrapConnection;
    private final MigrationManager migrationManager;

    public Database(@NotNull Plugin plugin, @NotNull Configuration config) throws
            CredentialsParseException,
            DatabaseBootstrapException,
            DriverNotFoundException,
            DriverLoadException,
            SQLException,
            UnknownDatabaseTypeException
    {
        this(plugin, config.getSection("database"));
    }

    public Database(@NotNull Plugin plugin, @NotNull ConfigurationSection databaseSection) throws
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

        // migrations manager instance creation
        this.migrationManager = new MigrationManager(plugin, this);
    }

    public @NotNull Database complete() {
        Validate.notNull(bootstrapConnection, "bootstrapConnection");
        bootstrapConnection.closeQuietly();
        return this;
    }

    public @NotNull ConnectionSource establishConnection() throws SQLException {
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
                "Credentials auth is required, but it is not an " +
                "instance of the AuthDatabaseCredentials!"
        );
    }

    public @NotNull Database performMigrations() throws AbstractMigrationException, SQLException {
        migrationManager.runMigrations();
        return this;
    }

    public @NotNull Database setActualSchemaVersion(int version) {
        migrationManager.setActualSchemaVersion(version);
        return this;
    }

    public @NotNull Database setMigrationsPathRoot(@NotNull String migrationsPathRoot) {
        migrationManager.setMigrationsPathRoot(migrationsPathRoot);
        return this;
    }

    public @NotNull Database registerDataConverter(@NotNull String migrationPath, @NotNull MigrationDataConverter<?, ?> dataConverter) {
        migrationManager.registerDataConverter(migrationPath, dataConverter);
        return this;
    }

    public @NotNull Database registerDataConverter(@NotNull WrappedDataConverter<?, ?> wrappedDataConverter) {
        migrationManager.registerDataConverter(wrappedDataConverter);
        return this;
    }

    public @NotNull Database createTable(@NotNull Class<?> daoClass) throws SQLException {
        Validate.notNull(daoClass, "daoClass");
        Validate.notNull(bootstrapConnection, "bootstrapConnection");

        TableUtils.createTableIfNotExists(bootstrapConnection, daoClass);
        return this;
    }

    public @NotNull Database createTables(@NotNull Class<?>... daoClasses) throws SQLException {
        Validate.notNull(daoClasses, "daoClasses");
        Validate.notNull(bootstrapConnection, "bootstrapConnection");

        for(Class<?> daoClass : daoClasses)
            TableUtils.createTableIfNotExists(bootstrapConnection, daoClass);

        return this;
    }

}
