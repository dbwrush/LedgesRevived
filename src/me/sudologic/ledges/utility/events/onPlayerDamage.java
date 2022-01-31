//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.sudologic.ledges.utility.events;

import java.util.HashMap;

import me.sudologic.ledges.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

public class onPlayerDamage implements Listener {
    Main main;
    public static HashMap<Player, Integer> playerData = new HashMap();
    public static HashMap<Player, Player> lastHit = new HashMap();

    public onPlayerDamage() {
        this.main = Main.plugin;
    }

    @EventHandler
    public void playerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity().getWorld() == this.main.world && (event.getEntity() instanceof Player || !(event.getDamager() instanceof Player))) {
            Player player = (Player)event.getEntity();
            if(player.getLocation().getY() > main.world.getSpawnLocation().getY() - 10) {
                event.setCancelled(true);
                return;
            }
            Player attacker = (Player)event.getDamager();
            lastHit.put(player, attacker);
            if (attacker.getFoodLevel() < 20) {
                attacker.setFoodLevel(attacker.getFoodLevel() + 1);
            }

            player.setFoodLevel(player.getFoodLevel() - 1);
            if (!playerData.containsKey(player)) {
                playerData.put(player, 1);
            } else /*if ((Integer)playerData.get(player) < 1000)*/ {
                playerData.put(player, (Integer)playerData.get(player) + 1);
            } /*else {
                player.teleport(this.main.world.getSpawnLocation());
                playerData.put(player, 0);
                Iterator var5 = this.main.world.getPlayers().iterator();

                while(var5.hasNext()) {
                    Player p = (Player)var5.next();
                    p.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " was killed!");
                }
            }*/

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§4§l" + playerData.get(player) + "%"));
            Vector direction = player.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize();
            Vector finalDirection = direction.multiply((double)(Integer)playerData.get(player) * 0.1D);
            player.setVelocity(new Vector(finalDirection.getX(), 0.5D, finalDirection.getZ()));
            if (!attacker.getGameMode().equals(GameMode.CREATIVE)) {
                event.setDamage(0.0D);
            }

        }
    }

    @EventHandler
    public void otherPlayerDamage(EntityDamageEvent event) {
        event.getCause();
        if (event.getEntity().getWorld() == this.main.world && event.getEntity() instanceof Player && !event.getCause().equals(DamageCause.ENTITY_ATTACK) && !event.getCause().equals(DamageCause.VOID) && !event.getCause().equals(DamageCause.STARVATION)) {
            event.setCancelled(true);
        }
    }
}
