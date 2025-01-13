package xyz.syodo.data;

import java.util.ArrayList;
import java.util.List;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import xyz.syodo.database.sql.ResultSetList;
import xyz.syodo.database.sql.SyoSQL;

public class User {

	private final String name;
	private String group;
	private final List<String> permissions = new ArrayList<>();
	
	public User(ProxiedPlayer player) {
		
		this.name = player.getName();
		ResultSetList set = SyoSQL.executeQuery("SELECT primary_group FROM LuckPerms.luckperms_players WHERE uuid = ?", player.getUniqueId().toString());
		ResultSetList userperms = SyoSQL.executeQuery("SELECT permission FROM LuckPerms.luckperms_user_permissions WHERE uuid = ? AND value = 1", player.getUniqueId().toString());
		if(set.next() ) {

			this.group = set.getString("primary_group");
		
			while(userperms.next()) {
				this.permissions.add(set.getString("permission"));
			}
			
		} else {
			this.group = "default";
		}

		this.permissions.addAll(Group.getGroup(this.group).getPermissions());

	}

	public String getName() {
		return name;
	}
	
	public List<String> getPermissions() {
		return permissions;
	}

	public String getGroup() {
		return group;
	}
	
}
