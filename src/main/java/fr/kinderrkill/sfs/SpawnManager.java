package fr.kinderrkill.sfs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class SpawnManager {

    private Location spawn = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public void save(ConfigurationSection section) {
        section.set("spawn", spawn);
    }

    public void load(ConfigurationSection section) {
        spawn = (Location) section.get("spawn", new Location(Bukkit.getWorlds().get(0), 0, 0, 0));
    }

    public static SpawnManager fromConfig(ConfigurationSection section){
         SpawnManager spawnManager = new SpawnManager();
         spawnManager.load(section);
         return spawnManager;
    }
}