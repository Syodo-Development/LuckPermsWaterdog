package xyz.syodo.listener;

import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.command.SimpleCommandMap;
import dev.waterdog.waterdogpe.command.defaults.ServerCommand;
import xyz.syodo.communication.message.Message;
import xyz.syodo.core.platform.waterdog.event.CommunicationMessageReceiveEvent;
import xyz.syodo.data.User;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.utils.types.Permission;

import java.lang.reflect.Field;
import java.util.UUID;

public class PlayerListener {

	public static void onPlayerJoin(PlayerLoginEvent e) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ProxiedPlayer player = e.getPlayer();
				reloadPermissions(player);
			}
		}).start();
		
	}

	public static void onCommunication(CommunicationMessageReceiveEvent event) {

		Message message = event.getMessage();
		String[] args = message.getArguments();;

		if(args[0].equals("PermissionUpdate")) {
			ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(args[1]));
			if(player != null && player.isConnected()) {
				reloadPermissions(player);
			}
		}
	}

	public static void reloadPermissions(ProxiedPlayer player) {
		player.getPermissions().forEach(perm -> player.removePermission(perm.getName()));
		User user = new User(player);
		for(String permission : user.getPermissions()) {
			if(permission != null)
				if(!player.hasPermission(permission)) {
					player.addPermission(new Permission(permission, true));
				}
		}
	}

}
