package fr.kinderrkill.sfs.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CommandUsage implements TabCompleter {

    private List<TabCompleter> arguments;

    public CommandUsage(List<TabCompleter> arguments) {
        this.arguments = arguments;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(arguments.size() < args.length) return null;
        List<String> possibilities = arguments.get(args.length-1).onTabComplete(sender, command, alias, args);
        if(possibilities == null) return null;
        return possibilities.stream()
                .filter(arg -> arg.startsWith(args[args.length - 1]))
                .sorted()
                .collect(Collectors.toList());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private List<TabCompleter> arguments = new ArrayList<>();

        private Builder() {
        }

        public Builder withArgument(TabCompleter completer) {
            arguments.add(completer);
            return this;
        }

        public Builder withArgument(Supplier<List<String>> possibilities) {
            return withArgument((sender, command, alias, args) -> possibilities.get());
        }

        public Builder withArgument(List<String> possibilities) {
            return withArgument(() -> possibilities);
        }

        public Builder withArgument(String... possibilities) {
            return withArgument(Arrays.asList(possibilities));
        }

        public CommandUsage build() {
            return new CommandUsage(arguments);
        }
    }
}
