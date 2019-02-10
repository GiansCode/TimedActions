package io.inkstudios.timedactions;

import net.milkbowl.vault.permission.Permission;

import io.inkstudios.commons.log.Logger;
import io.inkstudios.commons.spigot.command.CommandService;
import io.inkstudios.commons.spigot.locale.Locale;
import io.inkstudios.commons.spigot.locale.placeholder.placeholders.CommandArgumentPlaceholder;
import io.inkstudios.commons.spigot.locale.placeholder.placeholders.CommandRuleFailedPlaceholder;
import io.inkstudios.commons.spigot.plugin.PluginTransporter;
import io.inkstudios.timedactions.group.PlaceholderGroups;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class TimedActionsPlugin extends JavaPlugin implements PluginTransporter {

	private static TimedActionsPlugin instance;
	
	public static TimedActionsPlugin getInstance() {
		return instance;
	}
	
	private Locale locale;
	private CommandService commandService;
	private Permission permission;
	
	private PlaceholderGroups placeholderGroups;
	
	@Override
	public void onEnable() {
		instance = this;
		
		loadLocale();
		commandService = new CommandService(this);
		
		placeholderGroups = new PlaceholderGroups();
		placeholderGroups.loadGroups(getConfig());
		
		if (!setupPermissions()) {
			Logger.severe("Failed to find Vault permissions");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		
	}
	
	private void loadLocale() {
		File file = new File(getDataFolder(), "locale.yml");
		if (!file.exists()) {
			saveResource("locale.yml", true);
		}
		
		locale = new Locale(YamlConfiguration.loadConfiguration(file));
		locale.loadMessages();
		
		locale.registerPlaceholder(new CommandArgumentPlaceholder());
		locale.registerPlaceholder(new CommandRuleFailedPlaceholder());
	}
	
	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		permission = rsp.getProvider();
		return permission != null;
	}
	
	@Override
	public void onDisable() {
		instance = null;
	}
	
	public PlaceholderGroups getPlaceholderGroups() {
		return placeholderGroups;
	}
	
	@Override
	public Plugin getPlugin() {
		return this;
	}
	
	@Override
	public Locale getLocale() {
		return locale;
	}
	
	@Override
	public CommandService getCommandService() {
		return commandService;
	}
	
	public Permission getPermission() {
		return permission;
	}
	
}