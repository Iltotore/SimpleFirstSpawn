package fr.kinderrkill.sfs.command;

import fr.kinderrkill.sfs.SpawnManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDefine implements SubCommand {

    private SpawnManager spawnManager;

    public CommandDefine(SpawnManager spawnManager) {
        this.spawnManager = spawnManager;
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Defines the spawn at the user's location.";
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            spawnManager.setSpawn(((Player) sender).getLocation());
            sender.sendMessage(ChatColor.GREEN + "Done !");
        } else {
            sender.sendMessage(ChatColor.RED + "You're not a player !");
        }
        return true;
    }
}
