package xyz.syodo.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.syodo.database.sql.ResultSetList;
import xyz.syodo.database.sql.SyoSQL;


public class Group {

	private static Map<String, Group> groups = new HashMap<>();
	
	public static void reloadGroups() {
		groups.clear();
		ResultSetList set = SyoSQL.executeQuery("SELECT name FROM LuckPerms.luckperms_groups");
		
			while(set.next()) {
				
				String name = set.getString(1);
				groups.put(name, new Group(name));
			
			}
			
			boolean anyUpdate = false;
			
			do {
				
				anyUpdate = false;
				
				for(Group g : groups.values()) {
					if(g.update()) anyUpdate = true;
				}
				
			} while(anyUpdate);
			
	}
	
	public static Group getGroup(String name) {
		return groups.get(name);
	}
	
	private final String name;
	private final List<String> permissions;


	
	public Group(String name) {
		
		this.name = name;
		this.permissions = new ArrayList<>();
	
		
		//ResultSet set = LuckPerms.con().execQuery("".replace("%name", this.name));
		ResultSetList set = SyoSQL.executeQuery("SELECT permission FROM LuckPerms.luckperms_group_permissions WHERE name = ? AND server = 'global'", this.name);
		
			while(set.next()) {
				String permission = set.getString("permission");
				this.permissions.add(permission);
			}
		
	}
	
	public boolean update() {
		
		boolean updated = false;
		
		List<String> perms = new ArrayList<>(this.permissions);
		
		for(String perm1 : perms) {
			String gr= "group.";
			if(perm1.startsWith(gr)) {
				String grp = perm1.replace(gr, "");
				if(groups.containsKey(grp)) {
					
					Group group = groups.get(grp);
					for(String perm2 : group.permissions) {
						if(!this.permissions.contains(perm2)) {
							this.permissions.add(perm2);
							updated = true;
						}
					}
					
				}
			}
			
		}
		
		return updated;
		
	}
	
	public List<String> getPermissions() {
		return permissions;
	}
}
