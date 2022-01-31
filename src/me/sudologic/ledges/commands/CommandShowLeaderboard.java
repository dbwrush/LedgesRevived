package me.sudologic.ledges.commands;

import me.sudologic.ledges.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class CommandShowLeaderboard implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String[] lines = Main.leaderboard.getLeaderboardLines();
        if(commandSender instanceof Player) {
            Player p = (Player)commandSender;
            for(String st : lines) {
                p.sendMessage(st);
            }
        } else {
            Bukkit.getLogger().log(Level.INFO, "Begin leaderboard data:");
            for(String st : lines) {
                Bukkit.getLogger().log(Level.INFO, st);
            }
        }
        return true;
    }
}
