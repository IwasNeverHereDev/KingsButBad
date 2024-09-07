package kingsbutbad.kingsbutbad.commands.Leaderboards;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.DatabaseManager;
import kingsbutbad.kingsbutbad.utils.CreateText;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.time.Instant;
import java.util.*;

public class LBManager {
    public static HashMap<leaderboards, Instant> lastTimeUpdated = new HashMap<>();
    public static HashMap<leaderboards, Component> leaderboardData = new HashMap<>();
    public static HashMap<leaderboards, Boolean> isUpdating = new HashMap<>();

    public static boolean getIsUpdating(leaderboards leaderboard) {
        return isUpdating.getOrDefault(leaderboard, false);
    }

    public static Component getLeaderboardData(leaderboards leaderboard) {
        return leaderboardData.getOrDefault(leaderboard, Component.empty());
    }

    public static Instant getLastTimeUpdated(leaderboards leaderboard) {
        return lastTimeUpdated.getOrDefault(leaderboard, KingsButBad.startTime);
    }

    public static void setIsUpdating(leaderboards leaderboard, boolean isUpdating) {
        LBManager.isUpdating.put(leaderboard, isUpdating);
    }

    public static void setLastTimeUpdated(leaderboards leaderboard, Instant timeUpdated) {
        LBManager.lastTimeUpdated.put(leaderboard, timeUpdated);
    }

    public static void setLeaderboardData(leaderboards leaderboard, Component leaderboardData) {
        LBManager.leaderboardData.put(leaderboard, leaderboardData);
    }

    public static void updateLeaderboard(leaderboards leaderboard) {
        if (getIsUpdating(leaderboard)) return;

        setIsUpdating(leaderboard, true);

        Bukkit.getScheduler().runTaskAsynchronously(KingsButBad.pl, () -> {
            List<Map.Entry<String, Double>> leaderboardEntries = new ArrayList<>();

            if (leaderboard.getStatistic() != null) {
                for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                    double value = player.getStatistic(leaderboard.getStatistic());
                    leaderboardEntries.add(new AbstractMap.SimpleEntry<>(player.getName(), value));
                }
            } else if (leaderboard.getKey() != null) {
                for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                    Double vale = 0.0;
                    if(DatabaseManager.getKeyPlayer(player, leaderboard.getKey().name()) != null)
                        vale = (Double) DatabaseManager.getKeyPlayer(player, leaderboard.getKey().name());
                    leaderboardEntries.add(new AbstractMap.SimpleEntry<>(player.getName(), vale));
                }
            }

            leaderboardEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            Component leaderboardComponent = Component.text(CreateText.addColors("<gold>Leaderboard<gray>: <white>" + leaderboard.name() + "\n"));

            for (int i = 0; i < Math.min(leaderboardEntries.size(), 10); i++) {
                Map.Entry<String, Double> entry = leaderboardEntries.get(i);
                leaderboardComponent = leaderboardComponent.append(Component.text((i + 1) + ". " + entry.getKey() + ": " + entry.getValue() + "\n"));
            }

            setLeaderboardData(leaderboard, leaderboardComponent);
            setLastTimeUpdated(leaderboard, Instant.now());
            setIsUpdating(leaderboard, false);
        });
    }
}
