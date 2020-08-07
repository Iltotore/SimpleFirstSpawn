package fr.kinderrkill.sfs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SFSListeners implements Listener {

	private SpawnManager spawnManager;
	private ConfigurationSection sfsConfig;

	public SFSListeners(SpawnManager spawnManager, ConfigurationSection sfsConfig) {
		this.spawnManager = spawnManager;
		this.sfsConfig = sfsConfig;
	}

	@EventHandler
	public void playerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(!player.hasPlayedBefore()) {
			player.teleport(spawnManager.getSpawn());
			if(sfsConfig.getBoolean("active_first_join_message")) {
				event.setJoinMessage(null);
				String finalMessage = format(sfsConfig.getString("player_first_join_message"), player);
				for(Player p : Bukkit.getOnlinePlayers()) p.sendMessage(finalMessage);
			}
			if(sfsConfig.getBoolean("first_join_sound")) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
				}
			}
		} else {
			if(sfsConfig.getBoolean("active_player_join_message")) {
				event.setJoinMessage(null);
				for(Player p : Bukkit.getOnlinePlayers()) {
					String finalMessage = format(sfsConfig.getString("player_join_message"), player);
					p.sendMessage(finalMessage);
				}
			}
		}
	}

	@EventHandler
	public void playerLeave(PlayerQuitEvent event) {
		if(sfsConfig.getBoolean("active_player_quit_message")) {
			event.setQuitMessage(null);
			for(Player p : Bukkit.getOnlinePlayers()) {
				String finalMessage = format(sfsConfig.getString("player_quit_message"), event.getPlayer());
				p.sendMessage(finalMessage);
			}
		}
	}

	private String format(String message, OfflinePlayer player) {
		if(player != null) message = message.replace("%player%", player.getName());
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	private String format(String message){
		return format(message, null);
	}
}
