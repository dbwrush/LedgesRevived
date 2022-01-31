//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.sudologic.ledges;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import me.sudologic.ledges.commands.CommandShowLeaderboard;
import me.sudologic.ledges.utility.LeaderboardManager;
import me.sudologic.ledges.utility.events.onPlayerDamage;
import me.sudologic.ledges.utility.events.onPlayerJoin;
import me.sudologic.ledges.utility.events.onPlayerLeave;
import me.sudologic.ledges.utility.events.onPlayerMove;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private File customConfigFile;
    private FileConfiguration customConfig;

    public String worldName;
    public boolean useWorldSpawn;
    public int startX;
    public int startY;
    public int startZ;
    public int floor;
    public static World world;
    public static Main plugin;
    public static LeaderboardManager leaderboard;

    public Main() {
    }

    public void Plugin(Main plugin) {
        Main.plugin = plugin;
    }

    public static Main getPlugin() {
        return plugin;
    }

    public void onEnable() {
        this.Plugin(this);
        Bukkit.getLogger().log(Level.INFO, "[Ledges] Plugin has started!");
        this.world = Bukkit.getServer().getWorld(worldName);
        leaderboard = new LeaderboardManager();
        this.registerCommands();
        this.registerListeners();
    }

    public void onDisable() {
        leaderboard.writeFile();
        Bukkit.getLogger().log(Level.INFO, "[Ledges] Writing leaderboard data to file.");
        Bukkit.getLogger().log(Level.INFO, "[Ledges] Plugin has shut down!");
    }

    private void registerCommands() {
        this.getCommand("ShowLeaderboard").setExecutor(new CommandShowLeaderboard());
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new onPlayerJoin(), this);
        pluginManager.registerEvents(new onPlayerDamage(), this);
        pluginManager.registerEvents(new onPlayerLeave(), this);
        pluginManager.registerEvents(new onPlayerMove(), this);
    }

    public void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "config.yml");
        if(!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }

        customConfig = new YamlConfiguration();
        try{
            customConfig.load(customConfigFile);
        } catch(IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void createConfigs() {
        this.saveDefaultConfig();
        this.getConfig();
        FileConfiguration config = this.getConfig();

        worldName = config.getString("world");
        useWorldSpawn = config.getBoolean("useWorldSpawn");
        startX = config.getInt("solox");
        startY = config.getInt("soloy");
        startZ = config.getInt("soloz");
        floor = config.getInt("floor");
    }
}
