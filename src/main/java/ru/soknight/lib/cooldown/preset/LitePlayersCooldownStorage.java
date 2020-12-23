package ru.soknight.lib.cooldown.preset;

import ru.soknight.lib.cooldown.LiteCooldownStorage;

/**
 * Extension for {@link LiteCooldownStorage} with String key preset
 */
public class LitePlayersCooldownStorage extends LiteCooldownStorage<String> implements PlayersCooldownStorage {

	/**
	 * Extension for {@link LiteCooldownStorage} with String key preset
	 * @param cooldown The cooldown constant for all stored entries
	 */
	public LitePlayersCooldownStorage(long cooldown) {
		super(cooldown);
	}

}
