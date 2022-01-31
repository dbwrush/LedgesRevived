package me.sudologic.ledges.utility;

import me.sudologic.ledges.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class LeaderboardManager {
    public class PlayerStats {
        UUID p;
        private int kills;
        private int deaths;

        public PlayerStats(OfflinePlayer player) {
            this.p = player.getUniqueId();
        }

        public PlayerStats(String fileLine) {
            String[] parts = fileLine.split(" ");

            p = UUID.fromString(parts[0]);
            kills = Integer.parseInt(parts[1]);
            deaths = Integer.parseInt(parts[2]);
            Bukkit.getLogger().log(Level.INFO, "[Ledges] loaded stats for " + Bukkit.getOfflinePlayer(p) + " Kills: " + kills + " Deaths: " + deaths);
        }

        public String getFileLine() {
            return p + " " + kills + " " + deaths;
        }

        public UUID getP() {
            return p;
        }

        public int getKills() {
            return kills;
        }

        public void setKills(int kills) {
            this.kills = kills;
        }

        public int getDeaths() {
            return deaths;
        }

        public void setDeaths(int deaths) {
            this.deaths = deaths;
        }

        public double getKD() {
            return ((double)kills)/((double)deaths);
        }

        public boolean betterThan(PlayerStats otherPlayer) {
            double myKD = ((double)kills) / ((double)deaths);
            double theirKD = ((double)otherPlayer.getKills()) / ((double)otherPlayer.getDeaths());
            return myKD > theirKD;
        }
    }

    private HashMap<UUID, PlayerStats> statMap;
    private ArrayList<OfflinePlayer> leaderboard;

    public LeaderboardManager() {
        statMap = readFile();
        leaderboard = new ArrayList<>();
        updateLeaderboard();
        //printMap();
    }

    public PlayerStats getPlayerStats(OfflinePlayer p) {
        if(!statMap.containsKey(p.getUniqueId())) {
            statMap.put(p.getUniqueId(), new PlayerStats(p));
        }
        return statMap.get(p.getUniqueId());
    }

    public void setPlayerStats(Player p, PlayerStats stats) {
        statMap.put(p.getUniqueId(), stats);
        updateLeaderboard();
    }

    public HashMap<UUID, PlayerStats> readFile() {
        HashMap<UUID, PlayerStats> map = new HashMap<>();
        File file = new File("plugins/LedgesRevived/leaderboard.txt");
        BufferedReader br = null;
        Bukkit.getLogger().log(Level.INFO, "[Ledges] Attempting to load leaderboard.txt");
        try{
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            Bukkit.getLogger().log(Level.INFO,"[Ledges] No leaderboard file found. Creating a new one.");
            Main.plugin.saveResource("leaderboard.txt", true);
            return map;
        }
        Bukkit.getLogger().log(Level.INFO, "[Ledges] Found leaderboard.txt. Loading player info.");
        ArrayList<String> lines = new ArrayList<>();
        String st = "";
        while(true) {
            try{
                if(!((st = br.readLine()) != null)) break;
            } catch (IOException e) {

            }
            PlayerStats readStats = new PlayerStats(st);
            map.put(readStats.getP(), readStats);
        }
        Bukkit.getLogger().log(Level.INFO, "[Ledges] Finished loading player info.");
        return map;
    }

    public void writeFile() {
        ArrayList<String> lines = new ArrayList<>();
        for(PlayerStats stats : statMap.values()) {
            lines.add(stats.getFileLine());
        }
        Path file = Paths.get("plugins/LedgesRevived/leaderboard.txt");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.INFO,"[Ledges] Error writing leaderboard to file.");
            e.printStackTrace();
        }
    }

    public void updateLeaderboard() {
        //printMap();
        leaderboard = new ArrayList<>();
        for(PlayerStats stats : statMap.values()) {
            double kd = stats.getKD();
            if(leaderboard.size() <= 0) {
                leaderboard.add(Bukkit.getOfflinePlayer(stats.p));
            } else {
                boolean added = false;
                for(int i = 0; i < leaderboard.size(); i++) {
                    PlayerStats otherStats = statMap.get(leaderboard.get(i).getUniqueId());
                    if(kd > otherStats.getKD()) {
                        leaderboard.add(i, Bukkit.getOfflinePlayer(stats.p));
                        added = true;
                        break;
                    }
                }
                if(!added) {
                    leaderboard.add(Bukkit.getOfflinePlayer(stats.p));
                }
            }
        }
        Bukkit.getLogger().log(Level.INFO, "[Ledges] Leaderboard updated.");
        //printLeaderboard();
    }

    public String[] getLeaderboardLines() {
        String[] strings = new String[leaderboard.size()];
        for(int i = 0; i < strings.length; i++) {
            OfflinePlayer p = leaderboard.get(i);
            PlayerStats pStats = getPlayerStats(p);
            strings[i] = p.getName() + " K: " + pStats.kills + " D: " + pStats.deaths;
        }
        return strings;
    }

    public void printLeaderboard() {
        Bukkit.getLogger().log(Level.INFO, "[Ledges] Printing current leaderboard:");
        for(OfflinePlayer p : leaderboard) {
            Bukkit.getLogger().log(Level.INFO, "[Ledges] "+ leaderboard.indexOf(p) + " " + p.getName());
        }
    }

    public void printMap() {
        Bukkit.getLogger().log(Level.INFO, "[Ledges] Printing current player map:");
        for(PlayerStats ps : statMap.values()) {
            Bukkit.getLogger().log(Level.INFO, "[Ledges] " + Bukkit.getOfflinePlayer(ps.p) + " " + ps.getFileLine());
        }
    }
}
