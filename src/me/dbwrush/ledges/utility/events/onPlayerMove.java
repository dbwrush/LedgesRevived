//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.dbwrush.ledges.utility.events;

import java.util.Iterator;
import java.util.logging.Level;

import me.dbwrush.ledges.Main;
import me.dbwrush.ledges.utility.LeaderboardManager;
import org.bukkit.Bukkit;
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
                Player attacker = p;
                if (onPlayerDamage.lastHit.containsKey(p)) {
                    Iterator var4 = this.main.world.getPlayers().iterator();
                    while(var4.hasNext()) {
                        Player player = (Player)var4.next();
                        player.sendMessage(ChatColor.GOLD + p.getName() + ChatColor.GREEN + " was knocked off by " + ChatColor.GOLD + ((Player)onPlayerDamage.lastHit.get(p)).getName());
                        player.playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10.0F, 10.0F);
                    }
                    attacker = (Player)onPlayerDamage.lastHit.get(p);
                    onPlayerDamage.lastHit.remove(p);
                }

                p.teleport(this.main.world.getSpawnLocation());
                p.setFoodLevel(20);
                p.setHealth(20);
                p.setVelocity(new Vector(0, 0, 0));
                if(!(attacker == p)) {
                    LeaderboardManager.PlayerStats playerStats = main.leaderboard.getPlayerStats(p);
                    LeaderboardManager.PlayerStats attackerStats = main.leaderboard.getPlayerStats(attacker);

                    playerStats.setDeaths(playerStats.getDeaths() + 1);
                    attackerStats.setKills(attackerStats.getKills() + 1);
                    main.leaderboard.updateLeaderboard();
                }
                if (onPlayerDamage.playerData.containsKey(p)) {
                    onPlayerDamage.playerData.remove(p);
                }
            }

        }
    }
}
