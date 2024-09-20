package kingsbutbad.kingsbutbad.commands.Leaderboards;

import kingsbutbad.kingsbutbad.utils.CreateText;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class LeaderboardCommand implements CommandExecutor {
    private static final Duration UPDATE_INTERVAL = Duration.ofMinutes(1);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(CreateText.addColors("<red>Please specify a leaderboard to view."));
            return true;
        }

        String leaderboardName = args[0].toUpperCase();
        leaderboards leaderboard;

        try {
            leaderboard = leaderboards.valueOf(leaderboardName);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(CreateText.addColors("<red>Leaderboard not found."));
            return true;
        }

        if (LBManager.getIsUpdating(leaderboard)) {
            sender.sendMessage(CreateText.addColors("<red>Leaderboard is currently being updated. Please try again later."));
            return true;
        }

        Instant lastUpdated = LBManager.getLastTimeUpdated(leaderboard);
        if (lastUpdated == null || Duration.between(lastUpdated, Instant.now()).compareTo(UPDATE_INTERVAL) > 0) {
            sender.sendMessage(CreateText.addColors("<red>Leaderboard data is outdated. Updating..."));
            LBManager.updateLeaderboard(leaderboard);
            return true;
        }

        Component leaderboardData = LBManager.getLeaderboardData(leaderboard);

        if (leaderboardData == Component.empty()) {
            sender.sendMessage(CreateText.addColors("<red>Leaderboard data not available. Updating..."));
            LBManager.updateLeaderboard(leaderboard);
            return true;
        }
        sender.sendMessage(leaderboardData);
        return true;
    }
}