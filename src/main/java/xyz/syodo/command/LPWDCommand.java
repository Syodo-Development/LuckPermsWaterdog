package xyz.syodo.command;

import xyz.syodo.LuckPerms;
import xyz.syodo.data.Group;
import xyz.syodo.data.User;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.utils.types.Permission;
import xyz.syodo.listener.PlayerListener;

public class LPWDCommand extends Command {

	public LPWDCommand(String name, CommandSettings settings) {
		super(name, settings);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onExecute(CommandSender sender, String alias, String[] args) {
		
		if(args.length == 1) {
			
			if(args[0].equalsIgnoreCase("reload")) {
				
				Group.reloadGroups();
				for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers().values()) {
					PlayerListener.reloadPermissions(player);
				}

				sender.sendMessage("§aReloaded all groups and players!");
				return true;
			}
			
		}
		
		return false;
	}

}
