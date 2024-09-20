package kingsbutbad.kingsbutbad.commands.Leaderboards;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.commands.Clans.Clans;
import kingsbutbad.kingsbutbad.commands.Clans.ClansDB;
import kingsbutbad.kingsbutbad.keys.DatabaseManager;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.FormatUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.time.Instant;
import java.util.*;

public class LBManager {
    public static HashMap<leaderboards, Instant> lastTimeUpdated = new HashMap<>();
    public static HashMap<leaderboards, Component> leaderboardData = new HashMap<>();
    public static HashMap<leaderboards, Boolean> isUpdating = new HashMap<>();
    private static final File gangDB = new File(ClansDB.getDataFolder(), "gangs.gz");
    private static ClansDB databaseManager;

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
                    Double value = 0.0;
                    if (DatabaseManager.getKeyPlayer(player, leaderboard.getKey().name()) != null)
                        value = (Double) DatabaseManager.getKeyPlayer(player, leaderboard.getKey().name());
                    leaderboardEntries.add(new AbstractMap.SimpleEntry<>(player.getName(), value));
                }
            } else if(leaderboard == leaderboards.GANG){
                databaseManager = ClansDB.loadData(gangDB.getPath());
                for(String gangNames : databaseManager.getGangs().keySet()){
                    Clans clans = databaseManager.getGangs().get(gangNames);
                    double value = 0.0;
                    if(clans.getBankAmount() >= 1)
                        value = clans.getBankAmount();
                    leaderboardEntries.add(new AbstractMap.SimpleEntry<>(clans.getName(), value));
                }
            }

            leaderboardEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            Component leaderboardComponent = Component.text(CreateText.addColors("<gold>Leaderboard<gray>: <white>" + leaderboard.name() + "\n"));

            for (int i = 0; i < Math.min(leaderboardEntries.size(), 10); i++) {
                Map.Entry<String, Double> entry = leaderboardEntries.get(i);

                String formattedValue;
                if (leaderboard.name().toLowerCase().contains("playtime")) {
                    formattedValue = formatPlaytime(entry.getValue());
                } else if(leaderboard == leaderboards.MONEY || leaderboard == leaderboards.GANG){
                    formattedValue = FormatUtils.formatMoney(entry.getValue());
                } else {
                    formattedValue = entry.getValue().toString();
                }

                leaderboardComponent = leaderboardComponent.append(Component.text((i + 1) + ". " + entry.getKey() + ": " + formattedValue + "\n"));
            }

            setLeaderboardData(leaderboard, leaderboardComponent);
            setLastTimeUpdated(leaderboard, Instant.now());
            setIsUpdating(leaderboard, false);
        });
    }
    private static String formatPlaytime(double ticks) {
        long totalSeconds = (long) (ticks / 20);

        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        if (hours > 0) {
            return String.format("%dh %dm %ds", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%dm %ds", minutes, seconds);
        } else {
            return String.format("%ds", seconds);
        }
    }
}
