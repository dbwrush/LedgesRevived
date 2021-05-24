//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.dbwrush.ledges;

import java.util.logging.Level;

import me.dbwrush.ledges.utility.LeaderboardManager;
import me.dbwrush.ledges.utility.events.onPlayerDamage;
import me.dbwrush.ledges.utility.events.onPlayerJoin;
import me.dbwrush.ledges.utility.events.onPlayerLeave;
import me.dbwrush.ledges.utility.events.onPlayerMove;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public int startX;
    public int startY;
    public int startZ;
    public World world;
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
        this.world = Bukkit.getServer().getWorld("world");
        this.registerListeners();
        leaderboard = new LeaderboardManager();
    }

    public void onDisable() {
        leaderboard.writeFile();
        Bukkit.getLogger().log(Level.INFO, "[Ledges] Writing leaderboard data to file.");
        Bukkit.getLogger().log(Level.INFO, "[Ledges] Plugin has shut down!");
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new onPlayerJoin(), this);
        pluginManager.registerEvents(new onPlayerDamage(), this);
        pluginManager.registerEvents(new onPlayerLeave(), this);
        pluginManager.registerEvents(new onPlayerMove(), this);
    }
}
