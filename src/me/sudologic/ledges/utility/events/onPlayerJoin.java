//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.sudologic.ledges.utility.events;

import me.sudologic.ledges.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.logging.Level;

public class onPlayerJoin implements Listener {
    Main main;

    public onPlayerJoin() {
        this.main = Main.plugin;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        onPlayerDamage.playerData.put(event.getPlayer(), 0);
        Bukkit.getLogger().log(Level.INFO, "[Ledges] " + event.getPlayer().getName() + " added to Ledges game!");
        event.getPlayer().teleport(main.world.getSpawnLocation());
    }
}
