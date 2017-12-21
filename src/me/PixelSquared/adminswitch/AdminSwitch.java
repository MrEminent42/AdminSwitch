package me.PixelSquared.adminswitch;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class AdminSwitch extends JavaPlugin {
	
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("This command can only be executed by a player.");
			return true;
		}
	
		Player p = (Player) sender;
		
		if (!p.hasPermission(new Permission("adminswitch.use", PermissionDefault.FALSE))) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.no-permission")));
			return true;
		}
		
		if (args.length < 1) {
			p.sendMessage(ChatColor.RED + "Usage: /admin <on/off/reload>");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("on")) {
			
			for (String s : getConfig().getStringList("commands.to-admin")) {
				s = s.replaceAll("%player%", p.getName());
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
			}
			
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.to-admin")));
			
			return true;
			
		} else if (args[0].equalsIgnoreCase("off")) {
			
			for (String s : getConfig().getStringList("commands.to-player")) {
				s = s.replaceAll("%player%", p.getName());
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
			}

			p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.to-player")));
			
			return true;
			
		} else if (args[0].equalsIgnoreCase("reload")) {
			
			this.reloadConfig();
			p.sendMessage(ChatColor.GREEN + "AdminSwitch config reloaded.");
		} else {
			p.sendMessage(ChatColor.RED + "Usage: /admin <on/off/reload>");
			return true;
		}
		
		return true;
	}
	
}