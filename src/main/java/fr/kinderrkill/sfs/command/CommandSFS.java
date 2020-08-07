package fr.kinderrkill.sfs.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandSFS implements TabExecutor {

    private CommandRegistry registry;
    private TabCompleter completer;

    public CommandSFS(CommandRegistry registry) {
        this.registry = registry;
        completer = CommandUsage.builder()
                .withArgument(registry.getNames().stream().flatMap(cmd -> cmd.getIdentifiers().stream()).collect(Collectors.toList()))
                .withArgument((sender, command, alias, args) -> {
                    String subCmd = args[0];
                    return registry.getCommand(subCmd)
                            .map(entry -> entry.getValue().onTabComplete(sender, command, alias, args))
                            .orElse(null);
                })
                .build();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /sfs <subCommand>");
            return false;
        }
        String subCmd = args[0];
        return registry.execute(subCmd, sender, command, label, Arrays.copyOfRange(args, 1, args.length));
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return completer.onTabComplete(sender, command, alias, args);
    }

}
