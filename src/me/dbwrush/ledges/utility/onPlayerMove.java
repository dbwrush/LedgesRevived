//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.dbwrush.ledges.utility;

import java.util.Iterator;

import me.dbwrush.ledges.Main;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class onPlayerMove implements Listener {
    Main main;

    public onPlayerMove() {
        this.main = Main.plugin;
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if (p.getWorld() == this.main.world) {
            if (p.getLocation().getY() < 30.0D) {
                //Bukkit.getLogger().log(Level.INFO, "playerMove called in world below y=30!");
                if (onPlayerDamage.lastHit.containsKey(p)) {
                    Iterator var4 = this.main.world.getPlayers().iterator();

                    while(var4.hasNext()) {
                        Player player = (Player)var4.next();
                        player.sendMessage(ChatColor.GOLD + p.getName() + ChatColor.GREEN + " was knocked off by " + ChatColor.GOLD + ((Player)onPlayerDamage.lastHit.get(p)).getName());
                        player.playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10.0F, 10.0F);
                    }

                    onPlayerDamage.lastHit.remove(p);
                }

                p.teleport(this.main.world.getSpawnLocation());
                p.setFoodLevel(20);
                p.setHealth(20);
                p.setVelocity(new Vector(0, 0, 0));
                if (onPlayerDamage.playerData.containsKey(p)) {
                    onPlayerDamage.playerData.remove(p);
                }
            }

        }
    }
}
