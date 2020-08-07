package fr.kinderrkill.sfs;

import fr.kinderrkill.sfs.command.CommandDefine;
import fr.kinderrkill.sfs.command.CommandName;
import fr.kinderrkill.sfs.command.CommandRegistry;
import fr.kinderrkill.sfs.command.CommandSFS;
import fr.kinderrkill.sfs.util.ThrowingRunnable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class SimpleFirstSpawn extends JavaPlugin {

	private SpawnManager spawnManager;
	private File spawnFile;
	private FileConfiguration spawnConfig;

	@Override
	public void onEnable() {

		saveDefaultConfig();

		spawnFile = new File(getDataFolder(), "spawn.yml");
		((ThrowingRunnable) spawnFile::createNewFile).run();

		spawnConfig = YamlConfiguration.loadConfiguration(spawnFile);

		spawnManager = SpawnManager.fromConfig(spawnConfig);

		getServer().getPluginManager().registerEvents(new SFSListeners(spawnManager, getConfig()), this);

		CommandRegistry registry = new CommandRegistry("sfs");
		registry.register(CommandName.of("define"), new CommandDefine(spawnManager));

		getCommand("simpleFirstSpawn").setExecutor(new CommandSFS(registry));


		getLogger().info("Successfully enabled SFS !");
	}

	@Override
	public void onDisable() {
		getLogger().info("Saving spawn...");
		spawnManager.save(spawnConfig);
		try {
			spawnConfig.save(spawnFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
