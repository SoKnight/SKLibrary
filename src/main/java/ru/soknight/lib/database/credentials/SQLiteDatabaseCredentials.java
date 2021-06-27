package ru.soknight.lib.database.credentials;

import lombok.Getter;
import org.bukkit.plugin.Plugin;
import ru.soknight.lib.database.DatabaseType;
import ru.soknight.lib.database.exception.DriverNotFoundException;
import ru.soknight.lib.tool.Validate;

import java.io.File;
import java.lang.reflect.Constructor;

@Getter
public class SQLiteDatabaseCredentials implements DatabaseCredentials {

    public static final String URL_PATTERN = "jdbc:sqlite:%s";

    @CredentialField("file")
    private String filename;

    @Override
    public String getConnectionUrl(Plugin plugin) {
        Validate.notNull(filename, "filename");

        String path = new File(plugin.getDataFolder(), filename).getAbsolutePath();
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
