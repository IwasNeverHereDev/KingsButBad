package kingsbutbad.kingsbutbad.commands;

import kingsbutbad.kingsbutbad.keys.DatabaseManager;
import kingsbutbad.kingsbutbad.keys.Key;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.FormatUtils;
import kingsbutbad.kingsbutbad.utils.Role;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage(CreateText.addColors("<red>You must specify a player name!"));
            return false;
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);

        if (!player.hasPlayedBefore() && !player.isOnline()) {
            commandSender.sendMessage(CreateText.addColors("<red>Player not found or has never played before."));
            return false;
        }

        List<String> stats = new ArrayList<>();

        stats.add(CreateText.addColors("<gray>--- Stats for <green>" + player.getName() + "<gray> ---"));
        stats.add(CreateText.addColors("<gray>Playtime: <white>" + FormatUtils.parseTicksToTime(player.getStatistic(Statistic.PLAY_ONE_MINUTE))));
        stats.add(CreateText.addColors("<gray>Kills: <white>" + player.getStatistic(Statistic.PLAYER_KILLS)));
        stats.add(CreateText.addColors("<gray>Deaths: <white>" + player.getStatistic(Statistic.DEATHS)));

        int kills = player.getStatistic(Statistic.PLAYER_KILLS);
        int deaths = player.getStatistic(Statistic.DEATHS);
        double kdr = deaths > 0 ? (double) kills / deaths : kills;
        stats.add(CreateText.addColors("<gray>KDR: <white>" + String.format("%.2f", kdr)));

        for(Role role : Role.values())
            stats.addAll(getRoleStats(role, player));

        commandSender.sendMessage(String.join("\n", stats));

        return true;
    }
    private List<String> getRoleStats(Role role, OfflinePlayer player) {
        List<String> stats = new ArrayList<>();

        Key roleKillsKey = getRoleKills(role);
        Key roleDeathsKey = getRoleDeaths(role);
        Key rolePlaytimeKey = getRolePlaytime(role);

        if (roleKillsKey == null || roleDeathsKey == null || rolePlaytimeKey == null) {
            stats.add(CreateText.addColors("<red>Error: Role statistics keys are not properly defined. <gray>(<white>Role: "+role.name()+"<gray>)"));
            return stats;
        }

        Double roleKills = fetchStatFromDatabase(player, roleKillsKey);
        Double roleDeaths = fetchStatFromDatabase(player, roleDeathsKey);
        Double rolePlaytime = fetchStatFromDatabase(player, rolePlaytimeKey);

        roleKills = (roleKills != null) ? roleKills : 0.0;
        roleDeaths = (roleDeaths != null) ? roleDeaths : 0.0;
        rolePlaytime = (rolePlaytime != null) ? rolePlaytime : 0.0;

        stats.add(CreateText.addColors("<gray>--- " + role.tag + " Stats <gray> ---"));
        stats.add(CreateText.addColors("     <gray>- Playtime: <white>" + FormatUtils.parseDoubleTicksToTime(rolePlaytime)));
        stats.add(CreateText.addColors("     <gray>- Kills: <white>" + roleKills));
        stats.add(CreateText.addColors("     <gray>- Deaths: <white>" + roleDeaths));
        stats.add(CreateText.addColors("     <gray>- KDR: <white>" + getKDR(roleKills, roleDeaths)));

        return stats;
    }
    private String getKDR(double kills, double deaths){
        double kdr = deaths > 0 ? kills / deaths : kills;
        return String.format("%.2f", kdr);
    }
    private Key getRoleKills(kingsbutbad.kingsbutbad.utils.Role role){
        return Keys.valueOf(role.name().toUpperCase()+"kills");
    }
    private Key getRoleDeaths(kingsbutbad.kingsbutbad.utils.Role role){
        return Keys.valueOf(role.name().toUpperCase()+"Deaths");
    }
    private Key getRolePlaytime(kingsbutbad.kingsbutbad.utils.Role role){
        return Keys.valueOf(role.name().toUpperCase()+"Ticks");
    }
    private Double fetchStatFromDatabase(OfflinePlayer player, Key key) {
        Object result = DatabaseManager.getKeyPlayer(player, key.name());
        return result instanceof Double ? (Double) result : null;
    }
}

