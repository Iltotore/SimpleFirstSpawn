package fr.kinderrkill.sfs.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class CompositeTabCompleter implements TabCompleter {

    private List<TabCompleter> completers;

    public CompositeTabCompleter(List<TabCompleter> completers) {
        this.completers = completers;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        for(TabCompleter completer : completers) {
            List<String> completion = completer.onTabComplete(sender, command, alias, args);
            if(completion != null && !completion.isEmpty()) return completion;
        }
        return null;
    }
}
