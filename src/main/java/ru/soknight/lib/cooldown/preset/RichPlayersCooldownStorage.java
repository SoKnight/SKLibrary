package ru.soknight.lib.cooldown.preset;

import ru.soknight.lib.cooldown.RichCooldownStorage;

/**
 * Extension for {@link RichCooldownStorage} with String key preset
 */
public class RichPlayersCooldownStorage extends RichCooldownStorage<String> {

	/**
	 * Extension for {@link RichCooldownStorage} with String key preset
	 * @param defaultCooldown The cooldown default value for all stored entries without custom cooldown
	 */
	public RichPlayersCooldownStorage(long defaultCooldown) {
		super(defaultCooldown);
	}

}
