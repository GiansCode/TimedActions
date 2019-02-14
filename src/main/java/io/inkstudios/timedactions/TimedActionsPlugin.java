package io.inkstudios.timedactions;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.milkbowl.vault.permission.Permission;

import io.inkstudios.commons.log.Logger;
import io.inkstudios.commons.spigot.command.CommandService;
import io.inkstudios.commons.spigot.locale.Locale;
import io.inkstudios.commons.spigot.locale.placeholder.placeholders.CommandArgumentPlaceholder;
import io.inkstudios.commons.spigot.locale.placeholder.placeholders.CommandRuleFailedPlaceholder;
import io.inkstudios.commons.spigot.plugin.PluginTransporter;
import io.inkstudios.timedactions.group.PlaceholderGroups;
import io.inkstudios.timedactions.locale.placeholders.PlayerTimedActionNamePlaceholder;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

import java.io.File;

public final class TimedActionsPlugin extends JavaPlugin implements PluginTransporter {
	
	private static TimedActionsPlugin instance;
	
	public static TimedActionsPlugin getInstance() {
		return instance;
	}
	
	private Locale locale;
	private CommandService commandService;
	private Permission permission;
	
	private TimedActionConfiguration timedActionConfiguration;
	private PlaceholderGroups placeholderGroups;
	private TimedActions timedActions;
	
	private JDA jda;
	
	@Override
	public void onEnable() {
		instance = this;
		
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		
		timedActionConfiguration = new TimedActionConfiguration();
		timedActionConfiguration.load(getConfig());
		
		loadLocale();
		commandService = new CommandService(this);
		
		placeholderGroups = new PlaceholderGroups();
		placeholderGroups.loadGroups(getConfig());
		
		if (!setupPermissions()) {
			Logger.severe("Failed to find Vault permissions");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		timedActions = new TimedActions();
		timedActions.loadTimedActions(getConfig());
		
		try {
			jda = new JDABuilder(timedActionConfiguration.getDiscordToken())
					.build();
			jda.awaitReady();
			Logger.info("Discord Bot connected");
		} catch (LoginException | InterruptedException exception) {
			Logger.severe("Failed to connect discord bot", exception);
		}
		
		getServer().getScheduler().runTaskTimer(this, new TimedActionTask(), 20L, 20L);
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
		locale.registerPlaceholder(new PlayerTimedActionNamePlaceholder());
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
	
	public TimedActionConfiguration getTimedActionConfiguration() {
		return timedActionConfiguration;
	}
	
	public PlaceholderGroups getPlaceholderGroups() {
		return placeholderGroups;
	}
	
	public TimedActions getTimedActions() {
		return timedActions;
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
	
	public JDA getJda() {
		return jda;
	}
	
	public void sendDiscordMessage(String message) {
		jda.getTextChannelById(timedActionConfiguration.getDiscordChannelId()).sendMessage(
				new MessageBuilder()
						.setContent(message)
						.build()
		).submit().whenComplete((msg, throwable) -> Logger.info(throwable));
	}
	
}