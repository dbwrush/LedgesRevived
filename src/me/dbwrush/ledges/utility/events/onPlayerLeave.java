//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.dbwrush.ledges.utility.events;

import java.util.Iterator;
import java.util.logging.Level;

import me.dbwrush.ledges.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class onPlayerLeave implements Listener {
    Main main;

    public onPlayerLeave() {
        this.main = Main.plugin;
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent event) {
        event.getPlayer().getInventory().clear();
        this.sendMessage(" has left the game!", event.getPlayer());
    }

    @EventHandler
    public void playerChangeWorld(PlayerChangedWorldEvent event) {
        Bukkit.getLogger().log(Level.INFO, "[Ledges] Player " + event.getPlayer() + " changed worlds.");
        final Player p = event.getPlayer();
        if (Main.plugin.world == null) {
            Main.plugin.world = Bukkit.getWorld(Main.plugin.world.getName());
        }

        if (p.getWorld().getName().equals(Main.plugin.world.getName())) {
            (new BukkitRunnable() {
                public void run() {
                    Bukkit.getLogger().log(Level.INFO, "[Ledges] Cleared inventory of leaving player " + p.getName());
                    p.getInventory().clear();
                }
            }).runTaskLater(this.main, 3L);
        }

    }

    public void sendMessage(String message, Player p) {
        Iterator var4 = Main.getPlugin().world.getPlayers().iterator();
        Bukkit.getLogger().log(Level.INFO, "[Ledges] Sending [" + message + "] to all players");
        while(var4.hasNext()) {
            Player player = (Player)var4.next();
            player.sendMessage(ChatColor.GOLD + p.getName() + ChatColor.GREEN + message);
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
        }

    }
}
