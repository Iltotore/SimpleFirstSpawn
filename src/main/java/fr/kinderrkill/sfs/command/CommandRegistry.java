package fr.kinderrkill.sfs.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandRegistry {

    private Map<CommandName, SubCommand> subCommands = new HashMap<>();
    private String permissionPrefix = "sfs";

    public CommandRegistry(String permissionPrefix){
        this.permissionPrefix = permissionPrefix;
    }

    public void register(CommandName name, SubCommand executor){
        subCommands.put(name, executor);
    }

    public void unregister(CommandName name){
        subCommands.remove(name);
    }

    public String getPermissionPrefix() {
        return permissionPrefix;
    }

    public void setPermissionPrefix(String permissionPrefix) {
        this.permissionPrefix = permissionPrefix;
    }

    public Collection<CommandName> getNames(){
        return subCommands.keySet();
    }

    public boolean execute(String subCommand, CommandSender sender, Command command, String label, String[] subArgs){
        return getCommand(subCommand)
                .map(entry -> {
                    if(sender.hasPermission(permissionPrefix + "." + entry.getKey().getName())){
                        return entry.getValue().onCommand(sender, command, label, subArgs);
                    } else {
                        sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'exécuter cette commande !");
                        return true;
                    }
                })
                .orElse(false);
    }

    public Optional<Map.Entry<CommandName, SubCommand>> getCommand(String id){
        return subCommands.entrySet()
                .stream()
                .filter(entry -> entry.getKey().fit(id))
                .findAny();
    }
}
