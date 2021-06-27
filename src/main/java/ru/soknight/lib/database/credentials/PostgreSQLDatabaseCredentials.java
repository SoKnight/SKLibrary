package ru.soknight.lib.database.credentials;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;
import ru.soknight.lib.database.DatabaseType;
import ru.soknight.lib.database.exception.DriverLoadException;
import ru.soknight.lib.database.exception.DriverNotFoundException;
import ru.soknight.lib.external.voidpointer.bukkit.framework.dependency.DependencyLoader;
import ru.soknight.lib.external.voidpointer.bukkit.framework.dependency.PluginDependencyLoader;
import ru.soknight.lib.tool.MD5Checksum;
import ru.soknight.lib.tool.Validate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostgreSQLDatabaseCredentials implements AuthDatabaseCredentials {

    private static final String POSTGRESQL_URL = "https://jdbc.postgresql.org/download/postgresql-42.2.18.jar";
    private static final String POSTGRESQL_CHECKSUM = "d6895bb05ac7b9c85c4e89f3880127e3";
    private static final String POSTGRESQL_OUTPUT_FILE = "postgresql.jar";

    public static final String URL_PATTERN = "jdbc:postgresql://%s:%d/%s%s";

    @CredentialField("host")
    private String hostname;
    @CredentialField("port")
    private int port;
    @CredentialField("name")
    private String databaseName;

    @CredentialField("user")
    private String username;
    @CredentialField("password")
    private String password;

    @CredentialField("params")
    private List<String> parameters;

    @Override
    public String getConnectionUrl(Plugin plugin) {
        Validate.notNull(hostname, "hostname");
        Validate.notNull(databaseName, "name");
        Validate.notNull(username, "user");
        Validate.notNull(password, "password");

        return String.format(URL_PATTERN, hostname, port, databaseName, formatParameters());
    }

    private String formatParameters() {
        if(parameters == null || parameters.isEmpty())
            return "";

        String joined = parameters.stream()
                .map(this::urlEncode)
                .collect(Collectors.joining("&"));

        return "?" + joined;
    }

    @SneakyThrows(UnsupportedEncodingException.class)
    private String urlEncode(String source) {
        return URLEncoder.encode(source, "UTF-8");
    }

    @Override
    public void loadDriver(Plugin plugin) throws DriverNotFoundException, DriverLoadException {
        try {
            Constructor<?> constructor = Class.forName("org.postgresql.Driver").getConstructor();
            constructor.newInstance();
            plugin.getLogger().info("PostgreSQL JDBC Driver is already loaded in the JVM Runtime.");
        } catch (Throwable ex) {
            DependencyLoader dependencyLoader = PluginDependencyLoader.forPlugin(plugin);
            boolean loaded;

            try {
                File driverFile = downloadPostgreSQLDriver(plugin);
                loaded = dependencyLoader.load(driverFile);

                if(loaded) {
                    Constructor<?> constructor = Class.forName("org.postgresql.Driver").getConstructor();
                    constructor.newInstance();
                    plugin.getLogger().info("PostgreSQL JDBC Driver has been loaded into the JVM Runtime.");
                }
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | NoSuchFieldException ex2) {
                throw new DriverNotFoundException(ex2, DatabaseType.POSTGRESQL);
            } catch (IOException ex2) {
                throw new DriverLoadException(ex2, DatabaseType.POSTGRESQL);
            }

            if(!loaded)
                throw new DriverLoadException("Couldn't download the PostgreSQL JDBC Driver!", DatabaseType.POSTGRESQL);
        }
    }

    private static File downloadPostgreSQLDriver(Plugin plugin) throws IOException, DriverLoadException {
        File driverFolder = new File(plugin.getDataFolder(), "driver");
        driverFolder.mkdirs();

        File destination = new File(driverFolder, POSTGRESQL_OUTPUT_FILE);
        if(destination.exists()) {
            if(!verifyMD5Hashsum(destination))
                throw new DriverLoadException("Failed to verify MD5 checksum! Please, try again.", DatabaseType.POSTGRESQL);

            return destination;
        } else {
            destination.delete();
        }

        InputStream externalResource = new URL(POSTGRESQL_URL).openConnection().getInputStream();
        if(externalResource == null)
            throw new DriverLoadException("External driver resource was not found!", DatabaseType.POSTGRESQL);

        plugin.getLogger().info("Downloading PostgreSQL JDBC driver...");
        plugin.getLogger().info("URL: " + POSTGRESQL_URL);

        Files.copy(externalResource, destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return destination;
    }

    private static boolean verifyMD5Hashsum(File downloadedFile) {
        try {
            String checksum = MD5Checksum.getMD5Checksum(downloadedFile);
            return checksum.equalsIgnoreCase(POSTGRESQL_CHECKSUM);
        } catch (Exception e) {
            return false;
        }
    }

}
