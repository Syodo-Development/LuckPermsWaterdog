package xyz.syodo.listener;

import xyz.syodo.data.User;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.utils.types.Permission;

public class PlayerListener {

	public static void onPlayerJoin(PlayerLoginEvent e) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ProxiedPlayer player = e.getPlayer();
				
				User user = new User(player);				
				for(String permission : user.getPermissions()) {
					if(permission != null)
					if(!player.hasPermission(permission)) {
						player.addPermission(new Permission(permission, true));
					}
				}
				
			}
		}).start();
		
	}
	
}
