package xyz.syodo;

import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.plugin.Plugin;
import dev.waterdog.waterdogpe.utils.config.Configuration;
import xyz.syodo.command.LPWDCommand;
import xyz.syodo.core.platform.waterdog.event.CommunicationMessageReceiveEvent;
import xyz.syodo.data.Group;
import xyz.syodo.listener.PlayerListener;

public class LuckPerms extends Plugin {

	private static Plugin plugin;
	
	private Configuration config;
	
	@Override
	public void onEnable() {
		
		plugin = this;
		
		Group.reloadGroups();
		
		getProxy().getEventManager().subscribe(PlayerLoginEvent.class, PlayerListener::onPlayerJoin);
		getProxy().getEventManager().subscribe(CommunicationMessageReceiveEvent.class, PlayerListener::onCommunication);


		CommandSettings.Builder builder = new CommandSettings.Builder();
		builder.setAliases(new String[] {"lpwd"});
		builder.setDescription("luckperms.waterdog");
		
		Command command = new LPWDCommand("luckpermswaterdog", builder.build());
		getProxy().getCommandMap().registerCommand(command);
		
	}

	public static Plugin get() {
		return plugin;
	}
	
}
