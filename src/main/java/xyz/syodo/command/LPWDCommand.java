package xyz.syodo.command;

import xyz.syodo.data.Group;
import xyz.syodo.data.User;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.utils.types.Permission;

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
					User user = new User(player);
					for(String permission : user.getPermissions()) {
						if(permission != null)
							if(!player.hasPermission(permission)) {
								player.addPermission(new Permission(permission, true));
							}
					}
				}
				
				sender.sendMessage("§aReloaded all groups and players!");
				return true;
			}
			
		}
		
		return false;
	}

}
