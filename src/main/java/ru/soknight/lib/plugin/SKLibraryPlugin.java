package ru.soknight.lib.plugin;

import com.j256.ormlite.logger.*;
import org.bukkit.plugin.java.JavaPlugin;
import ru.soknight.lib.configuration.Configuration;
import ru.soknight.lib.configuration.Messages;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

public class SKLibraryPlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		// Allowing only ORMLite errors logging
		LoggerFactory.setLogBackendFactory(JavaUtilLogBackend::new);
		LoggerFactory.setLogBackendType(LogBackendType.JAVA_UTIL);
		setORMLiteLogLevel(Level.ERROR);

		Configuration config = new Configuration(this, "config.yml");
		if(!config.getBoolean("enabled"))
			return;
		
		Messages messages = new Messages(this, "messages.yml");
		new CommandInfo(this, messages);
	}

	public void setORMLiteLogLevel(Level level) {
		Logger.setGlobalLogLevel(level);
	}

	static {
		// Fix ORMLite 'Unable to get new instance of class' warnings in the MC console
		// TODO try to find more pretty fix solution in the future releases
		try {
			Field printStreamField = LocalLogBackend.class.getDeclaredField("printStream");
			printStreamField.setAccessible(true);

			PrintStream printStream = new PrintStream(new SilentOutputStream());
			printStreamField.set(null, printStream);
		} catch (NoSuchFieldException | IllegalAccessException ignored) {}
	}

	private static final class SilentOutputStream extends OutputStream {
		@Override
		public void write(int b) {}
	}
	
}
