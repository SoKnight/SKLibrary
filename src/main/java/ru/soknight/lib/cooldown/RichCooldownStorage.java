package ru.soknight.lib.cooldown;

import java.util.HashMap;

/**
 * Rich cooldown storage interface implementation with individual cooldowns values
 */
public class RichCooldownStorage<K> extends HashMap<K, CustomCooldownValue> implements CooldownStorage<K> {
	
	private final long defaultCooldown;
	
	/**
	 * Rich cooldown storage interface implementation with default cooldown value
	 * @param defaultCooldown The cooldown default value for all stored entries without custom cooldown (in seconds)
	 */
	public RichCooldownStorage(long defaultCooldown) {
		this.defaultCooldown = defaultCooldown * 1000;
	}
	
	@Override
	public long getElapsedTime(K target) {
		CustomCooldownValue value = get(target);
		if(value == null) return -1;
		
		long stored = value.getResetDate();
		
		long current = System.currentTimeMillis();
		long elapsed = current - stored;
		
		return elapsed;
	}

	@Override
	public long getRemainedTime(K target) {
		CustomCooldownValue value = get(target);
		if(value == null) return -1;
		
		long elapsed = getElapsedTime(target);
		if(elapsed == -1) return -1;
		
		long remained = value.getCooldown() - elapsed;
		if(remained < 0) return 0;
		
		return remained;
	}

	@Override
	public long getResetDate(K target) {
		CustomCooldownValue value = get(target);
		if(value == null) return -1;
		
		return value.getResetDate();
	}

	@Override
	public boolean hasCooldown(K target) {
		return containsKey(target);
	}

	@Override
	public void removeCooldown(K target) {
		remove(target);
	}

	@Override
	public void refreshResetDate(K target) {
		CustomCooldownValue old = get(target);
		CustomCooldownValue newv = new CustomCooldownValue(old != null ? old.getCooldown() : this.defaultCooldown);
	
		put(target, newv);
	}
	
	/**
	 * Resets the custom cooldown time to default time specified in constructor of this object
	 * @param target Target ket value to reset (player name and etc.)
	 */
	public void resetCustomCooldown(K target) {
		CustomCooldownValue value = get(target);
		if(value == null) {
			value = new CustomCooldownValue(this.defaultCooldown);
		} else value.setCooldown(this.defaultCooldown);
		
		put(target, value);
	}
	
	/**
	 * Sets the custom cooldown time in seconds for target key value
	 * @param target Target key value to set (player name and etc.)
	 * @param cooldown The new cooldown time (in SECONDS)
	 * @param refreshResetDate Should to refresh cooldown reset date or not
	 */
	public void setCustomCooldown(K target, long cooldown, boolean refreshResetDate) {
		CustomCooldownValue old = get(target);
		long date = old != null && !refreshResetDate ? old.getResetDate() : System.currentTimeMillis();
		
		CustomCooldownValue newv = new CustomCooldownValue(cooldown * 1000, date);
	
		put(target, newv);
	}
	
	/**
	 * Sets the custom cooldown time in seconds and refreshes cooldown reset date
	 * @param target Target key value to set (player name and etc.)
	 * @param cooldown The new cooldown time (in SECONDS)
	 */
	public void setCustomCooldown(K target, long cooldown) {
		setCustomCooldown(target, cooldown * 1000, true);
	}

	@Override
	public void setResetDate(K target, long resetDate) {
		CustomCooldownValue old = get(target);
		long cooldown = old != null ? old.getCooldown() : this.defaultCooldown;
		
		CustomCooldownValue newv = new CustomCooldownValue(cooldown, resetDate);
	
		put(target, newv);
	}

}
