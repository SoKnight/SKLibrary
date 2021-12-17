package ru.soknight.lib.database.credentials.local;

import lombok.Getter;
import org.bukkit.plugin.Plugin;
import ru.soknight.lib.database.DatabaseType;
import ru.soknight.lib.database.credentials.CredentialField;
import ru.soknight.lib.database.credentials.DatabaseCredentials;
import ru.soknight.lib.database.exception.DriverNotFoundException;
import ru.soknight.lib.tool.Validate;

import java.io.File;
import java.lang.reflect.Constructor;

@Getter
public class SQLiteDatabaseCredentials implements DatabaseCredentials, LocalDatabaseCredentials {

    public static final String URL_PATTERN = "jdbc:sqlite:%s";

    @CredentialField("file")
    private String filePath;

    @Override
    public String getConnectionUrl(Plugin plugin) {
        Validate.notNull(filePath, "filePath");

        String path = new File(plugin.getDataFolder(), filePath.replace('/', File.separatorChar)).getAbsolutePath();
        return String.format(URL_PATTERN, path);
    }

    @Override
    public void loadDriver(Plugin plugin) throws DriverNotFoundException {
        try {
            Constructor<?> constructor = Class.forName("org.sqlite.JDBC").getConstructor();
            constructor.newInstance();
        } catch (Throwable ex) {
            throw new DriverNotFoundException(ex, DatabaseType.SQLITE);
        }
    }

}
