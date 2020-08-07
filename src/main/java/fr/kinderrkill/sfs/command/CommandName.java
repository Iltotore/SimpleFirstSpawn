package fr.kinderrkill.sfs.command;

import java.util.*;

public class CommandName {

    private String name;
    private Collection<String> aliases;

    private CommandName(String name, Collection<String> aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    public String getName() {
        return name;
    }

    public List<String> getAliases() {
        return new ArrayList<>(aliases);
    }

    public List<String> getIdentifiers(){
        List<String> ids = getAliases();
        ids.add(name);
        return ids;
    }

    public boolean fit(String key){
        return name.equals(key) || aliases.contains(key);
    }

    public static CommandName of(String name, Collection<String> aliases){
        return new CommandName(name, aliases);
    }

    public static CommandName of(String name, String... aliases) {
        return new CommandName(name, Arrays.asList(aliases));
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof CommandName)) return false;
        CommandName that = (CommandName) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(aliases, that.aliases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, aliases);
    }
}
