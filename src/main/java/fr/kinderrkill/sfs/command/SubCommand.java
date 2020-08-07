package fr.kinderrkill.sfs.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public interface SubCommand extends TabExecutor {

    default String getUsage(){
        return "";
    }

    String getDescription();

    @Override
    default List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
